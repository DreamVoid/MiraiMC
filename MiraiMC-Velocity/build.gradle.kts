import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow")
}

repositories {
    maven(url = "https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":MiraiMC-Base"))
    compileOnly("com.velocitypowered:velocity-api:3.1.0")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.0")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
}

tasks.withType<ShadowJar> {
    val libs = rootProject.extra["shadowLibrariesPackage"].toString()
    archiveClassifier.set("")
    destinationDirectory.set(file("${rootProject.rootDir}/build/libs"))
    minimize()

    relocate("com.zaxxer",  "$libs.com.zaxxer")
    relocate("org.apache", "$libs.org.apache")

    dependencies {
        exclude(dependency("org.jetbrains:annotations"))
    }

    exclude("META-INF/*")
}


val targetJavaVersion = 11
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion

}

tasks.withType<JavaCompile> {
    if (JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

// 替换代码中的变量
val templateSource = file("src/main/templates")
val templateDest = layout.buildDirectory.dir("generated/sources/templates")
val generateTemplates = tasks.create("generateTemplates", Copy::class)
generateTemplates.run {
    val props = mapOf(
        "version" to project.version
    )
    inputs.properties(props)

    from(templateSource)
    into(templateDest)
    props.forEach { expand(Pair(it.key, it.value)) }
}

sourceSets.main.get().java { srcDir(generateTemplates.outputs) }

tasks.getByName<JavaCompile>("compileJava").dependsOn(generateTemplates)
