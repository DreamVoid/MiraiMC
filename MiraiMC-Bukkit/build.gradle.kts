import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    implementation(project(":MiraiMC-Base"))
    compileOnly("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT")
    compileOnly("com.google.guava:guava:31.1-jre")
}

tasks.withType<ShadowJar> {
    val libs = rootProject.extra["shadowLibrariesPackage"].toString()
    archiveClassifier.set("")
    destinationDirectory.set(file("${rootProject.rootDir}/build/libs"))

    relocate("com.zaxxer",  "$libs.com.zaxxer")
    relocate("org.apache", "$libs.org.apache")
    relocate("org.slf4j", "$libs.org.slf4j")

    dependencies {
        exclude(dependency("org.jetbrains:annotations"))
        // 如果需要使用 kotlin，请移除下面这行
        exclude { it.moduleGroup.startsWith("org.jetbrains.kotlin") }
    }

    exclude("META-INF/*")
}
