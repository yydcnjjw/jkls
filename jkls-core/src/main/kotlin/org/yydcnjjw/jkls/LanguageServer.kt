package org.yydcnjjw.jkls

import com.alibaba.fastjson.JSON
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.yydcnjjw.jkls.lsp.ClientCapabilities
import org.yydcnjjw.jkls.lsp.ServerCapabilities
import org.yydcnjjw.jkls.lsp.message.*
import org.yydcnjjw.jkls.project.Project
import java.io.InputStream
import java.io.OutputStream
import java.net.URI

data class InMessage(
        val id: Any?,
        val method: String,
        val content: String
)

class Reply constructor(
        private val id: Any,
        private val ls: LanguageServer
) {
    operator fun <R> invoke(result: R) {
        ls.sendMessage(ResponseMessage<R, Any>(id, result, null))
    }

    fun <D> error(responseError: ResponseError<D>) {
        ls.sendMessage(ResponseMessage<Any, D>(id, null, responseError))
    }
}

class LanguageServerException(
        message: String
) : Exception(message)

fun initialize(server: LanguageServer, param: InitializeParams, reply: Reply) {
    // Logger(LogLevel.DEBUG, param.toString())
    try {
        val project = Project.getProject(URI(param.rootUri))
        project.index()
    } catch (e: Exception) {
        reply.error(ResponseError<Unit>(
                ErrorCode.InternalError.code,
                e.message ?: "",
                null
        ))
        return
    }

    server.clientCapabilities = param.capabilities
    val initializeResult = InitializeResult(ServerCapabilities())
    reply(initializeResult)
}

class LanguageServer(
        private val receive: InputStream,
        private val send: OutputStream
) {
    private val methods: MutableMap<String, (String, Reply) -> Unit> = mutableMapOf()
    private val rwChannel = Channel<String>(Channel.UNLIMITED)
    private val messageChannel = Channel<InMessage>(Channel.UNLIMITED)

    private var messageHandlerJob: Job? = null
    var project: Project? = null
    var clientCapabilities: ClientCapabilities? = null

    init {
        Logger.addDispatcher(this::windowLogMessage)

        registerMessageMethod(
                ServerLifetimeMethod.INITIALIZE.value,
                ::initialize)
    }

    suspend fun start() {
        messageReader()
        messageWriter()
        messageHandlerJob = messageHandler()
        messageHandlerJob?.join()
        Logger.join()
    }


    fun exit() {
        messageHandlerJob?.cancel()
        Logger.exit()
    }

    private inline fun <reified P> registerMessageMethod(
            methodName: String,
            crossinline handler: (LanguageServer, P, Reply) -> Unit
    ) {
        if (methods.containsKey(methodName)) {
            throw LanguageServerException("repeat that register message method")
        }

        methods[methodName] = { body, reply ->
            val param = JSON.parseObject(body,
                    P::class.java)!!
            handler(this@LanguageServer, param, reply)
        }
    }

    private fun unregisterMessageMethod(methodName: String) {
        methods.remove(methodName)
    }

    private fun messageReader() = GlobalScope.launch(Dispatchers.IO) {
        val reader = receive.bufferedReader()
        val body = StringBuilder()
        while (isActive) {
            val contentLength = reader.readLine()
            var len: Long? = 0L
            if (contentLength.length > 16) {
                len = contentLength.substring(16).toLongOrNull()
            }

            if (len == null || len == 0L) {
                Logger(LogLevel.ERR, "parse header error $len")
                exit()
                break
            }

            reader.readLine()

            for (i in 1..len) {
                body.append(reader.read().toChar())
            }

            // Logger(LogLevel.INFO, body.toString())

            val message = JSON.parseObject(body.toString())
            messageChannel.send(InMessage(message.get("id"),
                    message.getString("method"),
                    message.get("params").toString()))
            body.clear()
        }
    }

    private fun messageWriter() = GlobalScope.launch(Dispatchers.IO) {
        val writer = send.bufferedWriter()
        while (isActive) {
            val content = rwChannel.receive()
            val message = "Content-Length: ${content.length}\r\n\r\n$content"
            writer.append(message)
            writer.flush()
        }
    }

    private fun messageHandler() = GlobalScope.launch {
        while (true) {
            val message = try {
                messageChannel.receive()
            } catch (e: CancellationException) {
                break
            }
            val handler = methods[message.method]
            if (handler == null) {
                if (message.id != null) {
                    val reply = Reply(message.id, this@LanguageServer)
                    reply.error(ResponseError(ErrorCode.MethodNotFound.code,
                            "Not support ${message.method}", null))
                }
                continue
            }
            try {
                if (message.id != null) {
                    val reply = Reply(message.id, this@LanguageServer)
                    // require message
                    handler(message.content, reply)
                } else {
                    // notification message
                }
            } catch (e: Exception) {
                Logger(LogLevel.ERR, e.message ?: "")
                exit()
                break
            }

        }
    }

    fun sendMessage(message: Message) = GlobalScope.launch {
        rwChannel.send(JSON.toJSONString(message))
    }

    private fun windowLogMessage(log: Log) {
        val logType = when (log.level) {
            LogLevel.DEBUG -> MessageType.Log
            LogLevel.INFO -> MessageType.Info
            LogLevel.WARN -> MessageType.Warning
            LogLevel.ERR -> MessageType.Error
        }

        val logMessageParams = LogMessageParams(logType.code, log.message)

        val message = NotificationMessage(WindowMethod.LOG_MESSAGE.value, logMessageParams)
        sendMessage(message)
    }
}