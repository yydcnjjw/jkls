package org.yydcnjjw.jkls.lsp.message

import org.yydcnjjw.jkls.lsp.ClientCapabilities
import org.yydcnjjw.jkls.lsp.DocumentUri
import org.yydcnjjw.jkls.lsp.ServerCapabilities

enum class ServerLifetimeMethod(val value: String) {
    INITIALIZE("initialize"),
    INITIALIZED("initialized"),
    SHUTDOWN("shutdown"),
    EXIT("exit"),
    CANCEL("\$/cancelRequest")
}

/**
 * Initialize Request
 * @method 'initialize'
 * @param 'InitializeParams'
 *
 * Initialize Response
 * @result 'InitializeResult'
 * @error.code InitializeErrorCode
 * @error.data InitializeError
 */
data class InitializeParams(
    val processId: Int,
    /*
     * @deprecated in favour of rootUri
     */
    val rootPath: String?,
    val rootUri: DocumentUri?,
    val initializationOptions: Any?,
    val capabilities: ClientCapabilities,
    val trace: String?,
    val workspaceFolders: List<WorkspaceFolder>?
)

enum class TraceKind (val value: String) {
    OFF("off"),
    MESSAGES("message"),
    VERBOSE("verbose")
}

data class InitializeResult(
    val capabilities: ServerCapabilities
)

enum class InitializeErrorCode(val e: Int) {
    unknownProtocolVersion(1)
}

data class InitializeError(
    val retry: Boolean
)

/**
 * Initialized Notification
 * @method 'initialized'
 * @param 'NotificationInitializedParams'
 */
class NotificationInitializedParams

/**
 * Cancellation Notification
 * @method '$/cancelRequest'
 * @ param 'CancelParams'
 */
data class CancelParams(
    // Int | String
    val id: Any
)
