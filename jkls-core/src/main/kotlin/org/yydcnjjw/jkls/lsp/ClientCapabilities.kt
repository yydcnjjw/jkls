package org.yydcnjjw.jkls.lsp

import org.yydcnjjw.jkls.lsp.message.CompletionItemKind
import org.yydcnjjw.jkls.lsp.message.EnumKind
import org.yydcnjjw.jkls.lsp.message.SymbolKind

enum class ResourceOperationKind(val kind: String) {
    Create("create"),
    Rename("rename"),
    Delete("delete")
}

enum class FailureHandlingKind(val kind: String) {
    Abort("abort"),
    Transactional("transactional"),
    TextOnlyTransactional("textOnlyTransactional"),
    Undo("undo")
}

interface IDynamicRegistration {
    val dynamicRegistration: Boolean?
}

data class DynamicRegistration(
    override val dynamicRegistration: Boolean?
): IDynamicRegistration

interface ILinkSupport {
    val linkSupport: Boolean?
}

data class LinkSupport(
    override val linkSupport: Boolean?
): ILinkSupport

data class WorkspaceEditCapabilities(
    val documentChanges: Boolean?,
    val resourceOperations: List<ResourceOperationKind>?,
    val failureHandling: FailureHandlingKind?
)

data class KindSet<T>(
        private val valueSet: List<Any>)

data class WorkspaceSymbolCapabilities(
    override val dynamicRegistration: Boolean?,
    val symbolKind: KindSet<SymbolKind>?
): IDynamicRegistration

data class WorkspaceClientCapabilities(
    val applyEdit: Boolean?,
    val workspaceEdit: WorkspaceEditCapabilities?,
    val didChangeConfiguration: DynamicRegistration?,
    val didChangeWatchedFiles: DynamicRegistration?,
    val symbol: WorkspaceSymbolCapabilities?,
    val executeCommand: DynamicRegistration?,
    val workspaceFolders: Boolean?,
    val configuration: Boolean?
)

// synchronization
data class TextDocumentSynchronizationCapabilities(
    override val dynamicRegistration: Boolean?,
    val willSave: Boolean?,
    val willSaveWaitUntil: Boolean?,
    val didSave: Boolean?
): IDynamicRegistration

// completion
data class TextDocumentCompletionCapabilities(
        override val dynamicRegistration: Boolean?,
        val completionItem: TextDocumentCompletionItemOptions?,
        val completionItemKind: KindSet<CompletionItemKind>?,
        val contextSupport: Boolean?
): IDynamicRegistration

data class TextDocumentCompletionItemOptions(
    val snippetSupport: Boolean?,
    val commitCharactersSupport: Boolean?,
    val documentationFormat: List<MarkupKind>?,
    val deprecatedSupport: Boolean?,
    val preselectSupport: Boolean?
)

// hover
data class TextDocumentHoverCapabilities(
    override val dynamicRegistration: Boolean?,
    val contentFormat: List<MarkupKind>?
): IDynamicRegistration

// signature help
data class TextDocumentSignatureHelpCapabilities(
    override val dynamicRegistration: Boolean?,
    val signatureInformation: TextDocumentSignatureInformationOptions?
): IDynamicRegistration

data class TextDocumentSignatureInformationOptions(
    val documentationFormat: List<MarkupKind>?,
    val parameterInformation: Any? // TODO labelOffsetSupport
)

// documentSymbol
data class TextDocumentDocumentSymbolCapabilities(
    override val dynamicRegistration: Boolean?,
    val symbolKind: KindSet<SymbolKind>?,
    val hierarchicalDocumentSymbolSupport: Boolean?
): IDynamicRegistration

// declaration
data class TextDocumentDeclarationCapabilities(
    override val dynamicRegistration: Boolean?,
    override val linkSupport: Boolean?
): IDynamicRegistration, ILinkSupport

data class TextDocumentDefinitionCapabilities(
    override val dynamicRegistration: Boolean?,
    override val linkSupport: Boolean?
): IDynamicRegistration, ILinkSupport

data class TextDocumentTypeDefinitionCapabilities(
    override val dynamicRegistration: Boolean?,
    override val linkSupport: Boolean?
): IDynamicRegistration, ILinkSupport

data class TextDocumentImplementationCapabilities(
    override val dynamicRegistration: Boolean?,
    override val linkSupport: Boolean?
): IDynamicRegistration, ILinkSupport

data class TextDocumentCodeActionCapabilities(
    override val dynamicRegistration: Boolean?
//    val codeActionLiteralSupport: TextDocumentCodeActionLiteralOptions?
): IDynamicRegistration

//data class TextDocumentCodeActionLiteralOptions(
//    val codeActionKind: KindSet<CodeActionKind>
//)

data class TextDocumentRenameCapabilities(
    override val dynamicRegistration: Boolean?,
    val prepareSupport: Boolean?
): IDynamicRegistration

data class TextDocumentPublishDiagnosticsCapabilities(
    val relatedInformation: Boolean?
)

data class TextDocumentFoldingRangeCapabilities(
    override val dynamicRegistration: Boolean?,
    val rangeLimit: Int?,
    val lineFoldingOnly: Boolean?
): IDynamicRegistration

data class TextDocumentClientCapabilities(
    val synchronization: TextDocumentSynchronizationCapabilities?,
    val completion:TextDocumentCompletionCapabilities?,
    val hover: TextDocumentHoverCapabilities?,
    val signatureHelp: TextDocumentSignatureHelpCapabilities?,
    val references: DynamicRegistration?,
    val documentHighlight: DynamicRegistration?,
    val documentSymbol: TextDocumentDocumentSymbolCapabilities?,
    val formatting: DynamicRegistration?,
    val rangeFormatting: DynamicRegistration?,
    val onTypeFormatting: DynamicRegistration?,
    val declaration: TextDocumentDeclarationCapabilities?,
    val definition: TextDocumentDefinitionCapabilities?,
    val typeDefinition: TextDocumentTypeDefinitionCapabilities?,
    val implementation: TextDocumentImplementationCapabilities?,
    val codeAction: TextDocumentCodeActionCapabilities?,
    val codeLens: DynamicRegistration?,
    val colorProvider: DynamicRegistration?,
    val rename: TextDocumentRenameCapabilities?,
    val publishDiagnostics: TextDocumentPublishDiagnosticsCapabilities?,
    val foldingRange: TextDocumentFoldingRangeCapabilities?
)

data class ClientCapabilities(
    val workspace: WorkspaceClientCapabilities?,
    val textDocument: TextDocumentClientCapabilities?,
    val experimental: Any?
)
