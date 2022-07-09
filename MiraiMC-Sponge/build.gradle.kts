import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    `maven-publish`
}

dependencies {
    implementation(project(":MiraiMC-Base"))
    compileOnly("org.spongepowered:spongeapi:7.2.0")?.because("maven")
    annotationProcessor("org.spongepowered:spongeapi:7.2.0")
}


tasks.withType<ShadowJar> {
    val libs = rootProject.extra["shadowLibrariesPackage"].toString()

    relocate("org.apache", "$libs.org.apache")

    dependencies {
        exclude(dependency("com.zaxxer:HikariCP"))
        exclude(dependency("org.slf4j:slf4j-api"))
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