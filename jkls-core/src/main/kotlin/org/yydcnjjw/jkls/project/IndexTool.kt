package org.yydcnjjw.jkls.project

import org.yydcnjjw.jkls.lsp.Range
import org.yydcnjjw.jkls.lsp.message.SymbolKind
import java.util.concurrent.atomic.AtomicLong

object IDGenerate{
    const val UNDEFINE = -1L
    private val id = AtomicLong(0)
    operator fun invoke(): Long {
        return id.getAndIncrement()
    }
}

typealias ID = Long

data class IndexImport(
        val range: Range,
        val path: String
)

abstract class IndexSymbol(
        open val label: String,
        open val kind: SymbolKind,
        open val range: Range = Range(),
        open val detail: String
) {
    val id = IDGenerate()
}

interface DeclRef {
    val decl: ID
    val refs: List<ID>
}

data class IndexVarSymbol(
        override val label: String,
        override val kind: SymbolKind,
        override val range: Range,
        override val detail: String
): IndexSymbol(label, SymbolKind.Variable, range, detail), DeclRef {
    override val decl = id
    override val refs: List<ID> = mutableListOf()
}

data class IndexMethodSymbol(
        override val label: String,
        override val range: Range,
        override val detail: String
): IndexSymbol(label, SymbolKind.Method, range, detail), DeclRef {
    override val decl: ID = id
    override val refs: List<ID> = mutableListOf()
}

data class IndexClass (
        override val label: String,
        override val range: Range,
        override val detail: String,
        val className: String,
        val superClass: ID, // IndexClass
        val interfaces: List<ID>, // IndexClass
        val fields: List<IndexVarSymbol>,
        val methods: List<IndexMethodSymbol>
): IndexSymbol(label, SymbolKind.Class, range, detail), DeclRef {
    override val decl: ID = id
    override val refs: List<ID> = mutableListOf()
}

data class IndexFile(
        val path: String,
        val language: LanguageType,
        val decl: MutableList<IndexSymbol> = mutableListOf(),
        val import: MutableList<IndexImport> = mutableListOf()
)

enum class LanguageType {
    UNkNOWN,
    JAVA,
    KOTLIN
}