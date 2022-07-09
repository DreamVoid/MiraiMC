import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    destinationDirectory.set(file("${rootProject.rootDir}/build/libs"))
    // 原 maven 仓库里 MiraiMC-Base 没有打包任何依赖，所以忽略所有依赖
    dependencies { exclude { true } }
}
