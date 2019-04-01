package org.yydcnjjw.jkls.lsp.message

import org.yydcnjjw.jkls.lsp.*

enum class LanguageFeaturesMethod(val value: String) {
    COMPLETION("textDocument/completion")
}

/**
 * Completion Request
 * @method 'textDocument/completion'
 * @param 'CompletionParams'
 *
 * @Options 'CompletionRegistrationOptions'
 * Response
 * @result 'CompletionItem[] | CompletionList | null'
 */

data class CompletionParams(
    override val textDocument: TextDocumentIdentifier,
    override val position: Position,
    val context: CompletionContext
) : AbstractTextDocumentPositionParams(textDocument, position)

enum class CompletionTriggerKind(val kind: Int) {
    Invoked(1),
    TriggerCharacter(2),
    TriggerForIncompleteCompletions(3)
}

data class CompletionContext(
    val triggerKind: CompletionTriggerKind,
    val triggerCharacter: String?
)

data class CompletionList(
    val isIncomplete: Boolean,
    val items: List<CompletionItem>
)

enum class InsertTextFormat(val value: Int) {
    PlainText(1),
    Snippet(2)
}

data class CompletionItem(
    val label: String,
    val kind: CompletionItemKind?,
    val detail: String?,
    // string | MarkupContent
    val documentation: Any?,
    val deprecated: Boolean?,
    val preselect: Boolean?,
    val sortText: String?,
    val filterText: String?,
    val insertText: String?,
    val insertTextFormat: InsertTextFormat?,
    val textEdit: TextEdit?,
    val additionalTextEdits: List<TextEdit>,
    val commitCharacters: List<String>?,
    val command: Command?,
    val data: Any?
)

enum class CompletionItemKind(val kind: Int) {
    Text(1),
    Method(2),
    Function(3),
    Constructor(4),
    Field(5),
    Variable(6),
    Class(7),
    Interface(8),
    Module(9),
    Property(10),
    Unit(11),
    Value(12),
    Enum(13),
    Keyword(14),
    Snippet(15),
    Color(16),
    File(17),
    Reference(18),
    Folder(19),
    EnumMember(20),
    Constant(21),
    Struct(22),
    Event(23),
    Operator(24),
    TypeParameter(25)
}

data class CompletionRegistrationOptions(
    override val documentSelector: DocumentSelector?,
    val triggerCharacters: List<String>?,
    val resolveProvider: Boolean?
): TextDocumentRegistrationOptions(documentSelector)

/**
 * Document Symbols Request
 * @method 'textDocument/documentSymbol'
 * @param 'DocumentSymbolParams'
 *
 * Response
 * @result 'DocumentSymbol[] | SymbolInformation[] | null'
 */
interface EnumKind<K, T> {
    fun from(value: K): T?
}

enum class SymbolKind(
        val kind: Int
): EnumKind<Int, SymbolKind> {
    File(1),
    Module(2),
    Namespace(3),
    Package(4),
    Class(5),
    Method(6),
    Property(7),
    Field(8),
    Constructor(9),
    Enum(10),
    Interface(11),
    Function(12),
    Variable(13),
    Constant(14),
    String(15),
    Number(16),
    Boolean(17),
    Array(18),
    Object(19),
    Key(20),
    Null(21),
    EnumMember(22),
    Struct(23),
    Event(24),
    Operator(25),
    TypeParameter(26);

    companion object {
        private val map = SymbolKind.values().associateBy(SymbolKind::kind)
    }

    override fun from(value: Int)= map[value]
}
