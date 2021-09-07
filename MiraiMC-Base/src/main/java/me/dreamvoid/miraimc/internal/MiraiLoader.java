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
    private static final Supplier<URLClassLoaderAccess> URL_INJECTOR = Suppliers.memoize(() -> URLClassLoaderAccess.create((URLClassLoader) Utils.classLoader))::get;

    public static void loadMiraiCore() throws IOException{
        // Mirai工作文件夹
        File MiraiDir;
        if (Config.Gen_MiraiWorkingDir.equals("default")) {
            MiraiDir = new File(Config.PluginDir,"MiraiBot");
        } else {
            MiraiDir = new File(Config.Gen_MiraiWorkingDir);
        }
        if(!MiraiDir.exists() && !MiraiDir.mkdir()) {
            throw new IOException("Failed to create folder " + MiraiDir.getPath());
        }

        // 库文件夹
        File LibrariesDir = new File(MiraiDir,"libs");
        if(!LibrariesDir.exists() && !LibrariesDir.mkdir()){
            throw new IOException("Failed to create folder "+ LibrariesDir.getPath());
        }

        downloadLibrary("net.mamoe","mirai-core-all","2.8-M1","https://repo1.maven.org/maven2","-all");

        // 获取所有的.jar和.zip文件
        File[] jarFiles = LibrariesDir.listFiles((dir, name) -> name.endsWith(".jar") || name.endsWith(".zip"));

        if (jarFiles != null) {
            for (File file : jarFiles) {
                try {
                    Utils.logger.info("Loading library "+file.getName());
                    URL_INJECTOR.get().addURL(file.toURI().toURL());
                } catch (Exception e) {
                    Utils.logger.severe("An error occurred while loading "+file.getName()+", Reason: "+e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * @param groupId 组ID
     * @param artifactId 构件ID
     * @param version 版本
     * @param repoUrl 仓库地址
     * @param extraArgs 额外参数
     */
    public static void downloadLibrary(String groupId, String artifactId, String version, String repoUrl, String extraArgs) {
        // 文件夹
        File MiraiDir;
        if(!(Config.Gen_MiraiWorkingDir.equals("default"))){
            MiraiDir = new File(Config.Gen_MiraiWorkingDir);
        } else {
            MiraiDir = new File(Config.PluginDir,"MiraiBot");
            if(!MiraiDir.exists()) {
                if(!MiraiDir.mkdir()) Utils.logger.warning("Failed to create ");
            }
        }

        File libPath = new File(MiraiDir,"libs");
        if(!libPath.exists()){
            if(!libPath.mkdir()){
                Utils.logger.warning("Unable to create libraries folder!");
            }
        }

        String name = artifactId + "-" + version; // 文件名

        if (!repoUrl.endsWith("/")) repoUrl += "/";
        repoUrl += "%s/%s/%s/%s-%s%s.jar"; // 下载地址

        String jarUrl = String.format(repoUrl, groupId.replace(".", "/"), artifactId, version, artifactId, version, extraArgs); // 格式化后的下载地址

        File saveLocation = new File(libPath, name + ".jar");
        if (!saveLocation.exists()) {
            try {
                Utils.logger.info("Downloading "+ jarUrl);
                URL url = new URL(jarUrl);

                try (InputStream is = url.openStream()) {
                    Files.copy(is, saveLocation.toPath());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Utils.logger.info(name + " successfully downloaded.");
        }

        if (!saveLocation.exists()) {
            throw new RuntimeException("Failed to download " + jarUrl);
        }
    }
}
