plugins {
    `kotlin-dsl`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "org.yydcnjjw.plugin"
version = "1.0"

gradlePlugin {
    plugins {
        register("jkls") {
            id = "org.yydcnjjw.jkls"
            implementationClass = "org.yydcnjjw.gradle.plugin.JKLSPlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())
    implementation(project(":jkls-plugins:gradle:jkls-model"))
}

publishing {
    repositories {
        maven {
            url = uri("$buildDir/repo")
        }
    }
    publications {
        create("shadow", MavenPublication::class.java) {
            group = "org.yydcnjjw.plugin"
            artifactId = "shadow-jkls"
            version = "1.0"
            project.shadow.component(this)
        }
    }
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}