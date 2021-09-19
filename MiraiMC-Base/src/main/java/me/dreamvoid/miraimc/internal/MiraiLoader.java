package me.dreamvoid.miraimc.internal;

import com.google.common.base.Suppliers;
import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Supplier;

public class MiraiLoader {
    // 把lucko的代码偷过来 XD
    private static final Supplier<URLClassLoaderAccess> LOADER = Suppliers.memoize(() -> URLClassLoaderAccess.create((URLClassLoader) Utils.classLoader))::get;

    /**
     * 加载最新版Mirai Core
     */
    public static void loadMiraiCore() throws RuntimeException, IOException, ParserConfigurationException, SAXException {
        String version = getLibraryVersionMaven("net.mamoe", "mirai-core-all", Config.Gen_MavenRepoUrl,"release");
        try {
            loadLibraryClass("net.mamoe", "mirai-core-all", version, Config.Gen_MavenRepoUrl, "-all");
            File writeName = new File(Config.PluginDir, "cache/core-ver");
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(version);
                out.flush();
            }
        } catch (Exception e) { // TODO: 捕获报错有问题
            Utils.logger.warning("Unable to download mirai core from remote server, try to use local core.");
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
                    LOADER.get().addURL(coreFile.toURI().toURL());
                } else throw e;
            } else throw e;
        }
    }

    /**
     * 加载指定版本的Mirai Core
     * @param version 版本
     */
    public static void loadMiraiCore(String version) throws RuntimeException, IOException {
        try {
            loadLibraryClass("net.mamoe", "mirai-core-all", version, Config.Gen_MavenRepoUrl, "-all");
            File writeName = new File(Config.PluginDir, "cache/core-ver");
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(version);
                out.flush();
            }
        } catch (Exception e) { // TODO: 捕获报错有问题
            Utils.logger.warning("Unable to download mirai core from remote server, try to use local core.");
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
                    LOADER.get().addURL(coreFile.toURI().toURL());
                } else throw e;
            } else throw e;
        }
    }

    /**
     * 加载依赖库
     * @param groupId 组ID
     * @param artifactId 构件ID
     * @param version 版本
     * @param repoUrl 仓库地址
     * @param extraArgs 额外参数
     */
    private static void loadLibraryClass(String groupId, String artifactId, String version, String repoUrl, String extraArgs) throws RuntimeException, IOException {
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

        String name = artifactId + "-" + version + ".jar"; // 文件名

        // jar
        File saveLocation = new File(LibrariesDir, name);
        Utils.logger.info("Verifying "+ name);
        if(!downloadLibraryMaven(groupId,artifactId,version,repoUrl,extraArgs,saveLocation,true)){
            throw new RuntimeException("Failed to load libraries!");
        }


        // -- 加载开始 --
        Utils.logger.info("Loading library " + name);
        LOADER.get().addURL(saveLocation.toURI().toURL());
    }

    public static String getLibraryVersionMaven(String groupId, String artifactId, String repoUrl, String xmlTag) throws RuntimeException, IOException, ParserConfigurationException, SAXException {
        File CacheDir = new File(Config.PluginDir,"cache");
        if(!CacheDir.exists() && !CacheDir.mkdirs()) {
            throw new RuntimeException("Failed to create " + CacheDir.getPath());
        }
        String metaFileName = "maven-metadata-" + groupId + "." + artifactId + ".xml";
        File metaFile = new File(CacheDir, metaFileName);

        if (!repoUrl.endsWith("/")) repoUrl += "/";
        repoUrl += "%s/%s/"; // 根目录格式
        String repoFormat = String.format(repoUrl, groupId.replace(".", "/"), artifactId); // 格式化后的根目录

        // MD5
        File metaFileMD5 = new File(CacheDir, metaFileName + ".md5");
        if(metaFileMD5.exists() && !metaFileMD5.delete()) throw new RuntimeException("Failed to delete " + metaFileMD5.getPath());

        URL metaFileMD5Url = new URL(repoFormat + "maven-metadata.xml.md5");

        downloadFile(metaFileMD5,metaFileMD5Url);

        if(!metaFileMD5.exists()) throw new RuntimeException("Failed to download " + metaFileMD5Url);

        // 验证meta文件
        Utils.logger.info("Verifying " + metaFileName);
        if (metaFile.exists()) {
            try (FileInputStream fis = new FileInputStream(metaFile)) {
                if (!DigestUtils.md5Hex(fis).equals(new String(Files.readAllBytes(metaFileMD5.toPath()), StandardCharsets.UTF_8))) {
                    fis.close();

                    URL metaFileUrl = new URL(repoFormat + "maven-metadata.xml");
                    downloadFile(metaFile, metaFileUrl);
                    if (!metaFileMD5.exists()) throw new RuntimeException("Failed to download " + metaFileUrl);
                }
            }
        } else {
            URL metaFileUrl = new URL(repoFormat + "maven-metadata.xml");
            Utils.logger.info("Downloading "+ metaFileUrl);
            downloadFile(metaFile, metaFileUrl);
            if (!metaFileMD5.exists()) throw new RuntimeException("Failed to download " + metaFileUrl);
        }

        // 读取内容
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(metaFile);
        return doc.getElementsByTagName(xmlTag).item(0).getFirstChild().getNodeValue();
    }

    private static void downloadFile(File file, URL url) throws IOException {
        try (InputStream is = url.openStream()) {
            Files.copy(is, file.toPath());
        }
    }

    /**
     * 从Maven仓库下载依赖
     * @param groupId 组ID
     * @param artifactId 构建ID
     * @param version 版本
     * @param repo 仓库地址
     * @param extraArgs 额外参数
     * @param saveFile 保存文件
     * @param checkMD5 是否检查MD5
     * @return 是否下载成功
     */
    public static boolean downloadLibraryMaven(String groupId, String artifactId, String version, String repo, String extraArgs, File saveFile, boolean checkMD5) throws RuntimeException, IOException {
        // 创建文件夹
        if(!saveFile.getParentFile().exists() && !saveFile.getParentFile().mkdirs()) throw new RuntimeException("Failed to create " + saveFile.getParentFile().getPath());

        // 下载地址格式
        if (!repo.endsWith("/")) repo += "/";
        repo += "%s/%s/%s/%s-%s%s.jar";
        String saveFileUrl = String.format(repo, groupId.replace(".", "/"), artifactId, version, artifactId, version, extraArgs); // 下载地址
        String saveFileName = artifactId + "-" + version + ".jar"; // 文件名

        // 检查MD5
        if(checkMD5) {
            File saveFileMD5 = new File(saveFile.getParentFile(), saveFileName + ".md5");
            String saveFileMD5Url = saveFileUrl + ".md5";
            URL saveFileMD5UrlFormat = new URL(saveFileMD5Url);

            if (saveFileMD5.exists() && !saveFileMD5.delete()) {
                throw new RuntimeException("Failed to delete " + saveFileMD5.getPath());
            }
            downloadFile(saveFileMD5,saveFileMD5UrlFormat);

            if(!saveFileMD5.exists()) throw new RuntimeException("Failed to download " + saveFileMD5Url);

            if(saveFile.exists()){
                FileInputStream fis = new FileInputStream(saveFile);
                boolean isSame = DigestUtils.md5Hex(fis).equals(new String(Files.readAllBytes(saveFileMD5.toPath()), StandardCharsets.UTF_8));
                if(!isSame){
                    fis.close();
                    if(!saveFile.delete()) throw new RuntimeException("Failed to delete " + saveFile.getPath());
                }
            }
        } else if (saveFile.exists() && !saveFile.delete()) {
            throw new RuntimeException("Failed to delete " + saveFile.getPath());
        }

        // 下载依赖文件
        if (!saveFile.exists()) {
            Utils.logger.info("Downloading "+ saveFileUrl);
            downloadFile(saveFile, new URL(saveFileUrl));
        }

        return saveFile.exists();
    }
}
