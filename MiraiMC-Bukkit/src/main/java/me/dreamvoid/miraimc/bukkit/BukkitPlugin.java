package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.commands.MiraiCommand;
import me.dreamvoid.miraimc.bukkit.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.bukkit.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.bukkit.utils.Metrics;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.PluginUpdate;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.classloader.MiraiLoader;
import me.dreamvoid.miraimc.webapi.Info;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BukkitPlugin extends JavaPlugin {

    private MiraiEvent MiraiEvent;
    public MiraiAutoLogin MiraiAutoLogin;

    @Override // 加载插件
    public void onLoad() {
        try {
            Utils.setLogger(this.getLogger());
            Utils.setClassLoader(this.getClassLoader());
            new BukkitConfig(this).loadConfig();

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

    @Override // 启用插件
    public void onEnable() {
        try { // 抛弃Forge用户，别问为什么
            Class.forName("cpw.mods.modlauncher.Launcher");
            getLogger().severe("任何Forge服务端均不受MiraiMC支持（如Catserver、Loliserver），请尽量更换其他服务端使用！");
            getLogger().severe("作者不会处理任何使用了Forge服务端导致的问题。");
            getLogger().severe("兼容性报告: https://wiki.miraimc.dreamvoid.me/troubleshoot/compatibility-report");
        } catch (ClassNotFoundException ignored) {}

        getLogger().info("Mirai working dir: " + Config.Gen_MiraiWorkingDir);

        if(Config.Gen_AddProperties_NoDesktop) System.setProperty("no-desktop", "MiraiMC");
        if(Config.Gen_AddProperties_SliderCaptchaSupported) System.setProperty("slider-captcha-supported", "MiraiMC");

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        getLogger().info("Registering commands.");
        getCommand("mirai").setExecutor(new MiraiCommand(this));
        getCommand("mirai").setTabCompleter(new MiraiCommand(this));
        getCommand("miraimc").setExecutor(new MiraiMcCommand(this));
        getCommand("miraimc").setTabCompleter(new MiraiMcCommand(this));
        getCommand("miraiverify").setExecutor(new MiraiVerifyCommand());

        if(Config.Bot_LogEvents){
            getLogger().info("Registering events.");
            Bukkit.getPluginManager().registerEvents(new Events(), this);
        }

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
            int pluginId = 11534;
            new Metrics(this, pluginId);
        }

        if(Config.Gen_CheckUpdate && !getDescription().getVersion().contains("dev")){
            new BukkitRunnable() {
                @Override
                public void run() {
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
                        } else getLogger().info("You are using the latest version!");
                    } catch (IOException e) {
                        getLogger().warning("An error occurred while fetching the latest version, reason: " + e);
                    }
                }
            }.runTaskAsynchronously(this);
        }

        // HTTP API
        if(Config.Gen_EnableHttpApi){
            getLogger().info("Initializing HttpAPI async task.");
            getServer().getScheduler().runTaskTimerAsynchronously(this,new MiraiHttpAPIResolver(), 0, Config.HTTPAPI_MessageFetch_Interval);
        }

        // 安全警告
        if(!(Config.Gen_DisableSafeWarningMessage)){
            getLogger().warning("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warning("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    List<String> announcement = Info.init().announcement;
                    if(announcement != null){
                        getLogger().info("========== [ MiraiMC 公告版 ] ==========");
                        announcement.forEach(s -> getLogger().info(s));
                        getLogger().info("=======================================");
                    }
                } catch (IOException ignored) {}
            }
        }.runTaskLaterAsynchronously(this, 40L);

        getLogger().info("All tasks done. Welcome to use MiraiMC!");
    }

    @Override // 禁用插件
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

        switch (Config.DB_Type.toLowerCase()){
            case "sqlite":
            default: {
                if (Utils.connection != null) {
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
