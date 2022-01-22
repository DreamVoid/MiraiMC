package me.dreamvoid.miraimc.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoader;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.velocity.commands.MiraiCommand;
import me.dreamvoid.miraimc.velocity.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.velocity.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.velocity.utils.Metrics;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.sql.SQLException;

@Plugin(
        id = "MiraiMC-Velocity",
        name = "MiraiMC",
        version = "1.0",
        description = "MiraiBot for Minecraft server",
        url = "https://github.com/DreamVoid/MiraiMC",
        authors = {"DreamVoid"}
)
public class VelocityPlugin {
    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory){
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.metricsFactory = metricsFactory;

        // load 阶段1
        Utils.setLogger(new VelocityLogger("MiraiMC", null, this));
        Utils.setClassLoader(this.getClass().getClassLoader());

        this.MiraiAutoLogin = new MiraiAutoLogin(this);
    }

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private final Metrics.Factory metricsFactory;

    private MiraiEvent MiraiEvent;
    public MiraiAutoLogin MiraiAutoLogin;

    private PluginContainer pluginContainer;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // load 阶段2
        try {
            new VelocityConfig(this).loadConfig();

            if(Config.Gen_MiraiCoreVersion.equalsIgnoreCase("latest")) {
                MiraiLoader.loadMiraiCore();
            } else {
                MiraiLoader.loadMiraiCore(Config.Gen_MiraiCoreVersion);
            }
            MiraiEvent = new MiraiEvent(this);

        } catch (Exception e) {
            getLogger().warn("An error occurred while loading plugin.");
            e.printStackTrace();
        }

        // enable
        getLogger().info("Mirai working dir: " + Config.Gen_MiraiWorkingDir);

        if(Config.Gen_AddProperties_MiraiNoDesktop) System.setProperty("mirai.no-desktop", "MiraiMC");
        if(Config.Gen_AddProperties_MiraiSliderCaptchaSupported) System.setProperty("mirai.slider.captcha.supported", "MiraiMC");

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        getLogger().info("Registering commands.");
        CommandManager manager = server.getCommandManager();
        CommandMeta mirai = manager.metaBuilder("mirai").build();
        CommandMeta miraimc = manager.metaBuilder("miraimc").build();
        CommandMeta miraiverify = manager.metaBuilder("miraiverify").build();
        manager.register(mirai, new MiraiCommand(this));
        manager.register(miraimc, new MiraiMcCommand(this));
        manager.register(miraiverify, new MiraiVerifyCommand());
        
        if(Config.Bot_LogEvents){
            getLogger().info("Registering events.");
            server.getEventManager().register(this, new Events());
        }

        switch (Config.DB_Type.toLowerCase()){
            case "sqlite":
            default: {
                getLogger().info("Initializing SQLite database.");
                try {
                    Utils.initializeSQLite();
                } catch (SQLException | ClassNotFoundException ex) {
                    if(Config.Gen_FriendlyException) {
                        getLogger().warn("Failed to initialize SQLite database, reason: " + ex);
                    } else ex.printStackTrace();
                }
                break;
            }
            case "mysql": {
                getLogger().info("Initializing MySQL database.");
                Utils.initializeMySQL();
                break;
            }
        }

        // bStats统计
        if(Config.Gen_AllowBStats) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = 13887;
            metricsFactory.make(this, pluginId);
        }

        // 安全警告
        if(!(Config.Gen_DisableSafeWarningMessage)){
            getLogger().warn("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warn("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        pluginContainer = server.getPluginManager().getPlugin("MiraiMC-Velocity").orElse(null);
        getLogger().info("All tasks done. Welcome to use MiraiMC!");
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
}
