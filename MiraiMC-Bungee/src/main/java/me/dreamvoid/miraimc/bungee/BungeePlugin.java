package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bungee.commands.MiraiCommand;
import me.dreamvoid.miraimc.bungee.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.bungee.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.bungee.utils.Metrics;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.PluginUpdate;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.classloader.MiraiLoader;
import me.dreamvoid.miraimc.webapi.Info;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BungeePlugin extends Plugin {
    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;
    static BungeePlugin INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = this;
        try {
            Utils.setLogger(this.getLogger());
            Utils.setClassLoader(this.getClass().getClassLoader());
            new BungeeConfig(this).loadConfig();

            if(Config.Gen_MiraiCoreVersion.equalsIgnoreCase("latest")) {
                MiraiLoader.loadMiraiCore();
            } else if(Config.Gen_MiraiCoreVersion.equalsIgnoreCase("stable")){
                MiraiLoader.loadMiraiCore(MiraiLoader.getStableVersion(getDescription().getVersion()));
            } else {
                MiraiLoader.loadMiraiCore(Config.Gen_MiraiCoreVersion);
            }
            if(!Config.Gen_LegacyEventSupport){
                this.MiraiEvent = new MiraiEvent();
            } else this.MiraiEvent = new MiraiEventLegacy();
            this.MiraiAutoLogin = new MiraiAutoLogin(this);
        } catch (Exception e) {
            getLogger().warning("An error occurred while loading plugin.");
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        getLogger().info("Mirai working dir: " + Config.Gen_MiraiWorkingDir);

        if(Config.Gen_AddProperties_MiraiNoDesktop){
            System.setProperty("mirai.no-desktop","MiraiMC");
        }
        if(Config.Gen_AddProperties_MiraiSliderCaptchaSupported){
            System.setProperty("mirai.slider.captcha.supported","MiraiMC");
        }

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Registering commands.");
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new MiraiCommand(this,"mirai"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new MiraiMcCommand(this,"miraimc"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new MiraiVerifyCommand("miraiverify"));

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        switch (Config.DB_Type.toLowerCase()){
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
        if(Config.Gen_AllowBStats && !getDescription().getVersion().contains("dev")) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = 12154;
            new Metrics(this, pluginId);
        }

        // 安全警告
        if(!(Config.Gen_DisableSafeWarningMessage)){
            getLogger().warning("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warning("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        if(Config.Gen_CheckUpdate && !getDescription().getVersion().contains("dev")){
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
        MiraiLoginSolver.closeAllVerifyThreads();
        for (long bots : MiraiBot.getOnlineBots()){
            MiraiBot.getBot(bots).close();
        }

        switch (Config.DB_Type.toLowerCase()){
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
