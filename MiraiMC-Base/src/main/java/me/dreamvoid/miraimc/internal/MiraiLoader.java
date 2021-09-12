package me.dreamvoid.miraimc.internal;

import com.google.common.base.Suppliers;
import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Supplier;

public class MiraiLoader {

    // 把lucko的代码偷过来 XD
    private static final Supplier<URLClassLoaderAccess> LOADER = Suppliers.memoize(() -> URLClassLoaderAccess.create((URLClassLoader) Utils.classLoader))::get;

    public static void loadMiraiCore() throws RuntimeException, IOException, ParserConfigurationException, SAXException {
        loadLibraryClass("net.mamoe", "mirai-core-all", "https://repo1.maven.org/maven2", "-all");
    }

    private static void loadLibraryClass(String groupId, String artifactId, String repoUrl, String extraArgs) throws RuntimeException, IOException, ParserConfigurationException, SAXException {
        loadLibraryClass(groupId, artifactId, getLibraryMeta(groupId,artifactId,repoUrl,"release"), repoUrl, extraArgs);
    }

    /**
     * @param groupId 组ID
     * @param artifactId 构件ID
     * @param version 版本
     * @param repoUrl 仓库地址
     * @param extraArgs 额外参数
     */
    private static void loadLibraryClass(String groupId, String artifactId, String version, String repoUrl, String extraArgs) throws RuntimeException, IOException {
        // 文件夹
        File MiraiDir; if (Config.Gen_MiraiWorkingDir.equals("default")) MiraiDir = new File(Config.PluginDir,"MiraiBot"); else MiraiDir = new File(Config.Gen_MiraiWorkingDir);
        File LibrariesDir = new File(MiraiDir,"libs");
        if(!LibrariesDir.exists() && !LibrariesDir.mkdirs()) throw new RuntimeException("Failed to create " + LibrariesDir.getPath());

        String name = artifactId + "-" + version + ".jar"; // 文件名

        // -- 下载开始 --
        if (!repoUrl.endsWith("/")) repoUrl += "/";
        repoUrl += "%s/%s/%s/%s-%s%s.jar"; // 下载地址格式
        String format = String.format(repoUrl, groupId.replace(".", "/"), artifactId, version, artifactId, version, extraArgs);

        // md5
        File jarMD5 = new File(LibrariesDir, name + ".md5"); if(jarMD5.exists()) if(!jarMD5.delete()) throw new RuntimeException("Failed to delete " + jarMD5.getPath());
        String md5Url = format + ".md5";
        URL md5UrlA = new URL(md5Url);
        try (InputStream is = md5UrlA.openStream()) {
            Files.copy(is, jarMD5.toPath());
        }
        if(!jarMD5.exists()) throw new RuntimeException("Failed to download " + md5Url);

        // jar
        File saveLocation = new File(LibrariesDir, name);

        Utils.logger.info("Verifying "+ name);
        if (!saveLocation.exists() || !DigestUtils.md5Hex(new FileInputStream(saveLocation)).equals(new String(Files.readAllBytes(jarMD5.toPath()), StandardCharsets.UTF_8))) {
            if (saveLocation.exists() && !saveLocation.delete()) {
                throw new RuntimeException("Failed to delete " + saveLocation.getPath());
            }

            Utils.logger.info("Downloading "+ format);
            URL url = new URL(format);

            try (InputStream is = url.openStream()) {
                Files.copy(is, saveLocation.toPath());
            }

            if(saveLocation.exists()){
                Utils.logger.info(name + " successfully downloaded.");
            } else throw new RuntimeException("Failed to download " + format);
        }

        // -- 加载开始 --
        Utils.logger.info("Loading library " + name);
        LOADER.get().addURL(saveLocation.toURI().toURL());
    }

    public static String getLibraryMeta(String groupId, String artifactId, String repoUrl, String tag) throws IOException, ParserConfigurationException, SAXException {
        File CacheDir = new File(Config.PluginDir,"cache"); if(!CacheDir.exists() && !CacheDir.mkdirs()) throw new RuntimeException("Failed to create " + CacheDir.getPath());

        String metaName = "maven-metadata-" + groupId + "." + artifactId + ".xml";

        if (!repoUrl.endsWith("/")) repoUrl += "/";
        repoUrl += "%s/%s/"; // 根目录格式
        repoUrl = String.format(repoUrl, groupId.replace(".", "/"), artifactId); // 格式化后的根目录

        // MD5
        File metaMD5 = new File(CacheDir, metaName + ".md5"); if(metaMD5.exists() && !metaMD5.delete()) throw new RuntimeException("Failed to delete " + metaMD5.getPath());

        URL metaMD5Url = new URL(repoUrl + "maven-metadata.xml.md5");

        try (InputStream is = metaMD5Url.openStream()) {
            Files.copy(is, metaMD5.toPath());
        }

        if(!metaMD5.exists()) throw new RuntimeException("Failed to download " + metaMD5Url);

        // 验证meta文件
        File metaFile = new File(CacheDir, metaName);
        Utils.logger.info("Verifying " + metaName);
        if(!metaFile.exists() || !DigestUtils.md5Hex(new FileInputStream(metaFile)).equals(new String(Files.readAllBytes(metaMD5.toPath()), StandardCharsets.UTF_8))) {
            URL metaFileUrl = new URL(repoUrl + "maven-metadata.xml");

            try (InputStream is = metaFileUrl.openStream()) {
                Files.copy(is, metaFile.toPath());
            }

            if(!metaMD5.exists()) throw new RuntimeException("Failed to download " + metaFileUrl);
        }

        // 读取内容
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(metaFile);
        return doc.getElementsByTagName(tag).item(0).getFirstChild().getNodeValue();
    }
}
