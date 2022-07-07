import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation(project(":MiraiMC-Base"))
    compileOnly("org.spongepowered:spongeapi:7.2.0")
    annotationProcessor("org.spongepowered:spongeapi:7.2.0")
}

tasks.withType<ShadowJar> {
    val libs = rootProject.extra["shadowLibrariesPackage"].toString()
    archiveClassifier.set("")

    relocate("org.apache", "$libs.org.apache")

    dependencies {
        exclude(dependency("org.jetbrains:annotations"))
        exclude(dependency("com.zaxxer:HikariCP"))
        exclude(dependency("org.slf4j:slf4j-api"))
        // 如果需要使用 kotlin，请移除下面这行
        exclude { it.moduleGroup.startsWith("org.jetbrains.kotlin") }
    }

    exclude("META-INF/*")
}