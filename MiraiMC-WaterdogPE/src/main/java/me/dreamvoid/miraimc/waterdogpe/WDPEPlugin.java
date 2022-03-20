package me.dreamvoid.miraimc.waterdogpe;

import dev.waterdog.waterdogpe.event.defaults.PlayerChatEvent;
import dev.waterdog.waterdogpe.plugin.Plugin;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoader;
import me.dreamvoid.miraimc.internal.PluginUpdate;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.waterdogpe.commands.MiraiCommand;
import me.dreamvoid.miraimc.waterdogpe.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.waterdogpe.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.waterdogpe.event.MiraiGroupMessageEvent;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author zixuan007
 * @version 1.0
 * @description: 代理端机器人
 * @date 2022/3/19 12:57 AM
 */
public class WDPEPlugin extends Plugin {

    private static WDPEPlugin instance = null;

    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;


    @Override
    public void onEnable() {
        instance = this;
        try {
            Utils.setLogger(new WDPELogger("MiraiMC-Nukkit", null, this));
            Utils.setClassLoader(this.getClass().getClassLoader());
            System.out.println(getInstance().getConfig());
            new WDPEConfig(this).loadConfig();

            MiraiLoader.loadMiraiCore();
            this.MiraiEvent = new MiraiEvent(this);
            this.MiraiAutoLogin = new MiraiAutoLogin(this);
        } catch (Exception e) {
            getLogger().warning("An error occurred while loading plugin.");
            e.printStackTrace();
        }

        getLogger().info("Mirai working dir: " + Config.Gen_MiraiWorkingDir);

        if (Config.Gen_AddProperties_MiraiNoDesktop) {
            System.setProperty("mirai.no-desktop", "MiraiMC");
        }
        if (Config.Gen_AddProperties_MiraiSliderCaptchaSupported) {
            System.setProperty("mirai.slider.captcha.supported", "MiraiMC");
        }

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        // 注册命令
        getLogger().info("Registering commands.");
        getProxy().getCommandMap().registerCommand(new MiraiCommand(this));
//        getServer().getCommandMap().register("", new MiraiMcCommand(this));
        getProxy().getCommandMap().registerCommand(new MiraiVerifyCommand());

        if (Config.Bot_LogEvents) {
            getLogger().info("Registering events.");
            getProxy().getEventManager().subscribe(MiraiGroupMessageEvent.class, Events::onMiraiGroupMessageReceived);
            getProxy().getEventManager().subscribe(MiraiFriendMessageEvent.class, Events::onMiraiFriendMessageReceived);
        }

        if (Config.DB_AllowDatabases)
            switch (Config.DB_Type.toLowerCase()) {
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


        // 安全警告
        if (!(Config.Gen_DisableSafeWarningMessage)) {
            getLogger().warning("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warning("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        getProxy().getScheduler().scheduleAsync(() -> {
            getLogger().info("Checking update...");
            try {
                PluginUpdate fetch = new PluginUpdate();
                String version = !getDescription().getVersion().contains("-") ? fetch.getLatestRelease() : fetch.getLatestPreRelease();
                if (!getDescription().getVersion().equals(version)) {
                    getLogger().info("已找到新的插件更新，最新版本: " + version);
                    getLogger().info("从Github下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                } else getLogger().info("你使用的是最新版本");
            } catch (IOException e) {
                getLogger().warning("An error occurred while fetching the latest version, reason: " + e);
            }
        });

        getLogger().info("All tasks done. Welcome to use MiraiMC!");
    }

    public static WDPEPlugin getInstance() {
        return instance;
    }

    public static void setInstance(WDPEPlugin instance) {
        WDPEPlugin.instance = instance;
    }
}
