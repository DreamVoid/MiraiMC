import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
}

dependencies {
    compileOnly(project(":MiraiMC-Base"))?.because("maven:compile")
    compileOnly(project(":MiraiMC-Bukkit"))?.because("maven:compile")
    compileOnly(project(":MiraiMC-Bungee"))?.because("maven:compile")
    compileOnly(project(":MiraiMC-Nukkit"))?.because("maven:compile")
    compileOnly(project(":MiraiMC-Sponge"))?.because("maven:compile")
    compileOnly(project(":MiraiMC-Velocity"))?.because("maven:compile")
}

tasks.withType<ShadowJar> {
    // MiraiMC-Integration 仅发布用于开发者引用依赖，无需打包任何东西
    dependencies { exclude { true } }
}
