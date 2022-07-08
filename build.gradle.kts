import java.net.URI
import java.util.Properties
import java.io.FileInputStream

/*
 如需发布，需要创建 local.properties

 # maven 账户信息
 mavenUsername=
 mavenPassword=
 # 签名信息
 signing.keyId=
 signing.password=
 signing.secretKeyRingFile=
 */
val local = Properties().apply { load(FileInputStream("local.properties")) }
val projects = listOf("Bukkit", "Bungee", "Nukkit", "Sponge", "Velocity")

plugins {
    val kotlinVersion = "1.5.10"
    val shadowVersion = "7.1.2"

    java
    `maven-publish`
    signing
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion apply false
    id("com.github.johnrengelman.shadow") version shadowVersion apply false
}

rootProject.let {
    group = "io.github.dreamvoid"
    version = "1.7-rc2"
    description = "Mirai for Minecraft server"
    extra["shadowLibrariesPackage"] = "me.dreamvoid.miraimc.libraries"
    extra["shadowPath"] = File(rootProject.rootDir, "shadow").absolutePath
}
val miraiVersion = "2.12.0"

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

    tasks.getByName<Javadoc>("javadoc") {
        description = "Generates project-level javadoc for use in -javadoc jar"

        options {
            memberLevel = JavadocMemberLevel.PUBLIC
            header = project.name
            encoding = "UTF-8"
        }

        // 重定向到父模块的 javadoc 生成目录
        setDestinationDir(File(rootProject.rootDir, "build/docs/javadoc"))

        logging.captureStandardError(LogLevel.INFO)
        logging.captureStandardOutput(LogLevel.INFO)
        isFailOnError = false
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

val javadocJar = tasks.create<Jar>("javadocJar") {
    dependsOn("javadoc")
    archiveClassifier.set("javadoc")
    from(tasks.getByName<Javadoc>("javadoc").destinationDir)
}

val sharedPom = Action<MavenPom> {
    name.set(rootProject.name)
    description.set("Mirai for Minecraft server")
    url.set("https://github.com/DreamVoid/MiraiMC")

    licenses {
        license {
            name.set("GNU Affero General Public License v3.0")
            url.set("https://www.gnu.org/licenses/agpl-3.0.html")
        }
    }
    developers {
        developer {
            name.set("DreamVoid")
            email.set("45266046+DreamVoid@users.noreply.github.com")
            organization.set("DreamVoid")
            organizationUrl.set("https://github.com/DreamVoid")
        }
    }
    scm {
        connection.set("scm:git:git://github.com/DreamVoid/MiraiMC.git")
        developerConnection.set("scm:git:ssh://github.com:DreamVoid/MiraiMC.git")
        url.set("https://github.com/DreamVoid/MiraiMC")
    }
}

// 发布
publishing {
    repositories {
        val mavenUsername = local["mavenUsername"].toString()
        val mavenPassword = local["mavenPassword"].toString()
        repositories {
            maven {
                name = "sonatypeRepository"
                url = URI("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
            }
            maven {
                name = "sonatypeSnapshotRepository"
                url = URI("https://oss.sonatype.org/content/repositories/snapshots/")
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
            }
            // 本地仓库
            mavenLocal()
        }
    }
    val files = file("build/libs/").listFiles()
    publications {
        // 批量添加发布配置
        projects.forEach { sub ->
            create<MavenPublication>(sub) {
                groupId = rootProject.group.toString()
                artifactId = "${rootProject.name}-$sub"
                version = rootProject.version.toString()
                description = rootProject.description

                artifact(files!!.find { it.name.startsWith(artifactId) }!!.absolutePath)
                artifact(javadocJar)

                pom(sharedPom)
            }
        }
    }
}
// 签名
signing {
    //sign(publishing.publications.getByName("maven"))
}
