plugins {
    java
    // id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation(project(":MiraiMC-Base"))
    implementation(project(":MiraiMC-Bukkit"))
    implementation(project(":MiraiMC-Bungee"))
    implementation(project(":MiraiMC-Nukkit"))
    implementation(project(":MiraiMC-Sponge"))
    implementation(project(":MiraiMC-Velocity"))
}
