package org.yydcnjjw.jkls.main

import kotlinx.coroutines.runBlocking
import org.gradle.tooling.GradleConnectionException
import org.gradle.tooling.UnsupportedVersionException
import org.yydcnjjw.jkls.LanguageServer
import org.yydcnjjw.jkls.project.GradleTools

fun main(args: Array<String>) = runBlocking {
    LanguageServer(System.`in`, System.out).start()

//    javaCompilerTest()
//    gradleProjectTest()

//    val jar = JarFile("")
//    val jarEntrys = jar.entries()
//    while (jarEntrys.hasMoreElements()) {
//        val jarEntry = jarEntrys.nextElement()
//        if (jarEntry.name.endsWith(".class")) {
//            jar.getInputStream(jarEntry)
//
//        }
//    }
}

fun gradleProjectTest() {
    try {
        val project =
            GradleTools("/home/yydcnjjw/workspace/code/project/jkls")
        project.close()
    } catch (e: UnsupportedVersionException) {
        e.printStackTrace()
    } catch (e: GradleConnectionException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
    }
}