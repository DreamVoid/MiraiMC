plugins {
    val kotlinVersion = "1.5.10"
    val shadowVersion = "7.1.2"

    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    id("com.github.johnrengelman.shadow") version shadowVersion apply false
}

rootProject.group = "io.github.dreamvoid"
rootProject.version = "1.7-rc2"
val miraiVersion = "2.12.0"

rootProject.extra["shadowLibrariesPackage"] = "me.dreamvoid.miraimc.libraries"
rootProject.extra["shadowPath"] = File(rootProject.rootDir, "shadow").absolutePath

allprojects {
    plugins.apply("org.jetbrains.kotlin.jvm")

    project.group = rootProject.group
    project.version = rootProject.version

    repositories {
        maven(url = "https://maven.aliyun.com/repository/central")
        maven(url = "https://maven.aliyun.com/repository/public")
        mavenLocal()
        mavenCentral()
    }
    // 所有项目共用的依赖
    dependencies {
        "compileOnly"("net.mamoe:mirai-core:$miraiVersion")
        "compileOnly"("net.mamoe:mirai-console:$miraiVersion")

        "compileOnly"("org.apache.logging.log4j:log4j-core:2.17.2")
        "implementation"("com.zaxxer:HikariCP:4.0.3")
        "compileOnly"("com.google.guava:guava:31.1-jre")
        "implementation"("commons-codec:commons-codec:1.15")
        "implementation"("org.apache.httpcomponents:httpclient:4.5.13")
        "compileOnly"("com.google.code.gson:gson:2.9.0")
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "8"
        targetCompatibility = "8"
        options.encoding = "UTF-8"
    }

    // 替换资源文件中的变量
    tasks.withType<ProcessResources> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        val props = mapOf(
            "version" to project.version
        )
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("*.yml") {
            props.forEach { expand(Pair(it.key, it.value)) }
        }
    }
}
