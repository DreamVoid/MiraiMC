import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    `maven-publish`
}

repositories {
    maven(url = "https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    implementation(project(":MiraiMC-Base"))
    compileOnly("net.md-5:bungeecord-api:1.17-R0.1-SNAPSHOT")?.because("maven")
    compileOnly("org.apache.maven.plugins:maven-assembly-plugin:3.3.0")?.because("maven")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")?.because("maven")
}

tasks.withType<ShadowJar> {
    val libs = rootProject.extra["shadowLibrariesPackage"].toString()

    relocate("com.zaxxer",  "$libs.com.zaxxer")
    relocate("org.apache", "$libs.org.apache")
    /* ？？？怎么会有slf4j？？？ */
    relocate("org.slf4j", "$libs.org.slf4j")
}