package me.dreamvoid.miraimc.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.dreamvoid.miraimc.IMiraiAutoLogin;
import me.dreamvoid.miraimc.IMiraiEvent;
import me.dreamvoid.miraimc.MiraiMCPlugin;
import me.dreamvoid.miraimc.PlatformPlugin;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.velocity.utils.Metrics;
import me.dreamvoid.miraimc.velocity.utils.SpecialUtils;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "miraimc",
        name = "MiraiMC",
        version = "1.8-pre1",
        description = "MiraiBot for Minecraft server",
        url = "https://github.com/DreamVoid/MiraiMC",
        authors = {"DreamVoid"}
)
public class VelocityPlugin implements PlatformPlugin {
    private final MiraiMCPlugin lifeCycle;
    private final MiraiMCConfig platformConfig;
    private final java.util.logging.Logger VelocityLogger;

    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory){
        INSTANCE = this;

        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.metricsFactory = metricsFactory;

        VelocityLogger = new VelocityLogger("MiraiMC", null, this);
        lifeCycle = new MiraiMCPlugin(this);
        lifeCycle.startUp();
        platformConfig = new VelocityConfig(this);
    }

    public static VelocityPlugin INSTANCE;
    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private final Metrics.Factory metricsFactory;

    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;

    private PluginContainer pluginContainer;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        pluginContainer = server.getPluginManager().getPlugin("miraimc").orElse(null);

        // load
        try {
            lifeCycle.preLoad();

            MiraiEvent = !MiraiMCConfig.General.LegacyEventSupport ? new MiraiEvent(this) : new MiraiEventLegacy(this);
            MiraiAutoLogin = new MiraiAutoLogin(this);
        } catch (Exception e) {
            getLogger().warn("An error occurred while loading plugin.");
            e.printStackTrace();
        }

        // enable
        lifeCycle.postLoad();

        // 注册命令
        getLogger().info("Registering commands.");
        CommandManager manager = server.getCommandManager();
        CommandMeta mirai = manager.metaBuilder("mirai").build();
        CommandMeta miraimc = manager.metaBuilder("miraimc").build();
        CommandMeta miraiverify = manager.metaBuilder("miraiverify").build();
        manager.register(mirai, (SimpleCommand) invocation -> new MiraiCommand().onCommand(SpecialUtils.getSender(invocation.source()), invocation.arguments()));
        manager.register(miraimc, (SimpleCommand) invocation -> new MiraiMcCommand().onCommand(SpecialUtils.getSender(invocation.source()), invocation.arguments()));
        manager.register(miraiverify, (SimpleCommand) invocation -> new MiraiVerifyCommand().onCommand(SpecialUtils.getSender(invocation.source()), invocation.arguments()));

        // 监听事件
        if(MiraiMCConfig.Bot.LogEvents){
            getLogger().info("Registering events.");
            server.getEventManager().register(this, new Events());
        }

        // bStats统计
        if(MiraiMCConfig.General.AllowBStats) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = 13887;
            metricsFactory.make(this, pluginId);
        }

        // HTTP API
        if(MiraiMCConfig.General.EnableHttpApi){
            getLogger().info("Initializing HttpAPI async task.");
            getServer().getScheduler().buildTask(this, new MiraiHttpAPIResolver(this)).repeat(MiraiMCConfig.HttpApi.MessageFetch.Interval * 20, TimeUnit.MILLISECONDS).schedule();
        }
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        lifeCycle.unload();
    }

    public Logger getLogger() {
        return logger;
    }

    public File getDataFolder() {
        return dataDirectory.toFile();
    }

    public ProxyServer getServer() {
        return server;
    }

    public PluginContainer getPluginContainer(){
        return pluginContainer;
    }

    @Override
    public String getPlayerName(UUID uuid) {
        return getServer().getPlayer(uuid).get().getUsername();
    }

    @Override
    public UUID getPlayerUUID(String name) {
        return getServer().getPlayer(name).get().getUniqueId();
    }

    @Override
    public void runTaskAsync(Runnable task) {
        getServer().getScheduler().buildTask(this, task).schedule();
    }

    @Override
    public void runTaskLaterAsync(Runnable task, long delay) {
        getServer().getScheduler().buildTask(this,task).delay(delay * 50, TimeUnit.MILLISECONDS).schedule();
    }

    @Override
    public String getPluginName() {
        return getPluginContainer().getDescription().getName().orElse("MiraiMC");
    }

    @Override
    public String getPluginVersion() {
        return getPluginContainer().getDescription().getVersion().orElse("reserved");
    }

    @Override
    public List<String> getAuthors() {
        return getPluginContainer().getDescription().getAuthors();
    }

    @Override
    public java.util.logging.Logger getPluginLogger() {
        return VelocityLogger;
    }

    @Override
    public ClassLoader getPluginClassLoader() {
        return getClass().getClassLoader();
    }

    @Override
    public IMiraiAutoLogin getAutoLogin() {
        return MiraiAutoLogin;
    }

    @Override
    public IMiraiEvent getMiraiEvent() {
        return MiraiEvent;
    }

    @Override
    public MiraiMCConfig getPluginConfig() {
        return platformConfig;
    }
}
