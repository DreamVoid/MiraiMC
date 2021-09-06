package me.dreamvoid.miraimc.internal;

import com.google.common.base.Suppliers;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.function.Supplier;

public class MiraiClassLoader {

    // 把lucko的代码偷过来 XD
    private static final Supplier<URLClassLoaderAccess> URL_INJECTOR = Suppliers.memoize(() -> URLClassLoaderAccess.create((URLClassLoader) Config.ClassLoader))::get;

    public static void loadMiraiCore(){
        File MiraiDir;
        if(!(Config.Gen_MiraiWorkingDir.equals("default"))){
            MiraiDir = new File(Config.Gen_MiraiWorkingDir);
        } else {
            MiraiDir = new File(String.valueOf(Config.PluginDir),"MiraiBot");
            if(!MiraiDir.exists()) MiraiDir.mkdir();
        }

        // 库路径
        File libPath = new File(MiraiDir,"libs");
        if(!libPath.exists()){
            if(!libPath.mkdir()){
                Utils.logger.warning("Unable to create libraries folder!");
            }
        }

        downloadLibrary("net.mamoe","mirai-core-all","2.8-M1","https://repo1.maven.org/maven2","-all");

        // 获取所有的.jar和.zip文件
        File[] jarFiles = libPath.listFiles((dir, name) -> name.endsWith(".jar") || name.endsWith(".zip"));

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

    public static void downloadLibrary(String groupId, String artifactId, String version, String repoUrl, String extraArgs) {
        String name = artifactId + "-" + version;

        if (!repoUrl.endsWith("/")) {
            repoUrl += "/";
        }
        repoUrl += "%s/%s/%s/%s-%s%s.jar";

        String jarUrl = String.format(repoUrl, groupId.replace(".", "/"), artifactId, version, artifactId, version, extraArgs);

        File MiraiDir;
        if(!(Config.Gen_MiraiWorkingDir.equals("default"))){
            MiraiDir = new File(Config.Gen_MiraiWorkingDir);
        } else {
            MiraiDir = new File(String.valueOf(Config.PluginDir),"MiraiBot");
        }

        // 库路径
        File libPath = new File(MiraiDir,"libs");
        if(!libPath.exists()){
            if(!libPath.mkdir()){
                Utils.logger.warning("Unable to create libraries folder!");
            }
        }

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
