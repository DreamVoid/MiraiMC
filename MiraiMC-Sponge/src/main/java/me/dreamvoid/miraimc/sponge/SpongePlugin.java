package me.dreamvoid.miraimc.sponge;

import com.google.inject.Inject;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoader;
import me.dreamvoid.miraimc.internal.PluginUpdate;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.sponge.commands.MiraiCommand;
import me.dreamvoid.miraimc.sponge.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.sponge.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.sponge.utils.Metrics;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.metric.MetricsConfigManager;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Plugin(id = "miraimc", name = "MiraiMC", description = "MiraiBot for Minecraft server",version = "1.5-rc1", url = "https://github.com/DreamVoid/MiraiMC", authors = {"DreamVoid"})
public class SpongePlugin {
    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File dataFolder;

    @Inject
    private PluginContainer pluginContainer;

    @Inject
    private MetricsConfigManager metricsConfigManager;

    private MiraiEvent MiraiEvent;
    //private MiraiAutoLogin MiraiAutoLogin;

    /**
     * 触发 GamePreInitializationEvent 时，插件准备进行初始化，这时默认的 Logger 已经准备好被调用，同时你也可以开始引用配置文件中的内容。
     */
    @Listener
    public void onLoad(GamePreInitializationEvent e) {
        try {
            java.util.logging.Logger log4j = new SpongeLogger("MiraiMC", null, this);
            Utils.setLogger(log4j);
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
            getLogger().warn("An error occurred while loading plugin.");
            ex.printStackTrace();
        }
    }

    /**
     * 触发 GameInitializationEvent 时，插件应该完成他所需功能的所有应该完成的准备工作，你应该在这个事件发生时注册监听事件。
     */
    @Listener
    public void onEnable(GameInitializationEvent e) {
        getLogger().info("Mirai working dir: " + Config.Gen_MiraiWorkingDir);

        if(Config.Gen_AddProperties_MiraiNoDesktop) System.setProperty("mirai.no-desktop", "MiraiMC");
        if(Config.Gen_AddProperties_MiraiSliderCaptchaSupported) System.setProperty("mirai.slider.captcha.supported", "MiraiMC");

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        //getLogger().info("Loading auto-login file.");
        //MiraiAutoLogin.loadFile();
        //MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        if(Config.Bot_LogEvents){
            getLogger().info("Registering events.");
            Sponge.getEventManager().registerListeners(this, new Events());
        }


        switch (Config.DB_Type.toLowerCase()){
            case "sqlite":
            default: {
                getLogger().info("Initializing SQLite database.");
                try {
                    Utils.initializeSQLite();
                } catch (SQLException | ClassNotFoundException ex) {
                    if(Config.Gen_FriendlyException) {
                        getLogger().warn("Failed to initialize SQLite database, reason: " + e);
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
            if(this.metricsConfigManager.getCollectionState(this.pluginContainer).asBoolean()){
                getLogger().info("Initializing bStats metrics.");
                getLogger().info("If you do not want bStats to collect data, please execute command /sponge metrics miraimc disable");
                int pluginId = 12847;
                new Metrics(this.pluginContainer,getLogger(), getDataFolder().toPath(), pluginId);
            } else {
                getLogger().warn("You enabled bStats in config, but MetricsConfigManager returns that MiraiMC does not allow collect information, so bStats is disabled.");
                getLogger().warn("To enable bStats, please execute command /sponge metrics miraimc enable");
                getLogger().warn("Or disable bStats in config.yml file to hide this warning.");
            }
        }

        // 安全警告
        if(!(Config.Gen_DisableSafeWarningMessage)){
            getLogger().warn("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warn("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        Task.builder().async().execute(() -> {
            getLogger().info("正在检查更新...");
            try {
                String version = PluginUpdate.getVersion();
                if(!pluginContainer.getVersion().get().equals(version)){
                    getLogger().info("已找到新的插件更新，最新版本: " + version);
                    getLogger().info("从Github下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                } else getLogger().info("你使用的是最新版本");
            } catch (IOException e1) {
                getLogger().warn("An error occurred while fetching the latest version, reason: " + e1);
            }
        }).submit(this);

        getLogger().info("Some initialization tasks will continue to run afterwards.");
        getLogger().info("All tasks done. Welcome to use MiraiMC!");

    }

    /**
     * 触发 GameStartingServerEvent 时，服务器初始化和世界载入都已经完成，你应该在这时注册插件命令。
     */
    @Listener
    public void onServerLoaded(GameStartingServerEvent e) {
        getLogger().info("Registering commands.");

        CommandSpec mirai = CommandSpec.builder().description(Text.of("MiraiMC Bot Command.")).permission("miraimc.command.mirai").executor(new MiraiCommand(this))
                .arguments(GenericArguments.remainingJoinedStrings((Text.of("args"))))
                .build();
        CommandSpec miraimc = CommandSpec.builder().description(Text.of("MiraiMC Plugin Command.")).permission("miraimc.command.miraimc").executor(new MiraiMcCommand(this))
                .arguments(GenericArguments.remainingJoinedStrings((Text.of("args"))))
                .build();
        CommandSpec miraiverify = CommandSpec.builder().description(Text.of("MiraiMC LoginVerify Command.")).permission("miraimc.command.miraiverify").executor(new MiraiVerifyCommand(this))
                .arguments(GenericArguments.remainingJoinedStrings((Text.of("args"))))
                .build();

        Sponge.getCommandManager().register(this, mirai, "mirai");
        Sponge.getCommandManager().register(this, miraimc, "miraimc");
        Sponge.getCommandManager().register(this, miraiverify, "miraiverify");
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
