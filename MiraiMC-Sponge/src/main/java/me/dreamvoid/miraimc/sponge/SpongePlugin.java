package me.dreamvoid.miraimc.sponge; // TODO: 打包以后要把META-INF/versions/11/的文件弄掉，要不然加载不了 https://www.cnblogs.com/hejunhong/p/10696978.html

import com.google.inject.Inject;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoader;
import me.dreamvoid.miraimc.internal.Utils;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

@Plugin(id = "miraimc", name = "MiraiMC", description = "MiraiBot for Minecraft server",version = "1.5-pre2", url = "https://github.com/DreamVoid/MiraiMC", authors = {"DreamVoid"})
public class SpongePlugin {
    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File dataFolder;

    @Inject
    private PluginContainer pluginContainer;

    private MiraiEvent MiraiEvent;

    /**
     * 触发 GamePreInitializationEvent 时，插件准备进行初始化，这时默认的 Logger 已经准备好被调用，同时你也可以开始引用配置文件中的内容。
     */
    @Listener
    public void onPluginLoad(GamePreInitializationEvent e) {
        try {
            Utils.setLogger(logger);
            Utils.setClassLoader(this.getClass().getClassLoader());
            new SpongeConfig(this).loadConfig();

            if(Config.Gen_MiraiCoreVersion.equalsIgnoreCase("latest")) {
                MiraiLoader.loadMiraiCore();
            } else {
                MiraiLoader.loadMiraiCore(Config.Gen_MiraiCoreVersion);
            }
            MiraiEvent = new MiraiEvent(this);
            //this.MiraiAutoLogin = new MiraiAutoLogin(this);
        } catch (Exception ex) {
            getLogger().warning("An error occurred while loading plugin.");
            ex.printStackTrace();
        }
    }

    /**
     * 触发 GameInitializationEvent 时，插件应该完成他所需功能的所有应该完成的准备工作，你应该在这个事件发生时注册监听事件。
     */
    @Listener
    public void onPluginLoad(GameInitializationEvent e) {
        getLogger().info("Mirai working dir: " + Config.Gen_MiraiWorkingDir);

        if(Config.Gen_AddProperties_MiraiNoDesktop) System.setProperty("mirai.no-desktop", "MiraiMC");
        if(Config.Gen_AddProperties_MiraiSliderCaptchaSupported) System.setProperty("mirai.slider.captcha.supported", "MiraiMC");

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        /*
        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人
        */

        //getLogger().info("Registering commands.");
        //for (String s : Arrays.asList("mirai", "miraimc", "miraiverify")) { Objects.requireNonNull(getCommand(s)).setExecutor(new Commands(this)); }

        /*
        if(Config.Bot_LogEvents){
            getLogger().info("Registering events.");
            Bukkit.getPluginManager().registerEvents(new Events(), this);
        }
        */

        switch (Config.DB_Type.toLowerCase()){
            case "sqlite":
            default: {
                getLogger().info("Initializing SQLite database.");
                try {
                    Utils.initializeSQLite();
                } catch (SQLException | ClassNotFoundException ex) {
                    if(Config.Gen_FriendlyException) {
                        getLogger().warning("Failed to initialize SQLite database, reason: " + e);
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
        /*if(Config.Gen_AllowBStats) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = ;
            new Metrics(this, pluginId);
        }*/

        // 安全警告
        if(!(Config.Gen_DisableSafeWarningMessage)){
            getLogger().warning("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warning("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        getLogger().info("All tasks done. Welcome to use MiraiMC!");

    }
    public Logger getLogger() {
        return logger;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public PluginContainer getPluginContainer() {
        return pluginContainer;
    }
}
