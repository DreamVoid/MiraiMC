package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.utils.Metrics;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoader;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

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
            } else {
                MiraiLoader.loadMiraiCore(Config.Gen_MiraiCoreVersion);
            }
            this.MiraiEvent = new MiraiEvent();
            this.MiraiAutoLogin = new MiraiAutoLogin(this);
        } catch (Exception e) {
            getLogger().warning("An error occurred while loading plugin.");
            e.printStackTrace();
        }
    }

    @Override // 启用插件
    public void onEnable() {
        getLogger().info("Mirai working dir: " + Config.Gen_MiraiWorkingDir);

        if(Config.Gen_AddProperties_MiraiNoDesktop) System.setProperty("mirai.no-desktop", "MiraiMC");
        if(Config.Gen_AddProperties_MiraiSliderCaptchaSupported) System.setProperty("mirai.slider.captcha.supported", "MiraiMC");

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        getLogger().info("Registering commands.");
        for (String s : Arrays.asList("mirai", "miraimc", "miraiverify")) { Objects.requireNonNull(getCommand(s)).setExecutor(new Commands(this)); }

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
                    getLogger().severe("Failed to initialize SQLite database!");
                    getLogger().severe("Reason: "+e);
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

        // 安全警告
        if(!(Config.Gen_DisableSafeWarningMessage)){
            getLogger().warning("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warning("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        getLogger().info("All tasks done. Welcome to use MiraiMC!");
    }

    @Override // 禁用插件
    public void onDisable() {
        getLogger().info("Stopping bot event listener.");
        MiraiEvent.stopListenEvent();

        getLogger().info("Closing all bots");
        MiraiLoginSolver.closeAllVerifyThreads();
        for (long bots : MiraiBot.getOnlineBots()){
            MiraiBot.getBot(bots).doLogout();
        }

        switch (Config.DB_Type.toLowerCase()){
            case "sqlite":
            default: {
                getLogger().info("Closing SQLite database.");
                try {
                    Utils.closeSQLite();
                } catch (SQLException e) {
                    getLogger().severe("Failed to close SQLite database!");
                    getLogger().severe("Reason: " + e);
                }
                break;
            }
            case "mysql": {
                getLogger().info("Closing MySQL database.");
                Utils.closeMySQL();
                break;
            }
        }

        getLogger().info("All tasks done. Thanks for use MiraiMC!");
    }
}
