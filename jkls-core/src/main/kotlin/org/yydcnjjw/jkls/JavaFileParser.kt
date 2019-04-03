package org.yydcnjjw.jkls

import com.sun.source.tree.*
import com.sun.source.util.JavacTask
import com.sun.source.util.TreeScanner
import com.sun.source.util.Trees
import org.yydcnjjw.jkls.lsp.Position
import org.yydcnjjw.jkls.lsp.Range
import org.yydcnjjw.jkls.project.*
import java.io.File
import java.util.*
import javax.tools.JavaCompiler
import javax.tools.StandardJavaFileManager
import javax.tools.ToolProvider

class JavaFileParser(
        javaFiles: List<File>
) {
    private val compiler: JavaCompiler = ToolProvider.getSystemJavaCompiler()
    private val trees: Trees
    private val javacTask: JavacTask
    private val fileManger: StandardJavaFileManager

    init {
        if (javaFiles.isEmpty()) {
            throw Exception("empty file list")
        }

        fileManger = compiler
                .getStandardFileManager({
                    // Logger(LogLevel.INFO, it.toString())
                }, null, null)
        val compilationUnits = fileManger.getJavaFileObjectsFromFiles(javaFiles)

        val task = compiler
                .getTask(null, fileManger, null, options(), null, compilationUnits)
        trees = Trees.instance(task)

        if (task is JavacTask) javacTask = task
        else throw TypeCastException("JavacTask")
    }

    fun parse(): List<IndexFile> {
        // val docTrees = DocTrees.instance(task)

        val indexFiles = mutableListOf<IndexFile>()

        val compilationUnitTrees = javacTask.parse()
        compilationUnitTrees.forEach { unit ->
            val indexFile = IndexFile(unit.sourceFile.name, LanguageType.JAVA)

            // first index imports
            unit.imports.forEach {
//                Logger(LogLevel.INFO, it.qualifiedIdentifier.toString())
            }

            // val docTree = docTrees.getDocCommentTree(unit.sourceFile)
            // println(docTree)
            // DocumentTreeScanner().scan(docTree, null)

            unit.typeDecls.forEach {
                when (it.kind) {
                    Tree.Kind.ENUM,
                    Tree.Kind.CLASS,
                    Tree.Kind.INTERFACE,
                    Tree.Kind.ANNOTATION_TYPE -> {
                        val classTree = it as ClassTree

                        Logger(LogLevel.DEBUG, "extend: ${classTree.extendsClause}")

                        val interfaces = mutableListOf<ID>()

                        val methods = mutableListOf<IndexMethodSymbol>()
                        val fields = mutableListOf<IndexVarSymbol>()
                        classTree.members.forEach {
                            memberTree ->
                            // Logger(LogLevel.DEBUG, "${memberTree.kind}, $memberTree")
                            if (memberTree.kind == Tree.Kind.VARIABLE) {
                                val variableTree = memberTree as VariableTree
                            }

                            if (memberTree.kind == Tree.Kind.METHOD) {
                                val methodTree = memberTree as MethodTree
                                val variables = mutableListOf<String>()
                                MethodTreeScanner().scan(methodTree, variables)
                                Logger(LogLevel.DEBUG, "${memberTree.name}\n$variables")
                            }
                        }


                        indexFile.decl.add(IndexClass(
                                classTree.toString(),
                                getTreeRange(unit, classTree),
                                "", // document
                                classTree.simpleName.toString(),
                                IDGenerate.UNDEFINE,
                                interfaces,
                                fields,
                                methods
                        ))

                    }
                    else -> throw Exception("Not support global decl")
                }
            }
            indexFiles.add(indexFile)
        }

        // Logger(LogLevel.INFO, indexFiles.toString())

        fileManger.close()
        return indexFiles
    }

    private fun getTreeRange(file: CompilationUnitTree, tree: Tree): Range {
        val start = trees.sourcePositions.getStartPosition(file, tree)
        val end = trees.sourcePositions.getEndPosition(file, tree)
        return Range(
                Position(file.lineMap.getLineNumber(start), file.lineMap.getColumnNumber(start)),
                Position(file.lineMap.getLineNumber(end), file.lineMap.getColumnNumber(end))
        )
    }

    private fun options(): List<String> {
        val list = ArrayList<String>()

        Collections.addAll(list, "-proc:none")
        Collections.addAll(list, "-g")

        Collections.addAll(
                list,
                "-Xlint:cast",
                "-Xlint:deprecation",
                "-Xlint:empty",
                "-Xlint:fallthrough",
                "-Xlint:finally",
                "-Xlint:path",
                "-Xlint:unchecked",
                "-Xlint:varargs",
                "-Xlint:static")

        return list
    }

}

/**
 *
 */
class MethodTreeScanner(

) : TreeScanner<Unit, MutableList<String>>() {
    override fun visitVariable(node: VariableTree, p: MutableList<String>) {
        super.visitVariable(node, p)

        val variableType =
                node.type?.kind?.asVariableTypeOf() ?: VariableType.UNDEFINED
        p.add(node.name.toString())
        // Logger(LogLevel.DEBUG, "$node\n$variableType, ${node.name}")
    }

//    override fun visitLambdaExpression(node: LambdaExpressionTree, p: MutableList<String>) {
        // super.visitLambdaExpression(node, p)
//        scan(node.body, null)
//    }
}

//class DocumentTreeScanner: DocTreeScanner<Unit, Unit>() {
//    override fun visitDocComment(node: DocCommentTree?, p: Unit?) {
//        super.visitDocComment(node, p)
//    }
//}