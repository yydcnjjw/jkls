package org.yydcnjjw.jkls.lsp

class WorkspaceEdit(
    val changes: Map<String, List<TextEdit>>?,
    val documentChanges: List<TextDocumentEdit>?
)