package me.dreamvoid.miraimc.sponge;

import com.google.inject.Inject;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.libloader.MiraiLoader;
import me.dreamvoid.miraimc.sponge.commands.MiraiCommand;
import me.dreamvoid.miraimc.sponge.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.sponge.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.sponge.utils.Metrics;
import me.dreamvoid.miraimc.webapi.Info;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.metric.MetricsConfigManager;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Plugin(id = "miraimc",
        name = "MiraiMC",
        description = "MiraiBot for Minecraft server",
        version = "1.7.1",
        url = "https://github.com/DreamVoid/MiraiMC",
        authors = {"DreamVoid"}
)
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
    private MiraiAutoLogin MiraiAutoLogin;

    /**
     * 触发 GamePreInitializationEvent 时，插件准备进行初始化，这时默认的 Logger 已经准备好被调用，同时你也可以开始引用配置文件中的内容。
     */
    @Listener
    public void onLoad(GamePreInitializationEvent e) {
        System.setProperty("mirai.no-desktop", "MiraiMC");
        System.setProperty("mirai.slider.captcha.supported", "MiraiMC");

        try {
            java.util.logging.Logger log4j = new SpongeLogger("MiraiMC", null, this);
            Utils.setLogger(log4j);
            Utils.setClassLoader(this.getClass().getClassLoader());
            new SpongeConfig(this).loadConfig();

            if(Config.General.MiraiCoreVersion.equalsIgnoreCase("latest")) {
                MiraiLoader.loadMiraiCore();
            } else if(Config.General.MiraiCoreVersion.equalsIgnoreCase("stable")){
                MiraiLoader.loadMiraiCore(MiraiLoader.getStableVersion(getPluginContainer().getVersion().orElse("1.0")));
            } else {
                MiraiLoader.loadMiraiCore(Config.General.MiraiCoreVersion);
            }
            if(!Config.General.LegacyEventSupport){
                this.MiraiEvent = new MiraiEvent(this);
            } else this.MiraiEvent = new MiraiEventLegacy(this);
            this.MiraiAutoLogin = new MiraiAutoLogin(this);
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
        getLogger().info("Mirai working dir: " + Config.General.MiraiWorkingDir);

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        if(Config.Bot.LogEvents){
            getLogger().info("Registering events.");
            Sponge.getEventManager().registerListeners(this, new Events());
        }

        switch (Config.Database.Type.toLowerCase()){
            case "sqlite":
            default: {
                getLogger().info("Initializing SQLite database.");
                try {
                    Utils.initializeSQLite();
                } catch (SQLException | ClassNotFoundException ex) {
                    getLogger().warn("Failed to initialize SQLite database, reason: " + e);
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
            if(this.metricsConfigManager.getCollectionState(this.pluginContainer).asBoolean()){
                getLogger().info("Initializing bStats metrics.");
                int pluginId = 12847;
                new Metrics(this.pluginContainer,getLogger(), getDataFolder().toPath(), pluginId);
            } else {
                getLogger().warn("你在配置文件中启用了bStats，但是MetricsConfigManager告知MiraiMC不允许收集信息，因此bStats已关闭");
                getLogger().warn("要启用bStats，请执行命令 /sponge metrics miraimc enable");
                getLogger().warn("或者在配置文件中禁用bStats隐藏此警告");
            }
        }

        // HTTP API
        if(Config.General.EnableHttpApi){
            getLogger().info("Initializing HttpAPI async task.");
            Sponge.getScheduler().createTaskBuilder()
                    .async()
                    .execute(new MiraiHttpAPIResolver(this))
                    .intervalTicks(Config.HttpApi.MessageFetch.Interval)
                    .name("MiraiMC-HttpApi")
                    .submit(this);
        }

        // 安全警告
        if(!(Config.General.DisableSafeWarningMessage)){
            getLogger().warn("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warn("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        Sponge.getScheduler().createAsyncExecutor(this).schedule(() -> {
            try {
                List<String> announcement = Info.init().announcement;
                if(announcement != null){
                    getLogger().info("========== [ MiraiMC 公告版 ] ==========");
                    announcement.forEach(s -> getLogger().info(s));
                    getLogger().info("=======================================");
                }
            } catch (IOException ignored) {}
        }, 2, TimeUnit.SECONDS);

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

    /**
     * 触发 GameStoppingServerEvent 时，服务器会进入最后一个 Tick，紧接着就会开始保存世界。
     */
    @Listener
    public void onServerStopping(GameStoppingServerEvent event){
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
