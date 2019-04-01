package org.yydcnjjw.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.tooling.model.Model
import org.gradle.tooling.provider.model.ToolingModelBuilder
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry
import java.io.Serializable
import java.util.*
import javax.inject.Inject


class JKLSPlugin
@Inject constructor(
    private val registry: ToolingModelBuilderRegistry
) : Plugin<Project> {
    override fun apply(target: Project): Unit = target.run {
        registry.register(CustomToolingModelBuilder())
    }

    private class CustomToolingModelBuilder : ToolingModelBuilder {
        override fun canBuild(modelName: String): Boolean {
            return modelName == CustomModel::class.java.name
        }

        override fun buildAll(modelName: String, project: Project): Any {
            val list: MutableList<String> = mutableListOf()
            println(project.configurations.size)
            project.allprojects.forEach {
                println("project: ${it.name}")
                it.configurations.forEach {
                    conf ->
                    if (conf.isCanBeResolved) {
                        println("configuration: ${conf.name}")
                        conf.resolvedConfiguration.files.forEach {
                            file ->
                            println(file.name)
                        }
                    }
                }
            }
            return DefaultModel(list.toList())
        }
    }
}

//interface CustomModel : Model {
//    val dependencies: List<String>
//    fun getTasks(): List<String>
//}
//
//class DefaultModel(
//        override val dependencies: List<String>
//) : CustomModel, Serializable {
//
//    override fun getTasks(): List<String> {
//        return Arrays.asList(":1", ":2", ":3")
//    }
//
//}
