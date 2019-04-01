package org.yydcnjjw.jkls.lsp.message

import org.yydcnjjw.jkls.lsp.Diagnostic
import org.yydcnjjw.jkls.lsp.DocumentUri

enum class DiagnosticsMethod(val value: String) {
    PUBLISH_DIAGNOSTICS("textDocument/publishDiagnostics")
}


/**
 * PublishDiagnostics Notification
 * @method 'textDocument/publishDiagnostics'
 * @param 'PublishDiagnosticsParams'
 */
data class PublishDiagnosticsParams(
    val uri: DocumentUri,
    val diagnostics: List<Diagnostic>
)