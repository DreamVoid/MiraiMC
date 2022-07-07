plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("net.mamoe.mirai-console") version "2.12.0"
}

dependencies {
    implementation("com.zaxxer:HikariCP:4.0.3")
    compileOnly("com.google.guava:guava:31.1-jre")
    implementation("commons-codec:commons-codec:1.15")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    compileOnly("com.google.code.gson:gson:2.9.0")
}

