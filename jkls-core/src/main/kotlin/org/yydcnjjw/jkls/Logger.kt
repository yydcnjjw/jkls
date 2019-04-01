package org.yydcnjjw.jkls

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.io.File

enum class LogLevel {
    ERR,
    WARN,
    INFO,
    DEBUG
}

data class Log(
        val level: LogLevel,
        val message: String
)
typealias DispatcherFunc = (Log) -> Unit

object Logger {
    val logChannel = Channel<Log>(Channel.UNLIMITED)
    private val dispatchers = arrayListOf<DispatcherFunc>()
    private val logJob: Job
    init {
        logJob = dispatcher()
    }

    operator fun invoke(level: LogLevel, message: String) = GlobalScope.launch {
        logChannel.send(Log(level, message))
    }

    private fun dispatcher() = GlobalScope.launch {
        while (true) {
            val log = try {
                logChannel.receive()
            } catch (e: CancellationException) {
                break
            }

            dispatchers.forEach {
                it(log)
            }

        }
    }

    fun addDispatcher(func: DispatcherFunc) {
        dispatchers.add(func)
    }
    fun removeDispatcher(func: DispatcherFunc) {
        dispatchers.remove(func)
    }

    suspend fun join() {
        logJob.join()
    }

    fun exit() {
        logJob.cancel()
    }
}