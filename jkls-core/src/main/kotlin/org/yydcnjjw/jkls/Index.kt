package org.yydcnjjw.jkls

import com.sun.source.tree.Tree

enum class ModifierType {
    PUBLIC,
    PRIVATE,
    PROTECT,
}

//enum class Type {
//
//}

interface IdentifierId {
    val id: Long
}

interface DocumentInfo {
    val desc: String?
    val comment: String?
    val hover: String?
}

open class Type(
        typeName: String,
        modifiers: Set<ModifierType>
) : IdentifierId {
    override val id: Long
        get() = this.hashCode().toLong()
}

class ClassType(
        typeName: String,
        modifiers: Set<ModifierType>,
        typeParameters: Set<String>?,
        extends: Set<IdentifierId>,
        implements: Set<IdentifierId>,
        vars: Set<IdentifierId>,
        methods: Set<IdentifierId>
) : Type(typeName, modifiers)

class MethodType(
        typeName: String,
        modifiers: Set<ModifierType>,
        typeParameters: Set<String>,
        parameters: Set<IdentifierId>
) : Type(typeName, modifiers)

//class Variable(
//        type: ID,
//        modifiers: Set<ModifierType>,
//        comment: String?,
//        hover: String?
//): Type(name, modifiers, comment, hover)

//data class IndexType(
//)

//data class IndexFile(
//        val languageId: String, //
//        val path: String,
//        val type: Map<ID, IndexType>,
//        val imports: Set<ID> // import package
//)

enum class VariableType {
    PARAMETERIZED, // type <typeArguments>
    PRIMITIVE,     // int long ...
    IDENTIFIER,    // class
    ARRAY,
    UNDEFINED,
}

fun Tree.Kind.asVariableTypeOf(): VariableType {
    return when (this) {
        Tree.Kind.PARAMETERIZED_TYPE -> VariableType.PARAMETERIZED
        Tree.Kind.PRIMITIVE_TYPE -> VariableType.PRIMITIVE
        Tree.Kind.IDENTIFIER -> VariableType.IDENTIFIER
        Tree.Kind.ARRAY_TYPE -> VariableType.ARRAY
        else -> {
            VariableType.UNDEFINED
        }
    }
}

data class VariableIndex(
        val type: VariableType?,
        val name: String,
        val ref: IdentifierId?,
        override val desc: String?,
        override val comment: String?,
        override val hover: String?
): DocumentInfo