package me.dreamvoid.miraimc.paper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("all")
public class PaperPluginLoader implements PluginLoader {
    @Override
    public void classloader(@NotNull PluginClasspathBuilder builder) {
        File dependencies = builder.getContext().getDataDirectory().resolve("dependencies.json").toFile();
        HashMap<String, List<String>> map = new HashMap<>();
        if(dependencies.exists() && !dependencies.isDirectory()){
            try{
                map = new Gson().fromJson(new FileReader(dependencies), HashMap.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JsonSyntaxException e){
                e.printStackTrace();
            }
        }
        MavenLibraryResolver resolver = new MavenLibraryResolver();

        for(String s : map.get("repositories")){
            resolver.addRepository(new RemoteRepository.Builder(UUID.randomUUID().toString(), "default", s).build());
        }
        for(String s : map.get("dependencies")){
            resolver.addDependency(new Dependency(new DefaultArtifact(s), null));
        }

        // Remote
        resolver.addDependency(new Dependency(new DefaultArtifact("cloud.commandframework:cloud-paper:1.8.3"), null));
        resolver.addRepository(new RemoteRepository.Builder("central", "default", System.getProperty("MiraiMC.maven-central-url", "https://repo.huaweicloud.com/repository/maven/")).build());
        builder.addLibrary(resolver);
    }
}
