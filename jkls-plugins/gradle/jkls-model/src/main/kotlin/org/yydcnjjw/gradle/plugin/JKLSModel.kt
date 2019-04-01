package org.yydcnjjw.gradle.plugin

import org.gradle.tooling.model.Model
import java.io.Serializable
import java.util.*

interface CustomModel : Model {
    val dependencies: List<String>
    fun getTasks(): List<String>
}

class DefaultModel(
        override val dependencies: List<String>
) : CustomModel, Serializable {

    override fun getTasks(): List<String> {
        return Arrays.asList(":1", ":2", ":3")
    }

}
