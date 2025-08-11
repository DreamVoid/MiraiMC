package me.dreamvoid.miraimc.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.dreamvoid.miraimc.LifeCycle;
import me.dreamvoid.miraimc.commands.ICommandSender;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.interfaces.IMiraiAutoLogin;
import me.dreamvoid.miraimc.interfaces.IMiraiEvent;
import me.dreamvoid.miraimc.interfaces.Platform;
import me.dreamvoid.miraimc.interfaces.PluginConfig;
import me.dreamvoid.miraimc.loader.LibraryLoader;
import me.dreamvoid.miraimc.velocity.utils.Metrics;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.slf4j.Logger;

import java.io.File;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "miraimc",
        name = "MiraiMC",
        version = "PROJECT.VERSION",
        description = "MiraiBot for Minecraft server",
        url = "https://github.com/DreamVoid/MiraiMC",
        authors = {"DreamVoid"}
)
public class VelocityPlugin implements Platform {
    private final LifeCycle lifeCycle;
    private final java.util.logging.Logger VelocityLogger;
    private final PluginConfig config;
    private final LibraryLoader loader;

    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory){
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.metricsFactory = metricsFactory;

        //VelocityLogger = new VelocityLogger("MiraiMC", logger);
        lifeCycle = new LifeCycle(this);
        lifeCycle.startUp(VelocityLogger = new VelocityLogger("MiraiMC", logger));
        config = new VelocityConfig(this);
        loader = new LibraryLoader((URLClassLoader) getClass().getClassLoader());
    }

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

            MiraiEvent = new MiraiEvent(this);
            MiraiAutoLogin = new MiraiAutoLogin(this);
        } catch (Exception e) {
            me.dreamvoid.miraimc.internal.Utils.resolveException(e, VelocityLogger, "加载 MiraiMC 阶段 1 时出现异常！");
        }

        // enable
        try {
            lifeCycle.postLoad();

            // 注册命令
            getLogger().info("Registering commands.");
            CommandManager manager = server.getCommandManager();
            CommandMeta mirai = manager.metaBuilder("mirai").build();
            CommandMeta miraimc = manager.metaBuilder("miraimc").build();
            CommandMeta miraiverify = manager.metaBuilder("miraiverify").build();
            manager.register(mirai, (SimpleCommand) invocation -> new MiraiCommand().onCommand(getSender(invocation.source()), invocation.arguments()));
            manager.register(miraimc, (SimpleCommand) invocation -> new MiraiMcCommand().onCommand(getSender(invocation.source()), invocation.arguments()));
            manager.register(miraiverify, (SimpleCommand) invocation -> new MiraiVerifyCommand().onCommand(getSender(invocation.source()), invocation.arguments()));

            // 监听事件
            if (config.General_LogEvents) {
                getLogger().info("正在注册事件监听器.");
                server.getEventManager().register(this, new Events());
            }

            // bStats统计
            if (config.General_AllowBStats) {
                getLogger().info("正在初始化 bStats 统计.");
                int pluginId = 13887;
                metricsFactory.make(this, pluginId);
            }
        } catch (Exception ex){
            me.dreamvoid.miraimc.internal.Utils.resolveException(ex, VelocityLogger, "加载 MiraiMC 阶段 2 时出现异常！");
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
        return getServer().getPlayer(uuid).map(Player::getUsername).orElse(null);
    }

    @Override
    public UUID getPlayerUUID(String name) {
        return getServer().getPlayer(name).map(Player::getUniqueId).orElse(null);
    }

    @Override
    public void runTaskAsync(Runnable task) {
        getServer().getScheduler().buildTask(this, task).schedule();
    }

    @Override
    public void runTaskLaterAsync(Runnable task, long delay) {
        getServer().getScheduler().buildTask(this, task).delay(delay * 50, TimeUnit.MILLISECONDS).schedule();
    }

    @Override
    public void runTaskTimerAsync(Runnable task, long delay, long period) {
        getServer().getScheduler().buildTask(this, task).delay(delay * 50, TimeUnit.MILLISECONDS).repeat(period * 50, TimeUnit.MILLISECONDS).schedule();
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
    public LibraryLoader getLibraryLoader() {
        return loader;
    }

    @Override
    public String getType() {
        return "Velocity";
    }

    @Override
    public PluginConfig getPluginConfig() {
        return config;
    }

    private static ICommandSender getSender(CommandSource sender){
        return new ICommandSender() {
            @Override
            public void sendMessage(String message) {
                sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
            }

            @Override
            public boolean hasPermission(String permission) {
                return sender.hasPermission(permission);
            }
        };
    }
}
