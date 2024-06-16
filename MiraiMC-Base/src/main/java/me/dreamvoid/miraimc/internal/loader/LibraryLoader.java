package me.dreamvoid.miraimc.internal.loader;

import com.google.common.base.Suppliers;
import me.dreamvoid.miraimc.internal.SemVer;
import me.dreamvoid.miraimc.internal.Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.annotation.Nullable;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Jar 库加载器
 */
public class LibraryLoader {
	private final Supplier<URLClassLoaderAccess> loader; // 把lucko的代码偷过来 XD
	private final Logger logger;

	public LibraryLoader(URLClassLoader urlClassLoader) {
		loader = () -> Suppliers.memoize(() -> URLClassLoaderAccess.create(urlClassLoader)).get();
		logger = Logger.getLogger("MiraiMC-LibraryLoader");
		//logger.setParent(Utils.getLogger());
	}

	/**
	 * 加载本地 Jar<br>
	 * [!] 请勿使用此方法加载与 MiraiMC 无关的依赖
	 * @param file Jar 文件
	 */
	public void loadLibraryLocal(File file) {
		try{
			if(file.exists()){
				logger.info("Loading library " + file);
				loader.get().addURL(file.toURI().toURL());
			} else {
				logger.severe("Could not find library at " + file.getPath());
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error resolving libraries", e);
		}
	}

	/**
	 * 加载远程Maven仓库依赖<br>
	 * [!] 请勿使用此方法加载与 MiraiMC 无关的依赖
	 * @param groupId 组id
	 * @param artifactId 构件id
	 * @param version 版本
	 * @param repo 仓库地址
	 * @param savePath 保存路径
	 */
	public void loadLibraryMaven(String groupId, String artifactId, String version, String repo, Path savePath) throws Exception {
		loadLibraryMaven(groupId, artifactId, version, repo, ".jar", savePath);
	}

	/**
	 * 加载远程Maven仓库依赖<br>
	 * [!] 请勿使用此方法加载与 MiraiMC 无关的依赖
	 * @param groupId 组id
	 * @param artifactId 构件id
	 * @param version 版本
	 * @param repo 仓库地址
	 * @param archiveSuffix 文件后缀（通常为.jar）
	 * @param savePath 保存路径
	 */
	public void loadLibraryMaven(String groupId, String artifactId, String version, String repo, String archiveSuffix, Path savePath) throws Exception {
		String filename = artifactId + "-" + version + archiveSuffix; // 文件名
		String sha1Filename = filename + ".sha1"; // sha1文件名
		File file = savePath.resolve(filename).toFile();
		String url = getJarUrl(groupId, artifactId, version, repo, archiveSuffix);
		boolean needDownload = true;

		if(file.exists()){ // 如果文件已存在，联网验证完整性

            try(InputStream is = new URL(url + ".sha1").openStream();
				ByteArrayOutputStream os = new ByteArrayOutputStream()){

				int i;
				while ((i = is.read()) != -1) os.write(i);

				Files.write(savePath.resolve(sha1Filename), os.toByteArray()); // 写入sha1文件
                String checksum = os.toString().trim().replace(" ", "").toLowerCase();

                needDownload = !Utils.getFileSha1(file).equals(checksum);
			} catch (IOException e) {
				logger.warning("Failed to check file's checksum, reason: " + e);
				needDownload = false; // 由于文件已经存在，无法检测完整性只可能是网络问题，所以继续下载也不可能成功，不如直接不下载
			}
		}

		if(needDownload){
			file.getParentFile().mkdirs();
			Utils.Http.download(url, file);
		}

		loadLibraryLocal(file); // 下载完成后以本地方式加载jar
	}

	/**
	 * 获取远程Maven仓库依赖最新版本
	 * @param groupId 组ID
	 * @param artifactId 构件ID
	 * @param repo 仓库地址
	 * @return 版本号
	 */
	public String getLibraryVersion(String groupId, String artifactId, String repo){
		Document data = fetchMavenMetadata(groupId, artifactId, repo);

		SemVer.VersionKind kind = SemVer.VersionKind.Stable; // 我们只需要Stable，不需要别的

        NodeList vers = data.getElementsByTagName("versions").item(0).getChildNodes();
        TreeMap<SemVer, String> map = new TreeMap<>();
        for (int i = 0; i < vers.getLength(); i++) {
            String ver = vers.item(i).getTextContent().trim();
            if (!ver.isEmpty() && SemVer.isKind(ver, kind)) {
                SemVer semVer = SemVer.parseFromText(ver);
                if (semVer != null) {
                    map.put(semVer, ver);
                    continue;
                }

                logger.warning("Failed to parse version \"" + ver + "\" for \"" + groupId + ":" + artifactId + "\"");
            }
        }
        if (map.isEmpty()) {
            logger.warning("Cannot find any version matches channel \"stable\" for \"" + groupId + ":" + artifactId + "\", using default version.");
        } else {
            return map.lastEntry().getValue();
        }

        return data.getElementsByTagName("release").item(0).getTextContent();
	}

	@Nullable
	private static Document fetchMavenMetadata(String groupId, String artifactId, String repo) {
		try {
			String content = Utils.Http.get(repo + "/" + groupId.replace(".", "/") + artifactId + "/maven-metadata.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			return factory.newDocumentBuilder().parse(new InputSource(new StringReader(content)));
		} catch (Exception e) {
			return null;
		}
	}

	private static String getJarUrl(String groupId, String artifactId, String version, String repo, String archiveSuffix) throws Exception {
		if(version.endsWith("-SNAPSHOT")){
			String base = repo + (repo.endsWith("/") ? "" : "/") + groupId.replace(".", "/") + "/" + artifactId + "/" + version + "/";
			return getSnapshotJarUrl(base, artifactId, version, archiveSuffix);
		} else {
			String base = repo + (repo.endsWith("/") ? "" : "/") + groupId.replace(".", "/") + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version;
			return base + archiveSuffix;
		}
	}

	private static String getSnapshotJarUrl(String baseUrl, String packageName, String packageVersion, String archiveSuffix) throws Exception {
		String content = Utils.Http.get(baseUrl + "maven-metadata.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		Document document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(content)));
		NodeList elements = document.getElementsByTagName("snapshotVersion");
		HashMap<String,String> versions = new HashMap<>();
		for (int i = 0; i < elements.getLength(); i++) {
			NodeList version = elements.item(i).getChildNodes();
			String classifier = findNodeValue(version, "classifier", "");
			String extension = findNodeValue(version, "extension", "");
			String value = findNodeValue(version, "value", "");
			String suffix = (classifier.isEmpty() ? "" : ("-" + classifier)) + "." + extension;

			String real = baseUrl + packageName + "-" + value + suffix;
			versions.put(suffix, real);
		}

        return versions.get(archiveSuffix);
	}

	public static String findNodeValue(NodeList nodes, String name, String defValue) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equals(name)) {
				return node.getTextContent().trim();
			}
		}
		return defValue;
	}
}
