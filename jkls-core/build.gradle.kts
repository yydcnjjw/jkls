import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "org.yydcnjjw"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.3.21"
    application
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

application {
    mainClassName = "org.yydcnjjw.jkls.main.MainKt"
}

tasks.withType<JavaExec>() {
    standardInput = System.`in`
    standardOutput = System.out
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")
    implementation("org.gradle:gradle-tooling-api:+")
    // json parse
    implementation("com.squareup.moshi:moshi-kotlin:+")
    implementation("com.alibaba:fastjson:+")
    runtimeOnly("org.slf4j:slf4j-simple:+")
    
    implementation(project(":jkls-plugins:gradle:jkls-model"))
    // implementation(kotlin("compiler-embeddable"))
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClassName
    }
}
