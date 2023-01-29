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
import me.dreamvoid.miraimc.MiraiMCPlugin;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.PluginUpdate;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.libloader.MiraiLoader;
import me.dreamvoid.miraimc.Platform;
import me.dreamvoid.miraimc.velocity.utils.Metrics;
import me.dreamvoid.miraimc.velocity.utils.SpecialUtils;
import me.dreamvoid.miraimc.internal.webapi.Info;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "miraimc",
        name = "MiraiMC",
        version = "1.7.1",
        description = "MiraiBot for Minecraft server",
        url = "https://github.com/DreamVoid/MiraiMC",
        authors = {"DreamVoid"}
)
public class VelocityPlugin {
    @Inject
    public VelocityPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory){
        System.setProperty("mirai.no-desktop", "MiraiMC");
        System.setProperty("mirai.slider.captcha.supported", "MiraiMC");
        INSTANCE = this;

        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.metricsFactory = metricsFactory;

        // load 阶段1
        Utils.setLogger(new VelocityLogger("MiraiMC", null, this));
        Utils.setClassLoader(this.getClass().getClassLoader());

        this.MiraiAutoLogin = new MiraiAutoLogin(this);
    }

    public static VelocityPlugin INSTANCE;
    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private final Metrics.Factory metricsFactory;

    private MiraiEvent MiraiEvent;
    public final MiraiAutoLogin MiraiAutoLogin;

    private PluginContainer pluginContainer;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        pluginContainer = server.getPluginManager().getPlugin("miraimc").orElse(null);

        // load 阶段2
        try {
            new VelocityConfig(this).loadConfig();

            if(Config.General.MiraiCoreVersion.equalsIgnoreCase("latest")) {
                MiraiLoader.loadMiraiCore();
            } else if(Config.General.MiraiCoreVersion.equalsIgnoreCase("stable")){
                MiraiLoader.loadMiraiCore(MiraiLoader.getStableVersion(getPluginContainer().getDescription().getVersion().orElse("1.0")));
            } else {
                MiraiLoader.loadMiraiCore(Config.General.MiraiCoreVersion);
            }
            if(!Config.General.LegacyEventSupport){
                this.MiraiEvent = new MiraiEvent(this);
            } else this.MiraiEvent = new MiraiEventLegacy(this);

            MiraiMCPlugin.setPlugin(new MiraiMCPlugin() {
                @Override
                public String getName() {
                    return VelocityPlugin.this.getPluginContainer().getDescription().getName().orElse("MiraiMC");
                }

                @Override
                public String getVersion() {
                    return VelocityPlugin.this.getPluginContainer().getDescription().getVersion().orElse("reserved");
                }

                @Override
                public List<String> getAuthors() {
                    return VelocityPlugin.this.getPluginContainer().getDescription().getAuthors();
                }

                @Override
                public java.util.logging.Logger getLogger() {
                    return Utils.logger;
                }

                @Override
                public Platform getServer() {
                    return new Platform() {
                        @Override
                        public String getPlayerName(UUID uuid) {
                            return VelocityPlugin.this.getServer().getPlayer(uuid).get().getUsername();
                        }

                        @Override
                        public UUID getPlayerUUID(String name) {
                            return VelocityPlugin.this.getServer().getPlayer(name).get().getUniqueId();
                        }

                        @Override
                        public void runTaskAsync(Runnable task) {
                            VelocityPlugin.this.getServer().getScheduler().buildTask(VelocityPlugin.this, task).schedule();
                        }
                    };
                }

                @Override
                public IMiraiAutoLogin getAutoLogin() {
                    return new IMiraiAutoLogin() {
                        @Override
                        public void loadFile() {
                            MiraiAutoLogin.loadFile();
                        }

                        @Override
                        public List<Map<?, ?>> loadAutoLoginList() throws IOException {
                            List<Map<?,?>> list = new ArrayList<>();
                            MiraiAutoLogin.loadAutoLoginList().forEach(accounts -> {
                                Map<String, Long> map = new HashMap<>();
                                map.put("account", accounts.getAccount());
                                list.add(map);
                            });
                            return list;
                        }

                        @Override
                        public void doStartUpAutoLogin() {
                            MiraiAutoLogin.doStartUpAutoLogin();
                        }

                        @Override
                        public boolean addAutoLoginBot(long Account, String Password, String Protocol) {
                            return MiraiAutoLogin.addAutoLoginBot(Account, Password, Protocol);
                        }

                        @Override
                        public boolean delAutoLoginBot(long Account) {
                            return MiraiAutoLogin.delAutoLoginBot(Account);
                        }
                    };
                }
            });
        } catch (Exception e) {
            getLogger().warn("An error occurred while loading plugin.");
            e.printStackTrace();
        }

        // enable
        getLogger().info("Mirai working dir: " + Config.General.MiraiWorkingDir);

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
        manager.register(mirai, (SimpleCommand) invocation -> new MiraiCommand().onCommand(SpecialUtils.getSender(invocation.source()), invocation.arguments()));
        manager.register(miraimc, (SimpleCommand) invocation -> new MiraiMcCommand().onCommand(SpecialUtils.getSender(invocation.source()), invocation.arguments()));
        manager.register(miraiverify, (SimpleCommand) invocation -> new MiraiVerifyCommand().onCommand(SpecialUtils.getSender(invocation.source()), invocation.arguments()));
        
        if(Config.Bot.LogEvents){
            getLogger().info("Registering events.");
            server.getEventManager().register(this, new Events());
        }

        switch (Config.Database.Type.toLowerCase()){
            case "sqlite":
            default: {
                getLogger().info("Initializing SQLite database.");
                try {
                    Utils.initializeSQLite();
                } catch (SQLException | ClassNotFoundException ex) {
                    getLogger().warn("Failed to initialize SQLite database, reason: " + ex);
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
        if(Config.General.AllowBStats) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = 13887;
            metricsFactory.make(this, pluginId);
        }

        // HTTP API
        if(Config.General.EnableHttpApi){
            getLogger().info("Initializing HttpAPI async task.");
            getServer().getScheduler().buildTask(this, new MiraiHttpAPIResolver(this)).repeat(Config.HttpApi.MessageFetch.Interval * 20, TimeUnit.MILLISECONDS).schedule();
        }

        // 安全警告
        if(!(Config.General.DisableSafeWarningMessage)){
            getLogger().warn("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warn("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        if(Config.General.CheckUpdate && !getPluginContainer().getDescription().getVersion().orElse("reserved").contains("dev")) {
            getServer().getScheduler().buildTask(this, () -> {
                getLogger().info("Checking update...");
                try {
                    PluginUpdate fetch = new PluginUpdate();
                    String version = !getPluginContainer().getDescription().getVersion().orElse("reserved").contains("-") ? fetch.getLatestRelease() : fetch.getLatestPreRelease();
                    if (fetch.isBlocked(getPluginContainer().getDescription().getVersion().orElse("reserved"))) {
                        getLogger().error("当前版本已停用，继续使用将不会得到作者的任何支持！");
                        getLogger().error("请立刻更新到最新版本: " + version);
                        getLogger().error("从Github下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                    } else if (!getPluginContainer().getDescription().getVersion().orElse("reserved").equals(version)) {
                        getLogger().info("已找到新的插件更新，最新版本: " + version);
                        getLogger().info("从Github下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                    } else getLogger().info("你使用的是最新版本");
                } catch (IOException|NullPointerException e) {
                    getLogger().warn("An error occurred while fetching the latest version, reason: " + e);
                }
            }).schedule();
        }

        getServer().getScheduler().buildTask(this, () -> {
            try {
                List<String> announcement = Info.init().announcement;
                if(announcement != null){
                    getLogger().info("========== [ MiraiMC 公告版 ] ==========");
                    announcement.forEach(s -> getLogger().info(s));
                    getLogger().info("=======================================");
                }
            } catch (IOException ignored) {}
        }).delay(2, TimeUnit.SECONDS).schedule();

        getLogger().info("All tasks done. Welcome to use MiraiMC!");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        if(MiraiEvent != null) {
            getLogger().info("Stopping bot event listener.");
            MiraiEvent.stopListenEvent();
        }

        getLogger().info("Closing all bots");
        MiraiLoginSolver.cancelAll();
        for (long bots : MiraiBot.getOnlineBots()){
            MiraiBot.getBot(bots).close();
        }

        switch (Config.Database.Type.toLowerCase()){
            case "sqlite":
            default: {
                if(Utils.connection != null) {
                    getLogger().info("Closing SQLite database.");
                    try {
                        Utils.closeSQLite();
                    } catch (SQLException e) {
                        getLogger().error("Failed to close SQLite database!");
                        getLogger().error("Reason: " + e);
                    }
                }
                break;
            }
            case "mysql": {
                if(Utils.ds != null) {
                    getLogger().info("Closing MySQL database.");
                    Utils.closeMySQL();
                }
                break;
            }
        }

        getLogger().info("All tasks done. Thanks for use MiraiMC!");
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
