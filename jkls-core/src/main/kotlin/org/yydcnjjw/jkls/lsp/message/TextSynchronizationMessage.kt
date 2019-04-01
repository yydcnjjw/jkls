package org.yydcnjjw.jkls.lsp.message

import org.yydcnjjw.jkls.lsp.*

enum class TextSynchronizationMethod(val value: String) {
    DID_OPEN("textDocument/didOpen"),
    DID_CHANGE("textDocument/didChange"),
    WILL_SAVE("textDocument/willSave"),
    WILL_SAVE_WAIT_UNTIL("textDocument/willSaveWaitUntil"),
    DID_SAVE("textDocument/didSave"),
    DID_CLOSE("textDocument/didClose"),

}

/**
 * DidOpenTextDocument Notification
 * @method 'textDocument/didOpen'
 * @param 'DidOpenTextDocumentParams'
 *
 * @Options 'TextDocumentRegistrationOptions'
 */

data class DidOpenTextDocumentParams(
    val textDocument: TextDocumentItem
)

/**
 * DidChangeTextDocument Notification
 * @method 'textDocument/didChange'
 * @param 'DidChangeTextDocumentParams'
 *
 * @Options 'TextDocumentChangeRegistrationOptions'
 */

data class DidChangeTextDocumentParams(
    val textDocument: VersionedTextDocumentIdentifier,
    val contentChanges: List<TextDocumentContentChangeEvent>
)

data class TextDocumentContentChangeEvent(
    val range: Range?,
    val rangeLength: Int?,
    val text: String
)

data class TextDocumentChangeRegistrationOptions(
    override val documentSelector: DocumentSelector?,
    val syncKind: TextDocumentSyncKind
) : TextDocumentRegistrationOptions(documentSelector)


/**
 * WillSaveTextDocument Notification
 * @method 'textDocument/willSave'
 * @param 'WillSaveTextDocumentParams'
 */

data class WillSaveTextDocumentParams(
    val textDocument: TextDocumentIdentifier?,
    val reason: TextDocumentSaveReason
)

enum class TextDocumentSaveReason(val value: Int) {
    Manual(1),
    AfterDelay(2),
    FocusOut(3)
}

/**
 * WillSaveWaitUntilTextDocument Request
 * @method 'textDocument/willSaveWaitUntil'
 * @param 'WillSaveTextDocumentParams'
 *
 * @Options 'TextDocumentRegistrationOptions'
 * Response
 * @result 'TextEdit[] | null'
 */

/**
 * DidSaveTextDocument Notification
 * @method 'textDocument/didSave'
 * @param 'DidSaveTextDocumentParams'
 *
 * @Options 'TextDocumentSaveRegistrationOptions'
 */

data class DidSaveTextDocumentParams(
    val textDocument: TextDocumentIdentifier,
    val text: String?
)

data class TextDocumentSaveRegistrationOptions(
    override val documentSelector: DocumentSelector?,
    val includeText: Boolean?
) :TextDocumentRegistrationOptions(documentSelector)

/**
 * DidCloseTextDocument Notification
 * @method 'textDocument/didClose'
 * @param 'DidCloseTextDocumentParams'
 *
 * @Options 'TextDocumentRegistrationOptions'
 */

data class DidCloseTextDocumentParams(
    val textDocument: TextDocumentIdentifier
)