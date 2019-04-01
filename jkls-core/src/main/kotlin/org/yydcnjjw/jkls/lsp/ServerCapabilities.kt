package org.yydcnjjw.jkls.lsp

data class CompletionOptions(
        val resolveProvider: Boolean? = true,
        val triggerCharacters: List<String>? = arrayListOf(
                "."
        )
)

data class SignatureHelpOptions(
        val triggerCharacters: List<String>? = arrayListOf()
)

//data class CodeActionOptions(
//    val codeActionKind: List<CodeActionKind>?
//)

data class CodeLensOptions(
        val resolveProvider: Boolean? = false
)

data class DocumentOnTypeFormattingOptions(
        val firstTriggerCharacter: String = "}",
        val moreTriggerCharacter: List<String>? = arrayListOf()
)

data class RenameOptions(
        val prepareProvider: Boolean? = true
)

data class DocumentLinkOptions(
        val resolveProvider: Boolean? = false
)

data class ExecuteCommandOptions(
        val commands: List<String> = arrayListOf()
)

data class SaveOptions(
        val includeText: Boolean? = true
)

// TODO
class ColorProviderOptions

class FoldingRangeProviderOptions

enum class TextDocumentSyncKind(val kind: Int) {
    None(0),
    Full(1),
    Incremental(2)
}

data class TextDocumentSyncOptions(
        val openClose: Boolean?,
        val change: Int?,
        val willSave: Boolean?,
        val willSaveWaitUntil: Boolean?,
        val save: SaveOptions?
)

data class StaticRegistrationOptions(
        val id: String?
)


data class ServerWorkspaceFoldersOptions(
        val support: Boolean? = true,
        // string | boolean
        val changeNotifications: Any? = true
)

data class ServerWorkspaceOptions(
        val workspaceFolders: ServerWorkspaceFoldersOptions? = ServerWorkspaceFoldersOptions()
)

data class ServerCapabilities(
        // TextDocumentSyncOptions | TextDocumentSyncKind
        val textDocumentSync: Any? = TextDocumentSyncKind.None.kind,
        val hoverProvider: Boolean? = true,
        val completionProvider: CompletionOptions? = CompletionOptions(),
        val signatureHelpProvider: SignatureHelpOptions? = SignatureHelpOptions(),
        val definitionProvider: Boolean? = true,
        // boolean | (TextDocumentRegistrationOptions & StaticRegistrationOptions)
        val typeDefinitionProvider: Any? = true,
        // boolean | (TextDocumentRegistrationOptions & StaticRegistrationOptions)
        val implementationProvider: Any? = true,
        val referencesProvider: Boolean? = true,
        val documentHighlightProvider: Boolean? = true,
        val documentSymbolProvider: Boolean? = true,
        val workspaceSymbolProvider: Boolean? = true,
        // boolean | CodeActionOptions
        val codeActionProvider: Any? = true,
        val codeLensProvider: CodeLensOptions? = CodeLensOptions(),
        val documentFormattingProvider: Boolean? = true,
        val documentRangeFormattingProvider: Boolean? = true,
        val documentOnTypeFormattingProvider: DocumentOnTypeFormattingOptions? = DocumentOnTypeFormattingOptions(),
        // boolean | RenameOptions
        val renameProvider: Any? = true,
        val documentLinkProvider: DocumentLinkOptions? = DocumentLinkOptions(),
        // boolean | ColorProviderOptions |
        // (ColorProviderOptions & TextDocumentRegistrationOptions & StaticRegistrationOptions)
        val colorProvider: Any? = true,
        // boolean | FoldingRangeProviderOptions |
        // (FoldingRangeProviderOptions & TextDocumentRegistrationOptions & StaticRegistrationOptions)
        val foldingRangeProvider: Any? = true,
        val executeCommandProvider: ExecuteCommandOptions? = ExecuteCommandOptions(),
        val workspace: ServerWorkspaceOptions = ServerWorkspaceOptions(),
        val experimental: Any? = null
)