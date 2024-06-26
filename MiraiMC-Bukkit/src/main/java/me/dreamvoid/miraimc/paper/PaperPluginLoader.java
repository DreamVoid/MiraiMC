package me.dreamvoid.miraimc.paper;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class PaperPluginLoader implements PluginLoader {
    @Override
    public void classloader(@NotNull PluginClasspathBuilder builder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        resolver.addDependency(new Dependency(new DefaultArtifact("cloud.commandframework:cloud-paper:1.8.3"), null));
        resolver.addRepository(new RemoteRepository.Builder("central", "default", System.getProperty("MiraiMC.maven-central-url", "https://repo.huaweicloud.com/repository/maven/")).build());
        builder.addLibrary(resolver);
    }
}
