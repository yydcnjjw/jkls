package org.yydcnjjw.jkls.lsp.message

enum class WindowMethod(val value: String) {
    SHOW_MESSAGE("window/showMessage"),
    SHOW_MESSAGE_REQUEST("window/showMessageRequest"),
    LOG_MESSAGE("window/logMessage")
}

enum class MessageType(val code: Int) {
    Error(1),
    Warning(2),
    Info(3),
    Log(4)
}

interface IWindowMessage {
    val type: Int
    val message: String
}

/**
 * ShowMessage Notification
 * @method 'window/showMessage'
 * @param 'ShowMessageParams'
 */
data class ShowMessageParams(
    override val type: Int,
    override val message: String
): IWindowMessage

/**
 * ShowMessage Request
 * @method 'window/showMessageRequest'
 * @param 'ShowMessageRequestParams'
 *
 * ShowMessage Response
 * @result 'MessageActionItem | null'
 */
data class ShowMessageRequestParams(
    override val type: Int,
    override val message: String,
    val actions: List<MessageActionItem>?
): IWindowMessage

data class MessageActionItem(
    val title: String
)

/**
 * LogMessage Notification
 * @method 'window/logMessage'
 */
data class LogMessageParams(
    override val type: Int,
    override val message: String
): IWindowMessage