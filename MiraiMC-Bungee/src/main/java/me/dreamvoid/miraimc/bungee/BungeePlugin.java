package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.IMiraiAutoLogin;
import me.dreamvoid.miraimc.MiraiMCPlugin;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bungee.utils.Metrics;
import me.dreamvoid.miraimc.bungee.utils.SpecialUtils;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.PluginUpdate;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.libloader.MiraiLoader;
import me.dreamvoid.miraimc.Platform;
import me.dreamvoid.miraimc.internal.webapi.Info;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BungeePlugin extends Plugin {
    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;

    @Override
    public void onLoad() {
        System.setProperty("mirai.no-desktop", "MiraiMC");
        System.setProperty("mirai.slider.captcha.supported", "MiraiMC");

        try {
            Utils.setLogger(this.getLogger());
            Utils.setClassLoader(this.getClass().getClassLoader());
            new BungeeConfig(this).loadConfig();

            if(Config.General.MiraiCoreVersion.equalsIgnoreCase("latest")) {
                MiraiLoader.loadMiraiCore();
            } else if(Config.General.MiraiCoreVersion.equalsIgnoreCase("stable")){
                MiraiLoader.loadMiraiCore(MiraiLoader.getStableVersion(getDescription().getVersion()));
            } else {
                MiraiLoader.loadMiraiCore(Config.General.MiraiCoreVersion);
            }
            if(!Config.General.LegacyEventSupport){
                this.MiraiEvent = new MiraiEvent();
            } else this.MiraiEvent = new MiraiEventLegacy();
            this.MiraiAutoLogin = new MiraiAutoLogin(this);

            MiraiMCPlugin.setPlugin(new MiraiMCPlugin() {
                @Override
                public String getName() {
                    return getDescription().getName();
                }

                @Override
                public String getVersion() {
                    return getDescription().getVersion();
                }

                @Override
                public List<String> getAuthors() {
                    return Collections.singletonList(getDescription().getAuthor());
                }

                @Override
                public Logger getLogger() {
                    return Utils.logger;
                }

                @Override
                public Platform getServer() {
                    return new Platform() {
                        @Override
                        public String getPlayerName(UUID uuid) {
                            return getProxy().getPlayer(uuid).getName();
                        }

                        @Override
                        public UUID getPlayerUUID(String name) {
                            return getProxy().getPlayer(name).getUniqueId();
                        }

                        @Override
                        public void runTaskAsync(Runnable task) {
                            getProxy().getScheduler().runAsync(BungeePlugin.this, task);
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
                            return MiraiAutoLogin.loadAutoLoginList();
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
            getLogger().warning("An error occurred while loading plugin.");
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        getLogger().info("Mirai working dir: " + Config.General.MiraiWorkingDir);

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Registering commands.");
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command("mirai","miraimc.command.mirai") {
            @Override
            public void execute(CommandSender sender, String[] strings) {
                new MiraiCommand().onCommand(SpecialUtils.getSender(sender), strings);
            }
        });
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command("miraimc","miraimc.command.miraimc") {
            @Override
            public void execute(CommandSender sender, String[] strings) {
                new MiraiMcCommand().onCommand(SpecialUtils.getSender(sender), strings);
            }
        });
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command("miraiverify","miraimc.command.miraiverify") {
            @Override
            public void execute(CommandSender sender, String[] strings) {
                new MiraiVerifyCommand().onCommand(SpecialUtils.getSender(sender), strings);
            }
        });
        //ProxyServer.getInstance().getPluginManager().registerCommand(this,new MiraiCommand(this,"mirai"));
        //ProxyServer.getInstance().getPluginManager().registerCommand(this,new MiraiMcCommand(this,"miraimc"));
        //ProxyServer.getInstance().getPluginManager().registerCommand(this,new MiraiVerifyCommand("miraiverify"));

        if(Config.Bot.LogEvents){
            getLogger().info("Registering events.");
            getProxy().getPluginManager().registerListener(this, new Events());
        }

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        switch (Config.Database.Type.toLowerCase()){
            case "sqlite":
            default: {
                getLogger().info("Initializing SQLite database.");
                try {
                    Utils.initializeSQLite();
                } catch (SQLException | ClassNotFoundException e) {
                    getLogger().warning("Failed to initialize SQLite database, reason: " + e);
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
        if(Config.General.AllowBStats && !getDescription().getVersion().contains("dev")) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = 12154;
            new Metrics(this, pluginId);
        }

        // HTTP API
        if(Config.General.EnableHttpApi){
            getLogger().info("Initializing HttpAPI async task.");
            getProxy().getScheduler().schedule(this, new MiraiHttpAPIResolver(this), 0, Config.HttpApi.MessageFetch.Interval * 20, TimeUnit.MILLISECONDS);
        }

        // 安全警告
        if(!(Config.General.DisableSafeWarningMessage)){
            getLogger().warning("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warning("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        if(Config.General.CheckUpdate && !getDescription().getVersion().contains("dev")){
            getProxy().getScheduler().runAsync(this, () -> {
                getLogger().info("Checking update...");
                try {
                    PluginUpdate fetch = new PluginUpdate();
                    String version = !getDescription().getVersion().contains("-") ? fetch.getLatestRelease() : fetch.getLatestPreRelease();
                    if (fetch.isBlocked(getDescription().getVersion())) {
                        getLogger().severe("当前版本已停用，继续使用将不会得到作者的任何支持！");
                        getLogger().severe("请立刻更新到最新版本: " + version);
                        getLogger().severe("从Github下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                    } else if (!getDescription().getVersion().equals(version)) {
                        getLogger().info("已找到新的插件更新，最新版本: " + version);
                        getLogger().info("从Github下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                    } else getLogger().info("你使用的是最新版本");
                } catch (IOException e) {
                    getLogger().warning("An error occurred while fetching the latest version, reason: " + e);
                }
            });
        }

        getProxy().getScheduler().schedule(this, () -> {
            try {
                List<String> announcement = Info.init().announcement;
                if(announcement != null){
                    getLogger().info("========== [ MiraiMC 公告版 ] ==========");
                    announcement.forEach(s -> getLogger().info(s));
                    getLogger().info("=======================================");
                }
            } catch (IOException ignored) {}
        }, 2, TimeUnit.SECONDS);

        getLogger().info("All tasks done. Welcome to use MiraiMC!");
    }

    @Override
    public void onDisable() {
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
                        getLogger().severe("Failed to close SQLite database!");
                        getLogger().severe("Reason: " + e);
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
}
