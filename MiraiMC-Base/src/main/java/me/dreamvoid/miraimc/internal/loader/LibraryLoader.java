package me.dreamvoid.miraimc.internal.loader;

import com.google.common.base.Suppliers;
import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.internal.Utils;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Supplier;

/**
 * Jar 库加载器
 */
public class LibraryLoader {
	// 把lucko的代码偷过来 XD
	private static final Supplier<URLClassLoaderAccess> LOADER = () -> Suppliers.memoize(() -> URLClassLoaderAccess.create((URLClassLoader) Utils.getClassLoader())).get(); // 勿动，乱改可能导致低版本mc无法加载

	/**
	 * 加载本地 Jar
	 * @param file Jar 文件
	 */
	public static void loadJarLocal(File file) throws MalformedURLException {
		Utils.getLogger().info("Loading library " + file);
		LOADER.get().addURL(file.toURI().toURL());
	}

	/**
	 * 加载 Maven 中央仓库的依赖库
	 * @param groupId 组ID
	 * @param artifactId 构件ID
	 * @param version 版本
	 */
	public static void loadJarMaven(String groupId, String artifactId, String version) throws RuntimeException, IOException {
		loadJarMaven(groupId,artifactId,version,"", MiraiMCConfig.General.MavenRepoUrl, new File(MiraiMCConfig.PluginDir, "libraries"));
	}
	/**
	 * 加载 Maven 中央仓库的依赖库
	 * @param groupId 组ID
	 * @param artifactId 构件ID
	 * @param version 版本
	 * @param path 保存目录
	 */
	public static void loadJarMaven(String groupId, String artifactId, String version, File path) throws RuntimeException, IOException {
		loadJarMaven(groupId,artifactId,version,"", MiraiMCConfig.General.MavenRepoUrl, path);
	}
	/**
	 * 加载 Maven 仓库的依赖库
	 * @param groupId 组ID
	 * @param artifactId 构件ID
	 * @param version 版本
	 * @param repo 仓库地址
	 * @param extra 额外参数
	 * @param path 保存目录
	 */
	public static void loadJarMaven(String groupId, String artifactId, String version, String extra, String repo, File path) throws RuntimeException, IOException {
		String name = artifactId + "-" + version + ".jar"; // 文件名

		// jar
		File saveLocation = new File(path, name);
		if(!downloadJarMaven(repo, groupId, artifactId, version, extra, path)){
			throw new RuntimeException("Failed to download library!");
		}

		// 加载开始
		loadJarLocal(saveLocation);
	}

	/**
	 * 下载文件
	 * @param file 保存路径
	 * @param url 远程地址
	 * @param log 是否输出日志
	 * @throws IOException 下载失败时抛出
	 */
	private static void downloadFile(File file, URL url, boolean log) throws IOException {
		if(log) Utils.getLogger().info("Downloading "+ url);
		try (InputStream is = url.openStream()) {
			Files.copy(is, file.toPath());
		}
	}

	/**
	 * 从Maven仓库下载依赖
	 *
	 * @param repo       仓库地址
	 * @param groupId    组ID
	 * @param artifactId 构建ID
	 * @param version    版本
	 * @param extra      额外参数
	 * @param path       保存路径
	 * @return 下载成功返回true，否则返回false
	 */
	static boolean downloadJarMaven(String repo, String groupId, String artifactId, String version, String extra, File path) throws RuntimeException, IOException {
		// 创建文件夹
		if(!path.exists() && !path.mkdirs()) throw new RuntimeException("Failed to create " + path.getPath());

		String FileName = artifactId + "-" + version + ".jar"; // 文件名
		File JarFile = new File(path, FileName);

		// 下载地址格式
		if (!repo.endsWith("/")) repo += "/";
		repo += "%s/%s/%s/%s-%s%s.jar";
		String JarFileURL = String.format(repo, groupId.replace(".", "/"), artifactId, version, artifactId, version, extra); // 下载地址

		// 检查MD5
		try{
			Utils.getLogger().info("Verifying " + FileName);

			File md5File = new File(path, FileName + ".md5");
			String md5FileUrl = JarFileURL + ".md5";

			if (md5File.exists() && !md5File.delete()) throw new RuntimeException("Failed to delete " + md5File.getPath());

			downloadFile(md5File, new URL(md5FileUrl), false); // 下载MD5文件

			if(!md5File.exists()) throw new RuntimeException("Failed to download " + md5FileUrl);

			if(JarFile.exists()){
				FileInputStream fis = new FileInputStream(JarFile);
				if(!DigestUtils.md5Hex(fis).equals(new String(Files.readAllBytes(md5File.toPath()), StandardCharsets.UTF_8))){
					fis.close();
					if(!JarFile.delete()) throw new RuntimeException("Failed to delete " + JarFile.getPath());
				}
			}
		} catch (RuntimeException e){
			Utils.getLogger().warning("Verify library failed, skipping: " + e.getMessage());
		}

		// 下载正式文件
		if (!JarFile.exists()) {
			downloadFile(JarFile, new URL(JarFileURL), true);
		}

		return JarFile.exists();
	}


	/**
	 * 获取Maven中央仓库的依赖版本
	 * @param groupId 组ID
	 * @param artifactId 构件ID
	 * @param xmlTag XML标签
	 * @return 版本名
	 */
	public static String getLibraryVersionMaven(String groupId, String artifactId, String xmlTag) throws RuntimeException, IOException, ParserConfigurationException, SAXException {
		return getLibraryVersionMaven(groupId,artifactId, MiraiMCConfig.General.MavenRepoUrl, xmlTag);
	}
	/**
	 * 获取Maven仓库的最新依赖版本
	 * @param groupId 组ID
	 * @param artifactId 构件ID
	 * @return 版本名
	 */
	public static String getLibraryVersionMaven(String groupId, String artifactId) throws RuntimeException, IOException, ParserConfigurationException, SAXException {
		return getLibraryVersionMaven(groupId,artifactId, MiraiMCConfig.General.MavenRepoUrl, "release");
	}

	/**
	 * 获取Maven仓库的依赖版本
	 * @param groupId 组ID
	 * @param artifactId 构件ID
	 * @param repo 仓库地址
	 * @param xmlTag XML标签
	 * @return 版本名
	 */
	public static String getLibraryVersionMaven(String groupId, String artifactId, String repo, String xmlTag) throws RuntimeException, IOException, ParserConfigurationException, SAXException {
		File CacheDir = new File(MiraiMCConfig.PluginDir,"cache");
		if(!CacheDir.exists() && !CacheDir.mkdirs()) throw new RuntimeException("Failed to create " + CacheDir.getPath());
		String metaFileName = "maven-metadata-" + groupId + "." + artifactId + ".xml";
		File metaFile = new File(CacheDir, metaFileName);

		if (!repo.endsWith("/")) repo += "/";
		repo += "%s/%s/"; // 根目录格式
		repo = String.format(repo, groupId.replace(".", "/"), artifactId);

		// MD5
		File metaFileMD5 = new File(CacheDir, metaFileName + ".md5");
		if(metaFileMD5.exists() && !metaFileMD5.delete()) throw new RuntimeException("Failed to delete " + metaFileMD5.getPath());

		URL MD5FileUrl = new URL(repo + "maven-metadata.xml.md5");
		downloadFile(metaFileMD5, MD5FileUrl, false);
		if(!metaFileMD5.exists()) throw new RuntimeException("Failed to download " + MD5FileUrl);

		// 验证meta文件
		Utils.getLogger().info("Verifying " + metaFileName);
		if (metaFile.exists()) {
			try (FileInputStream fis = new FileInputStream(metaFile)) {
				if (!DigestUtils.md5Hex(fis).equals(new String(Files.readAllBytes(metaFileMD5.toPath()), StandardCharsets.UTF_8))) {
					fis.close();
					if(!metaFile.delete()) throw new RuntimeException("Failed to delete " + metaFile.getPath());
				}
			}
		}

		if(!metaFile.exists()){
			String metaFileUrl = repo + "maven-metadata.xml";
			downloadFile(metaFile, new URL(metaFileUrl), false);
			if (!metaFileMD5.exists()) throw new RuntimeException("Failed to download " + metaFileUrl);
		}

		// 读取内容
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(metaFile);
		return doc.getElementsByTagName(xmlTag).item(0).getFirstChild().getNodeValue();
	}
}
