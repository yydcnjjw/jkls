package org.yydcnjjw.jkls.project

import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import org.gradle.tooling.model.build.BuildEnvironment
import org.yydcnjjw.gradle.plugin.CustomModel
import java.io.File

open class GradleTools(
    projectDir: String

){
    // protected val task:

    protected val gradleProject: ProjectConnection
    val buildEnv: BuildEnvironment

    init {
        gradleProject = GradleConnector.newConnector()
            .forProjectDirectory(File(projectDir))
            .connect()
        buildEnv = gradleProject.getModel(BuildEnvironment::class.java)
        val modelBuilder = gradleProject.model(CustomModel::class.java)
        val customModel = modelBuilder
                .withArguments("--init-script", "/home/yydcnjjw/workspace/code/project/jkls/init.gradle")
                .setStandardOutput(System.out)
                .get()
        println(customModel.dependencies)
    }

    fun getDependencies() {
        val os = System.out
        gradleProject.newBuild()
            .setStandardOutput(os)
            .forTasks("dependencies")
            .run()
    }

    fun close() {
        gradleProject.close()
    }
}
