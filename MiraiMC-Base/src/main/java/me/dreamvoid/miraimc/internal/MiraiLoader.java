package me.dreamvoid.miraimc.internal;

import com.google.common.base.Suppliers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.function.Supplier;

public class MiraiLoader {

    // 把lucko的代码偷过来 XD
    private static final Supplier<URLClassLoaderAccess> LOADER = Suppliers.memoize(() -> URLClassLoaderAccess.create((URLClassLoader) Utils.classLoader))::get;

    public static void loadMiraiCore() throws RuntimeException, IOException {
        classLoadLibrary("net.mamoe", "mirai-core-all", "2.8-M1", "https://repo1.maven.org/maven2", "-all");
    }

    /**
     * @param groupId 组ID
     * @param artifactId 构件ID
     * @param version 版本
     * @param repoUrl 仓库地址
     * @param extraArgs 额外参数
     */
    public static void classLoadLibrary(String groupId, String artifactId, String version, String repoUrl, String extraArgs) throws RuntimeException, IOException {
        // 文件夹
        File MiraiDir; if (Config.Gen_MiraiWorkingDir.equals("default")) MiraiDir = new File(Config.PluginDir,"MiraiBot"); else MiraiDir = new File(Config.Gen_MiraiWorkingDir);
        File LibrariesDir = new File(MiraiDir,"libs");
        if(!LibrariesDir.exists() && !LibrariesDir.mkdirs()) throw new RuntimeException("Failed to create " + LibrariesDir.getPath());

        String name = artifactId + "-" + version + ".jar"; // 文件名

        // -- 下载开始 --
        if (!repoUrl.endsWith("/")) repoUrl += "/";
        repoUrl += "%s/%s/%s/%s-%s%s.jar"; // 下载地址格式

        String jarUrl = String.format(repoUrl, groupId.replace(".", "/"), artifactId, version, artifactId, version, extraArgs); // 格式化后的下载地址

        File saveLocation = new File(LibrariesDir, name);
        if (!saveLocation.exists()) {
            Utils.logger.info("Downloading "+ jarUrl);
            URL url = new URL(jarUrl);

            try (InputStream is = url.openStream()) {
                Files.copy(is, saveLocation.toPath());
            }

            if(saveLocation.exists()){
                Utils.logger.info(name + " successfully downloaded.");
            } else throw new RuntimeException("Failed to download " + jarUrl);
        }

        // -- 加载开始 --
        Utils.logger.info("Loading library " + name);
        LOADER.get().addURL(saveLocation.toURI().toURL());
    }
}
