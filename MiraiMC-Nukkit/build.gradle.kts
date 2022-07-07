import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow")
}

repositories {
    maven(url = "https://repo.opencollab.dev/maven-releases")
    maven(url = "https://repo.opencollab.dev/maven-snapshots")
}

dependencies {
    implementation(project(":MiraiMC-Base"))
    compileOnly("cn.nukkit:nukkit:1.0-SNAPSHOT")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
}

tasks.withType<ShadowJar> {
    val libs = rootProject.extra["shadowLibrariesPackage"].toString()
    archiveClassifier.set("")

    relocate("com.zaxxer",  "$libs.com.zaxxer")
    relocate("org.apache", "$libs.org.apache")
    /* ？？？怎么会有slf4j？？？ */
    relocate("org.slf4j", "$libs.org.slf4j")

    dependencies {
        exclude(dependency("org.jetbrains:annotations"))
        // 如果需要使用 kotlin，请移除下面这行
        exclude { it.moduleGroup.startsWith("org.jetbrains.kotlin") }
    }

    exclude("META-INF/*")
}
