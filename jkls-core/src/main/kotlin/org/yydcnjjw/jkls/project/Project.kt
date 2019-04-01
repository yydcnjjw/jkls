package org.yydcnjjw.jkls.project

import org.yydcnjjw.jkls.JavaFileParser
import org.yydcnjjw.jkls.LogLevel
import org.yydcnjjw.jkls.Logger
import java.io.File
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.function.BiPredicate

enum class ProjectType {
    GRADLE,
    MAVEN,
    GENERAL
}

abstract class Project(
        var projectDir: URI
) {
    companion object {
        private val projectMarkerFiles = mapOf(
                ProjectType.GRADLE to listOf("build.gradle", "build.gradle.kts"),
                ProjectType.MAVEN to listOf("pom.xml"),
                ProjectType.GENERAL to listOf("jkls.conf.json")
        )

        fun getProject(projectDir: URI): Project {
            val projectType = projectTypeDetect(projectDir)
            return when (projectType) {
                ProjectType.GRADLE -> GradleProject(projectDir)
                else -> GradleProject(projectDir)
            }
        }

        private fun projectTypeDetect(projectDir: URI): ProjectType {
            val projectRoot = File(projectDir)
            if (!projectRoot .isDirectory) {
                throw FileSystemException(projectRoot, null, "It is not a directory")
            }

            var projectType: ProjectType? = null

            val fileList = projectRoot.listFiles() ?: throw ProjectException("Root directory is not some files")

            for (file in fileList) {
                for ((type, files) in projectMarkerFiles) {
                    if (files.find { file.name!!.contentEquals(it) } != null) {
                        projectType = type
                        break
                    }
                }
            }

            if (projectType == null) {
                throw ProjectException("Do not understand the project")
            }

            return projectType
        }
    }

    abstract fun index()
}

class ProjectException(message: String) : Exception(message)

enum class FileType {
    JAVA,
    KOTLIN,
    GENERAL,
    CONFIGURE,
    INDEX
}

class FileEntry(
        val type: FileType,
        val file: File
)

class GradleProject(
        projectDir: URI
) : Project(projectDir) {

    override fun index() {
        val javaFiles = mutableListOf<File>()

        Files.find(Paths.get(projectDir), Int.MAX_VALUE,
            BiPredicate { _, attr ->
                attr.isRegularFile
            }
        ).forEach {
            path ->
            if (path.toString().endsWith(".java")) {
                // Logger(LogLevel.INFO, path.toString())
                javaFiles.add(path.toFile())
            }
        }

        val indexFile = JavaFileParser(
                javaFiles
        ).parse()

        // Logger(LogLevel.INFO, indexFile.toString())
    }
}

