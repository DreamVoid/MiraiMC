package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.utils.Metrics;
import me.dreamvoid.miraimc.internal.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
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
        if(new File("cauldron.yml").exists()){
            getLogger().severe("MiraiMC不支持Cauldron及任何下游服务端，请更换其他服务端使用！（如果你并没有在使用，请删除服务端根目录下的cauldron.yml文件）");
            getLogger().severe("请不要反馈你遇到的任何问题，作者将永远不会解决！");
            getLogger().severe("兼容性报告: https://wiki.miraimc.dreamvoid.ml/troubleshoot/compatibility-report/cauldron");
        }
        if(new File("catserver.yml").exists()){
            getLogger().severe("MiraiMC不支持CatServer及任何下游服务端，建议更换为Mohist或其他服务端！（如果你并没有在使用，请删除服务端根目录下的catserver.yml文件）");
            getLogger().severe("请不要反馈你遇到的任何问题，请联系CatServer作者解决兼容性问题！");
            getLogger().severe("兼容性报告: https://wiki.miraimc.dreamvoid.ml/troubleshoot/compatibility-report/catserver");
        }
        if(new File("loliserver.yml").exists()){
            getLogger().severe("MiraiMC不支持CatServer及任何下游服务端，建议更换为Arclight、Mohist或其他服务端！（如果你并没有在使用，请删除服务端根目录下的loliserver.yml文件）");
            getLogger().severe("请不要反馈你遇到的任何问题，请联系LoliServer作者解决兼容性问题！");
            getLogger().severe("兼容性报告: https://wiki.miraimc.dreamvoid.ml/troubleshoot/compatibility-report/catserver");
        }
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
                    if(Config.Gen_FriendlyException) {
                        getLogger().warning("Failed to initialize SQLite database, reason: " + e);
                    } else e.printStackTrace();
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
                        if(!getDescription().getVersion().equals(version)){
                            getLogger().info("已找到新的插件更新，最新版本: " + version);
                            getLogger().info("从Github下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                        } else getLogger().info("You are using the latest version!");
                    } catch (IOException e) {
                        getLogger().warning("An error occurred while fetching the latest version, reason: " + e);
                    }
                }
            }.runTaskAsynchronously(this);
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
