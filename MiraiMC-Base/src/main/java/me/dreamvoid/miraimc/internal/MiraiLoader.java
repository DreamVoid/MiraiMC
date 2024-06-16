package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.LifeCycle;
import me.dreamvoid.miraimc.internal.config.PluginConfig;
import me.dreamvoid.miraimc.internal.webapi.Info;
import me.dreamvoid.miraimc.internal.webapi.Version;

import java.io.IOException;

public class MiraiLoader {
    /**
     * 加载最新版Mirai Core
     */
    public static void loadMiraiCore() {
        loadMiraiCore("latest");
    }

    public static String getStableVersion() {
        try {
            return Info.init().mirai.get("stable");
        } catch (IOException e){
            Utils.getLogger().warning("Unable to get mirai stable version from remote server, try to use latest. Reason: " + e);
            return "latest";
        }
    }

    public static String getStableVersion(String PluginVersion) {
        try {
            String mirai = Info.init().mirai.get("stable"); // 最终获取到的 mirai 版本，先用stable占位

            try {
                int ver = Version.init().versions.getOrDefault(PluginVersion, 0); // 插件当前版本号
                int temp = -1; // 用于取最大值

                for (String s : Info.init().mirai.keySet()) {
                    if (s.equalsIgnoreCase("stable")) {
                        continue;
                    }

                    if (ver <= Integer.parseInt(s)) {
                        if(Integer.parseInt(s) > temp) {
                            mirai = Info.init().mirai.get(s);
                            temp = Integer.parseInt(s);
                        }
                    }
                }
            } catch (Exception ignored) {}

            return mirai;
        } catch (IOException e){
            Utils.getLogger().warning("Failed to get stable version, fallback to latest. Reason: " + e);
            return "latest";
        }
    }


    /**
     * 加载指定版本的Mirai Core
     * @param version 版本
     */
    public static void loadMiraiCore(String version) {
        try {
            LifeCycle.getPlatform().getLibraryLoader().loadLibraryMaven(
                    "net.mamoe",
                    "mirai-core-all",
                    (version.equalsIgnoreCase("latest") ? LifeCycle.getPlatform().getLibraryLoader().getLibraryVersion("net.mamoe", "mirai-core-all", PluginConfig.General.MavenRepoUrl) : version),
                    PluginConfig.General.MavenRepoUrl,
                    "-all.jar", // mirai 特性
                    Utils.getMiraiDir().toPath().resolve("libs")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
