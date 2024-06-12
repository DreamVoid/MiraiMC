package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.MiraiMCPlugin;
import me.dreamvoid.miraimc.internal.webapi.Info;
import me.dreamvoid.miraimc.internal.webapi.Version;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MiraiLoader {
    private static final RemoteRepository mavenCentral = new RemoteRepository.Builder("central","default", MiraiMCConfig.General.MavenRepoUrl).build();
    /**
     * 加载最新版Mirai Core
     */
    public static void loadMiraiCore() throws RuntimeException, IOException, ParserConfigurationException, SAXException {
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
            Utils.getLogger().warning("Unable to get mirai stable version from remote server, try to use latest. Reason: " + e);
            return "latest";
        }
    }


    /**
     * 加载指定版本的Mirai Core
     * @param version 版本
     */
    public static void loadMiraiCore(String version) throws RuntimeException, IOException, ParserConfigurationException, SAXException {
        MiraiMCPlugin.getPlatform().getLibraryLoader().loadLibraryMaven(mavenCentral, new Dependency(new DefaultArtifact("net.mamoe:mirai-core-all:" + (version.equalsIgnoreCase("latest") ? MiraiMCPlugin.getPlatform().getLibraryLoader().getLibraryVersion(mavenCentral, "net.mamoe", "mirai-core-all") : version)), null));
    }
}
