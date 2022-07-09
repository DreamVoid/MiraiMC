
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    // 原来的 MiraiMC-Base 没有打包依赖，所以无需打包任何东西
    dependencies { exclude { true } }
}