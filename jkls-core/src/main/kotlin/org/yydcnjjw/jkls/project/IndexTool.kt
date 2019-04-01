package org.yydcnjjw.jkls.project

import org.yydcnjjw.jkls.lsp.Range
import org.yydcnjjw.jkls.lsp.message.SymbolKind

typealias ID = Long

data class IndexImport(
        val range: Range,
        val path: String
)

data class SymbolRef(
        val id: ID,
        val range: Range = Range(),
        val kind: SymbolKind
)

class IndexMethod(
) {
    val name: String = ""
    val hover: String = ""
    val comments: String = ""
    val vars = listOf<String>()
    val refSymbols = listOf<String>()
}

//class IndexClassField(
//        range: Range,
//        name: String,
//
//): IndexIdentifier(range, name)

data class IndexDecl (
        val range: Range,
        val name: String,
        val id: ID = -1
)

//data class IndexClass (
//        val range: Range,
//        val className: String,
//        val superClass: String,
//        val interfaces: List<String>,
//        val fields: List<String>,
//        val methods: List<String>
//)

data class IndexFile(
        val path: String,
        val language: LanguageType,
        val decl: MutableList<IndexDecl> = mutableListOf()
) {

}

enum class LanguageType {
    UNkNOWN,
    JAVA,
    KOTLIN
}

fun Index(
) {
}