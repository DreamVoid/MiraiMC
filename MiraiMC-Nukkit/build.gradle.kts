import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    `maven-publish`
}

repositories {
    maven(url = "https://repo.opencollab.dev/maven-releases")
    maven(url = "https://repo.opencollab.dev/maven-snapshots")
}

dependencies {
    implementation(project(":MiraiMC-Base"))
    compileOnly("cn.nukkit:nukkit:1.0-SNAPSHOT")?.because("maven")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")?.because("maven")
}

tasks.withType<ShadowJar> {
    val libs = rootProject.extra["shadowLibrariesPackage"].toString()

    relocate("com.zaxxer",  "$libs.com.zaxxer")
    relocate("org.apache", "$libs.org.apache")
    /* ？？？怎么会有slf4j？？？ */
    relocate("org.slf4j", "$libs.org.slf4j")
}
