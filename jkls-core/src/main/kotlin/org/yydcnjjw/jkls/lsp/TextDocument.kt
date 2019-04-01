package org.yydcnjjw.jkls.lsp


// foo://example.com:8042/over/there?name=ferret#nose
// \_/   \______________/\_________/ \_________/ \__/
//  |           |            |            |        |
//scheme     authority       path        query   fragment
//  |   _____________________|__
// / \ /                        \
// urn:example:animal:ferret:nose

data class Position(val line: Long = 0, val character: Long = 0)
data class Range(val start: Position = Position(), val end: Position = Position())

typealias DocumentUri = String
val EOL = listOf("\n", "\r\n", "\r")
data class Location(val uri: DocumentUri, val range: Range)

data class LocationLink(
    val originSelectionRange: Range?,
    val targetUri: String,
    val targetRange: Range,
    val targetSelectRange: Range
)

enum class DiagnosticSeverity(val code: Int) {
    Error(1),
    Warning(2),
    Information(3),
    Hint(4)
}

data class Diagnostic(
    val range: Range,
    val severity: Long?,
    val code: Long?,
    val source: String?,
    val message: String,
    val relatedInformation: List<DiagnosticRelatedInformation>
)

data class DiagnosticRelatedInformation(
    val location: Location,
    val message: String
)

data class Command(
    val title: String,
    val command: String,
    val arguments: List<Any>?
)

data class TextEdit(
    val range: Range,
    val newText: String
)

data class TextDocumentEdit(
    val textDocument: VersionedTextDocumentIdentifier,
    val edits: List<TextEdit>
)

data class TextDocumentItem(
    val uri: DocumentUri,
    val languageId: String,
    val version: Int,
    val text: String
)

abstract class AbstractTextDocumentPositionParams(
    open val textDocument: TextDocumentIdentifier,
    open val position: Position
)

abstract class AbstractTextDocumentIdentifier(
    open val uri: DocumentUri
)

data class TextDocumentIdentifier(
    override val uri: DocumentUri
): AbstractTextDocumentIdentifier(uri)

data class VersionedTextDocumentIdentifier(
    override val uri: DocumentUri,
    val version: Int
) : AbstractTextDocumentIdentifier(uri)

data class DocumentFilter(
    val languageId: String?,
    val scheme: String?,
    val pattern: String?
)

typealias DocumentSelector = List<DocumentFilter>

enum class MarkupKind(val kind: String) {
    Plaintext("plaintext"),
    Markdown("markdown")
}

data class MarkupContent(
    val kind: MarkupKind,
    val value: String
)
