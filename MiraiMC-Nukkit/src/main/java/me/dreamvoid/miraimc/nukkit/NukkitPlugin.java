package me.dreamvoid.miraimc.nukkit;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.NukkitRunnable;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.PluginUpdate;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.classloader.MiraiLoader;
import me.dreamvoid.miraimc.nukkit.commands.MiraiCommand;
import me.dreamvoid.miraimc.nukkit.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.nukkit.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.nukkit.utils.MetricsLite;
import me.dreamvoid.miraimc.webapi.Info;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class NukkitPlugin extends PluginBase {

    private static NukkitPlugin nukkitPlugin;

    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;

    public static NukkitPlugin getInstance() {
        return nukkitPlugin;
    }

    @Override
    public void onLoad() {
        nukkitPlugin = this;
        try {
            Utils.setLogger(new NukkitLogger("MiraiMC-Nukkit",null, this));
            Utils.setClassLoader(this.getClass().getClassLoader());
            new NukkitConfig(this).loadConfig();

            if(Config.General.MiraiCoreVersion.equalsIgnoreCase("latest")) {
                MiraiLoader.loadMiraiCore();
            } else if(Config.General.MiraiCoreVersion.equalsIgnoreCase("stable")){
                MiraiLoader.loadMiraiCore(MiraiLoader.getStableVersion(getDescription().getVersion()));
            } else {
                MiraiLoader.loadMiraiCore(Config.General.MiraiCoreVersion);
            }
            if(!Config.General.LegacyEventSupport){
                this.MiraiEvent = new MiraiEvent(this);
            } else this.MiraiEvent = new MiraiEventLegacy(this);
            this.MiraiAutoLogin = new MiraiAutoLogin(this);
        } catch (Exception e) {
            getLogger().warning("An error occurred while loading plugin." );
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        getLogger().info("Mirai working dir: " + Config.General.MiraiWorkingDir);

        if(Config.General.AddProperties.MiraiNoDesktop){
            System.setProperty("mirai.no-desktop","MiraiMC");
        }
        if(Config.General.AddProperties.MiraiSliderCaptchaSupported){
            System.setProperty("mirai.slider.captcha.supported","MiraiMC");
        }

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // ???????????????????????????????????????????????????

        // ????????????
        getLogger().info("Registering commands.");
        getServer().getCommandMap().register("", new MiraiCommand());
        getServer().getCommandMap().register("", new MiraiMcCommand());
        getServer().getCommandMap().register("", new MiraiVerifyCommand());

        if(Config.Bot.LogEvents){
            getLogger().info("Registering events.");
            this.getServer().getPluginManager().registerEvents(new Events(this), this);
        }

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

        // bStats??????
        if(Config.General.AllowBStats && !getDescription().getVersion().contains("dev")) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = 12744;
            new MetricsLite(this, pluginId);
        }

        // HTTP API
        if(Config.General.EnableHttpApi){
            getLogger().info("Initializing HttpAPI async task.");
            getServer().getScheduler().scheduleRepeatingTask(this, new MiraiHttpAPIResolver(this), Math.toIntExact(Config.HttpApi.MessageFetch.Interval * 20), true);
        }

        // ????????????
        if(!(Config.General.DisableSafeWarningMessage)){
            getLogger().warning("??????????????????????????????MiraiMC????????????????????????????????????????????????????????????");
            getLogger().warning("????????????Github??????????????????????????????????????????: https://github.com/DreamVoid/MiraiMC");
        }

        if(Config.General.CheckUpdate && !getDescription().getVersion().contains("dev")) {
            new NukkitRunnable() {
                @Override
                public void run() {
                    getLogger().info("Checking update...");
                    try {
                        PluginUpdate fetch = new PluginUpdate();
                        String version = !getDescription().getVersion().contains("-") ? fetch.getLatestRelease() : fetch.getLatestPreRelease();
                        if (fetch.isBlocked(getDescription().getVersion())) {
                            getLogger().error("???????????????????????????????????????????????????????????????????????????");
                            getLogger().error("??????????????????????????????: " + version);
                            getLogger().error("???Github????????????: https://github.com/DreamVoid/MiraiMC/releases/latest");
                        } else if (!getDescription().getVersion().equals(version)) {
                            getLogger().info("??????????????????????????????????????????: " + version);
                            getLogger().info("???Github????????????: https://github.com/DreamVoid/MiraiMC/releases/latest");
                        } else getLogger().info("???????????????????????????");
                    } catch (IOException e) {
                        getLogger().warning("An error occurred while fetching the latest version, reason: " + e);
                    }
                }
            }.runTaskAsynchronously(this);
        }

        new NukkitRunnable() {
            @Override
            public void run() {
                try {
                    List<String> announcement = Info.init().announcement;
                    if(announcement != null){
                        getLogger().info("========== [ MiraiMC ????????? ] ==========");
                        announcement.forEach(s -> getLogger().info(s));
                        getLogger().info("=======================================");
                    }
                } catch (IOException ignored) {}
            }
        }.runTaskLaterAsynchronously(this, 40);

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
                        getLogger().error("Failed to close SQLite database!");
                        getLogger().error("Reason: " + e);
                    }
                }
                break;
            }
            case "mysql": {
                if (Utils.ds != null) {
                    getLogger().info("Closing MySQL database.");
                    Utils.closeMySQL();
                }
                break;
            }
        }

        getLogger().info("All tasks done. Thanks for use MiraiMC!");
    }

}
