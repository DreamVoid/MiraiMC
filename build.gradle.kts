plugins {
    kotlin("jvm") version "1.5.10" apply false
    kotlin("plugin.serialization") version "1.5.10" apply false
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
}

rootProject.group = "io.github.dreamvoid"
rootProject.version = "1.7-rc2"
rootProject.extra["shadowLibrariesPackage"] = "me.dreamvoid.miraimc.libraries"

allprojects {
    group = rootProject.group
    version = rootProject.version
    repositories {
        maven(url = "https://maven.aliyun.com/repository/central")
        maven(url = "https://maven.aliyun.com/repository/public")
        mavenLocal()
        mavenCentral()
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "8"
        targetCompatibility = "8"
        options.encoding = "UTF-8"
    }

    // 替换资源文件中的变量
    tasks.withType<ProcessResources> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        val props = mapOf("version" to project.version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("*.yml") {
            // 屑，不懂为什么不能 expand(props)
            props.forEach {
                expand(Pair(it.key, it.value))
            }
        }
    }
}