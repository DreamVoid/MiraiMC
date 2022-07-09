import java.net.URI
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

/*
 如需发布，需要在本机用户的 GRADLE_USER_HOME 创建 gradle.properties 并写下如下内容

 # maven 账户信息
 mavenUsername=
 mavenPassword=
 # 签名信息
 signing.keyId=
 signing.password=
 signing.secretKeyRingFile=
 */
plugins {
    java
    signing
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
}

rootProject.let {
    group = "io.github.dreamvoid"
    version = "1.7-rc2"
    description = "Mirai for Minecraft server"
    extra["shadowLibrariesPackage"] = "me.dreamvoid.miraimc.libraries"
    extra["shadowPath"] = File(rootProject.rootDir, "shadow").absolutePath
}
val miraiVersion = "2.11.1"

subprojects {
    plugins.apply("java")
    plugins.apply("maven-publish")
    plugins.apply("com.github.johnrengelman.shadow")

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
        compileOnly("net.mamoe:mirai-core-api-jvm:$miraiVersion")
        compileOnly("net.mamoe:mirai-console:$miraiVersion:all")

        compileOnly("org.apache.logging.log4j:log4j-core:2.17.2")
        implementation("com.zaxxer:HikariCP:4.0.3")?.apply { if (project.name == "MiraiMC-Base") because("maven") }
        compileOnly("com.google.guava:guava:31.1-jre")?.apply { if (project.name == "MiraiMC-Base") because("maven:provided") }
        implementation("commons-codec:commons-codec:1.15")?.apply { if (project.name == "MiraiMC-Base") because("maven:compile") }
        implementation("org.apache.httpcomponents:httpclient:4.5.13")?.apply { if (project.name == "MiraiMC-Base") because("maven") }
        compileOnly("com.google.code.gson:gson:2.9.0")?.apply { if (project.name == "MiraiMC-Base") because("maven:provided") }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "8"
        targetCompatibility = "8"
        options.encoding = "UTF-8"
    }

    tasks.getByName<Javadoc>("javadoc") {
        description = "Generates project-level javadoc for use in -javadoc jar"

        options {
            memberLevel = JavadocMemberLevel.PROTECTED
            header = project.name
            encoding = "UTF-8"
        }
        // 排除的包列表
        listOf (
            "me.dreamvoid.miraimc.webapi:",
            "me.dreamvoid.miraimc.webapi.*:",
            "me.dreamvoid.miraimc.internal:",
            "me.dreamvoid.miraimc.internal.*:",
            "me.dreamvoid.miraimc.bukkit:",
            "me.dreamvoid.miraimc.bukkit.commands:",
            "me.dreamvoid.miraimc.bukkit.commands.*:",
            "me.dreamvoid.miraimc.bukkit.utils:",
            "me.dreamvoid.miraimc.bukkit.utils.*:",
            "me.dreamvoid.miraimc.bungee:",
            "me.dreamvoid.miraimc.bungee.commands:",
            "me.dreamvoid.miraimc.bungee.commands.*:",
            "me.dreamvoid.miraimc.bungee.utils:",
            "me.dreamvoid.miraimc.bungee.utils.*:",
            "me.dreamvoid.miraimc.nukkit:",
            "me.dreamvoid.miraimc.nukkit.commands:",
            "me.dreamvoid.miraimc.nukkit.commands.*:",
            "me.dreamvoid.miraimc.nukkit.utils:",
            "me.dreamvoid.miraimc.nukkit.utils.*:",
            "me.dreamvoid.miraimc.sponge:",
            "me.dreamvoid.miraimc.sponge.commands:",
            "me.dreamvoid.miraimc.sponge.commands.*:",
            "me.dreamvoid.miraimc.sponge.utils:",
            "me.dreamvoid.miraimc.sponge.utils.*:",
            "me.dreamvoid.miraimc.velocity:",
            "me.dreamvoid.miraimc.velocity.commands:",
            "me.dreamvoid.miraimc.velocity.commands.*:",
            "me.dreamvoid.miraimc.velocity.utils:",
            "me.dreamvoid.miraimc.velocity.utils.*"
        ).forEach { exclude(it) }

        // 如需调试请移除以下三行
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

    tasks.create<Jar>("javadocJar") {
        dependsOn("javadoc")
        destinationDirectory.set(file("${rootProject.rootDir}/build/libs/javadoc"))
        archiveBaseName.set(project.name)
        archiveClassifier.set("javadoc")
        from((tasks["javadoc"] as Javadoc).destinationDir)
    }

    tasks.create<Jar>("sourceJar") {
        archiveClassifier.set("source")
        destinationDirectory.set(file("${rootProject.rootDir}/build/libs/source"))
        val specialTask = tasks.findByName("generateTemplates") as Copy?
        if (specialTask != null) dependsOn(specialTask)
        from(sourceSets.main.get().allSource.srcDirs)
        from(specialTask?.destinationDir)
    }

    tasks.withType<ShadowJar> {
        archiveClassifier.set("")
        destinationDirectory.set(file("${rootProject.rootDir}/build/libs"))
        minimize()

        dependencies {
            exclude(dependency("org.jetbrains:annotations"))
        }
        exclude("META-INF/*")
    }

    publishing {
        publications.create<MavenPublication>(project.name.replace("-", "")) {
            groupId = rootProject.group.toString()
            artifactId = project.name
            version = rootProject.version.toString()
            description = rootProject.description

            // 添加发布前构建任务
            artifact(project.tasks["shadowJar"])
            artifact(project.tasks["javadocJar"])
            artifact(project.tasks["sourceJar"])

            pom {
                name.set(rootProject.name)
                description.set("Mirai for Minecraft server")
                url.set("https://github.com/DreamVoid/MiraiMC")

                properties.put("maven.compiler.source", "8")
                properties.put("maven.compiler.target", "8")

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
            // Gradle 不能将构建脚本生成为 maven 的子模块 pom，因此需要
            // 处理依赖，添加了 .because("maven") 的依赖都将被添加到 pom
            pom.withXml {
                val depends = asNode().appendNode("dependencies")
                project.configurations.implementation.get().allDependencies.toMutableList()
                    .also { it.addAll(project.configurations.compileOnly.get().allDependencies) }
                    .filterIsInstance<ModuleDependency>()
                    .filter { it.reason?.startsWith("maven") ?: false }
                    .forEach {
                    val scope = it.reason?.substring(5) ?: ":"
                    val depend = depends.appendNode("dependency")
                    depend.appendNode("groupId", it.group)
                    depend.appendNode("artifactId", it.name)
                    depend.appendNode("version", it.version)
                    if (scope.length > 1) depend.appendNode("scope", scope.substring(1))
                    if (it.excludeRules.size > 0) {
                        val exclusions = depend.appendNode("exclusions")
                        it.excludeRules.forEach { ex ->
                            val exclusion = exclusions.appendNode("exclusion")
                            exclusion.appendNode("groupId", ex.group)
                            exclusion.appendNode("artifactId", ex.module)
                        }
                    }
                }
            }

            rootProject.signing.sign(this)
        }

        publishing {
            repositories {
                val mavenUsername :String? by project
                val mavenPassword :String? by project

                repositories {
                    maven {
                        name = "sonatype"
                        url = URI("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                        credentials {
                            username = mavenUsername
                            password = mavenPassword
                        }
                    }
                    maven {
                        name = "sonatypeSnapshot"
                        url = URI("https://oss.sonatype.org/content/repositories/snapshots/")
                        credentials {
                            username = mavenUsername
                            password = mavenPassword
                        }
                    }
                }
            }
        }
    }
}

val javadocJar = tasks.create("javadocJar") {
    subprojects.forEach { dependsOn(it.tasks["javadocJar"]) }
}
val sourceJar = tasks.create("sourceJar") {
    subprojects.forEach { dependsOn(it.tasks["sourceJar"]) }
}

// 构建一切
tasks.create("buildUp") {
    subprojects.forEach { dependsOn(it.tasks["shadowJar"]) }
    finalizedBy(javadocJar)
    finalizedBy(sourceJar)
}
