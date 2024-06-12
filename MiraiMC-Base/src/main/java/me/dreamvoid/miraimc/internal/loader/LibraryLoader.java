package me.dreamvoid.miraimc.internal.loader;

import com.google.common.base.Suppliers;
import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.internal.Utils;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.RepositoryPolicy;
import org.eclipse.aether.resolution.*;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transfer.AbstractTransferListener;
import org.eclipse.aether.transfer.TransferCancelledException;
import org.eclipse.aether.transfer.TransferEvent;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.version.Version;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Jar 库加载器
 */
public class LibraryLoader {
	private final Supplier<URLClassLoaderAccess> loader; // 把lucko的代码偷过来 XD

	private final RepositorySystem repository;
	private final DefaultRepositorySystemSession session;

	private final Logger logger;

	public LibraryLoader(URLClassLoader urlClassLoader) {
		loader = () -> Suppliers.memoize(() -> URLClassLoaderAccess.create(urlClassLoader)).get();
		logger = Logger.getLogger("MiraiMC-LibraryLoader");
		logger.setParent(Utils.getLogger());

		DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
		locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
		locator.addService(TransporterFactory.class, HttpTransporterFactory.class);

		this.repository = locator.getService(RepositorySystem.class);
		this.session = MavenRepositorySystemUtils.newSession();

		this.session.setSystemProperties(System.getProperties());
		this.session.setChecksumPolicy(RepositoryPolicy.CHECKSUM_POLICY_FAIL);
		this.session.setLocalRepositoryManager(this.repository.newLocalRepositoryManager(this.session, new LocalRepository(new File(MiraiMCConfig.PluginDir, "libraries"))));
		this.session.setTransferListener(new AbstractTransferListener() {
			@Override
			public void transferInitiated(@NotNull TransferEvent event) throws TransferCancelledException {
				logger.info("Downloading " + event.getResource().getRepositoryUrl() + event.getResource().getResourceName());
			}
		});
		this.session.setReadOnly();
	}

	/**
	 * 加载远程Maven仓库依赖<br>
	 * [!] 请勿使用此方法加载与 MiraiMC 无关的依赖
	 * @param remoteRepository 远程仓库
	 * @param dependency 依赖
	 */
	public void loadLibraryMaven(RemoteRepository remoteRepository, Dependency dependency) {
		List<RemoteRepository> repos = this.repository.newResolutionRepositories(this.session, Collections.singletonList(remoteRepository));

        try {
            DependencyResult result = repository.resolveDependencies(this.session, new DependencyRequest(new CollectRequest(dependency, repos), null));

            for (ArtifactResult artifact : result.getArtifactResults()) {
				File file = artifact.getArtifact().getFile();
				logger.info("Loading library " + file);
				loader.get().addURL(file.toURI().toURL());
			}
		} catch (DependencyResolutionException | MalformedURLException ex) {
			throw new RuntimeException("Error resolving libraries", ex);
		}
	}

	/**
	 * 加载本地 Jar
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
	 * 获取远程Maven仓库依赖最新版本
	 * @param remoteRepository 远程仓库
	 * @param groupId 组ID
	 * @param artifactId 构件ID
	 * @return 版本号
	 */
	public String getLibraryVersion(RemoteRepository remoteRepository, String groupId, String artifactId){
		List<RemoteRepository> repos = this.repository.newResolutionRepositories(this.session, Collections.singletonList(remoteRepository));
		VersionRangeRequest request = new VersionRangeRequest();
		request.setRepositories(repos);
		request.setArtifact(new DefaultArtifact(String.format("%s:%s:[0,)", groupId, artifactId)));
		try {
			VersionRangeResult result = repository.resolveVersionRange(this.session, request);
			if (!result.getVersions().isEmpty()) {
				Version newestVersion = result.getHighestVersion();
				return newestVersion.toString();
			} else {
				return null;
			}
		} catch (VersionRangeResolutionException e) {
			throw new RuntimeException(e);
		}
	}
}
