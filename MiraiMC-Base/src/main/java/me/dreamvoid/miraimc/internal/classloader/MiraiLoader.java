package me.dreamvoid.miraimc.internal.classloader;

import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static me.dreamvoid.miraimc.internal.classloader.LibrariesLoader.*;

public class MiraiLoader {
    /**
     * 加载最新版Mirai Core
     */
    public static void loadMiraiCore() throws RuntimeException, IOException, ParserConfigurationException, SAXException {
        loadMiraiCore(getLibraryVersionMaven("net.mamoe", "mirai-core-all", Config.Gen_MavenRepoUrl.replace("http://","https://"),"release"));
    }

    /**
     * 加载指定版本的Mirai Core
     * @param version 版本
     */
    public static void loadMiraiCore(String version) throws RuntimeException, IOException {
        try {
            // 文件夹
            File MiraiDir;
            if (Config.Gen_MiraiWorkingDir.equals("default")) {
                MiraiDir = new File(Config.PluginDir,"MiraiBot");
            } else {
                MiraiDir = new File(Config.Gen_MiraiWorkingDir);
            }
            File LibrariesDir = new File(MiraiDir,"libs");
            if(!LibrariesDir.exists() && !LibrariesDir.mkdirs()) {
                throw new RuntimeException("Failed to create " + LibrariesDir.getPath());
            }

            loadLibraryClassMaven("net.mamoe", "mirai-core-all", version, "-all", Config.Gen_MavenRepoUrl.replace("http://","https://"), LibrariesDir);
            File writeName = new File(Config.PluginDir, "cache/core-ver");
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(version);
                out.flush();
            }
        } catch (Exception e) {
            Utils.logger.warning("Unable to download mirai core from remote server("+e+"), try to use local core.");
            File writeName = new File(Config.PluginDir, "cache/core-ver");
            if(writeName.exists()) {
                String content = new String(Files.readAllBytes(writeName.toPath()), StandardCharsets.UTF_8);
                if(!content.equals("")){
                    String name = "mirai-core-all" + "-" + content + ".jar"; // 文件名
                    File MiraiDir;
                    if (Config.Gen_MiraiWorkingDir.equals("default")) {
                        MiraiDir = new File(Config.PluginDir,"MiraiBot");
                    } else {
                        MiraiDir = new File(Config.Gen_MiraiWorkingDir);
                    }
                    File LibrariesDir = new File(MiraiDir,"libs");
                    File coreFile = new File(LibrariesDir, name);
                    loadLibraryClassLocal(coreFile);
                } else {
                    Utils.logger.warning("Unable to use local core.");
                    throw e;
                }
            } else {
                Utils.logger.warning("Unable to use local core.");
                throw e;
            }
        }
    }
}
