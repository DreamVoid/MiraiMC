package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.api.MiraiMC;
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
            return Info.get(false).mirai.get("stable");
        } catch (IOException e){
            Utils.getLogger().warning("无法从远程服务器获取 mirai 核心的稳定版本，将改用 latest. 原因: " + e);
            return "latest";
        }
    }

    public static String getStableVersion(String PluginVersion) {
        try {
            String mirai = Info.get(false).mirai.get("stable"); // 最终获取到的 mirai 版本，先用stable占位

            try {
                int ver = Version.get(false).versions.getOrDefault(PluginVersion, 0); // 插件当前版本号
                int temp = -1; // 用于取最大值

                for (String s : Info.get(false).mirai.keySet()) {
                    if (s.equalsIgnoreCase("stable")) {
                        continue;
                    }

                    if (ver <= Integer.parseInt(s)) {
                        if(Integer.parseInt(s) > temp) {
                            mirai = Info.get(false).mirai.get(s);
                            temp = Integer.parseInt(s);
                        }
                    }
                }
            } catch (Exception ignored) {}

            return mirai;
        } catch (IOException e){
            Utils.getLogger().warning("无法获取稳定版本，回退到 latest. 原因: " + e);
            return "latest";
        }
    }


    /**
     * 加载指定版本的Mirai Core
     * @param version 版本
     */
    public static void loadMiraiCore(String version) {
        try {
            MiraiMC.getPlatform().getLibraryLoader().loadLibraryMaven(
                    "net.mamoe",
                    "mirai-core-all",
                    (version.equalsIgnoreCase("latest") ? MiraiMC.getPlatform().getLibraryLoader().getLibraryVersion("net.mamoe", "mirai-core-all", MiraiMC.getConfig().General_MavenRepoUrl) : version),
                    MiraiMC.getConfig().General_MavenRepoUrl,
                    "-all.jar", // mirai 特性
                    Utils.getMiraiDir().toPath().resolve("libs")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
