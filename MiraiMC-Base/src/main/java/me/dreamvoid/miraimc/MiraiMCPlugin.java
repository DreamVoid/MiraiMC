package me.dreamvoid.miraimc;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.PluginUpdate;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.libloader.MiraiLoader;
import me.dreamvoid.miraimc.internal.webapi.Info;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MiraiMCPlugin {
    public static MiraiMCPlugin INSTANCE;

    public MiraiMCPlugin(PlatformPlugin plugin){
        INSTANCE = this;
        platform = plugin;
    }

    private static PlatformPlugin platform;

    public static PlatformPlugin getPlatform() {
        return platform;
    }

    /**
     * 此方法应在插件实例化时调用，用于设置必要的运行环境，此时配置尚未初始化。
     */
    public void startUp() {
        platform.getPluginLogger().info("Preparing MiraiMC start-up.");

        System.setProperty("mirai.no-desktop", "MiraiMC");
        System.setProperty("mirai.slider.captcha.supported", "MiraiMC");

        Utils.setLogger(platform.getPluginLogger());
        Utils.setClassLoader(platform.getPluginClassLoader());

        platform.getPluginLogger().info("Start-up tasks finished.");
    }

    /**
     * 此方法应在插件各项准备工作均已完成时调用。此时插件已经准备就绪，可以开始初始化配置文件，加载 mirai 核心。
     */
    public void preLoad() throws IOException, ParserConfigurationException, SAXException {
        platform.getPluginLogger().info("Preparing MiraiMC pre-load.");

        // 加载配置
        platform.getPluginLogger().info("Loading config.");
        platform.getPluginConfig().loadConfig();

        platform.getPluginLogger().info("Mirai working dir: " + MiraiMCConfig.General.MiraiWorkingDir);

        // 加载 mirai 核心
        platform.getPluginLogger().info("Selected mirai core version: " + MiraiMCConfig.General.MiraiCoreVersion);
        if (MiraiMCConfig.General.MiraiCoreVersion.equalsIgnoreCase("latest")) {
            MiraiLoader.loadMiraiCore();
        } else if (MiraiMCConfig.General.MiraiCoreVersion.equalsIgnoreCase("stable")) {
            MiraiLoader.loadMiraiCore(MiraiLoader.getStableVersion(getPlatform().getPluginVersion()));
        } else {
            MiraiLoader.loadMiraiCore(MiraiMCConfig.General.MiraiCoreVersion);
        }

        platform.getPluginLogger().info("Pre-load tasks finished.");
    }

    /**
     * 此方法应在 mirai 核心加载完毕、服务端准备就绪后调用。<br>
     * 此方法调用完成后，可以继续执行HTTP API监听等未完成的任务。
     */
    public void postLoad() {
        platform.getPluginLogger().info("Preparing MiraiMC post-load.");

        // 数据库
        switch (MiraiMCConfig.Database.Type.toLowerCase()){
            case "sqlite":
            default: {
                platform.getPluginLogger().info("Initializing SQLite database.");
                try {
                    Utils.initializeSQLite();
                } catch (SQLException | ClassNotFoundException e) {
                    platform.getPluginLogger().warning("Failed to initialize SQLite database, reason: " + e);
                }
                break;
            }
            case "mysql": {
                platform.getPluginLogger().info("Initializing MySQL database.");
                Utils.initializeMySQL();
                break;
            }
        }

        // 接入 mirai 事件
        platform.getPluginLogger().info("Starting Mirai-Event listener.");
        platform.getMiraiEvent().startListenEvent();

        // 自动登录机器人
        platform.getPluginLogger().info("Starting Auto-Login bot.");
        platform.getAutoLogin().loadFile();
        platform.getAutoLogin().doStartUpAutoLogin();

        // 安全警告
        if(!(MiraiMCConfig.General.DisableSafeWarningMessage)){
            platform.getPluginLogger().warning("确保您正在使用开源的 MiraiMC 插件，未知来源的插件可能会盗取您的账号！");
            platform.getPluginLogger().warning("请始终从 GitHub 或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        // 公告板
        platform.runTaskLaterAsync(() -> {
            try {
                List<String> announcement = Info.init().announcement;
                if(announcement != null){
                    platform.getPluginLogger().info("========== [ MiraiMC 公告版 ] ==========");
                    announcement.forEach(s -> platform.getPluginLogger().info(s));
                    platform.getPluginLogger().info("=======================================");
                }
            } catch (IOException ignored) {}
        }, 40);

        // 检查更新
        if(MiraiMCConfig.General.CheckUpdate && !platform.getPluginVersion().contains("dev")){
            platform.runTaskAsync(() -> {
                platform.getPluginLogger().info("Checking update...");
                try {
                    PluginUpdate fetch = new PluginUpdate();
                    String version = !platform.getPluginVersion().contains("-") ? fetch.getLatestRelease() : fetch.getLatestPreRelease();
                    if (fetch.isBlocked(platform.getPluginVersion())) {
                        platform.getPluginLogger().severe("当前版本已停用，继续使用将不会得到作者的任何支持！");
                        platform.getPluginLogger().severe("请立刻更新到最新版本: " + version);
                        platform.getPluginLogger().severe("从 GitHub 下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                    } else if (!platform.getPluginVersion().equals(version)) {
                        platform.getPluginLogger().info("已找到新的插件更新，最新版本: " + version);
                        platform.getPluginLogger().info("从 GitHub 下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                    } else {
                        platform.getPluginLogger().info("你使用的是最新版本的 MiraiMC！");
                    }
                } catch (IOException e) {
                    platform.getPluginLogger().warning("An error occurred while fetching the latest version, reason: " + e);
                }
            });
        }

        platform.getPluginLogger().info("Some initialization tasks will continue to run afterwards.");
        platform.getPluginLogger().info("Post-load tasks finished. Welcome to use MiraiMC!");
    }

    public void unload() {
        platform.getPluginLogger().info("Preparing MiraiMC unload.");

        // 取消所有的待验证机器人和已登录机器人进程
        platform.getPluginLogger().info("Closing all bots");
        MiraiLoginSolver.cancelAll();
        for (long bots : MiraiBot.getOnlineBots()){
            MiraiBot.getBot(bots).close();
        }

        // 停止事件监听
        platform.getPluginLogger().info("Stopping bot event listener.");
        platform.getMiraiEvent().stopListenEvent();

        // 停止数据库
        switch (MiraiMCConfig.Database.Type.toLowerCase()){
            case "sqlite":
            default: {
                if (Utils.connection != null) {
                    platform.getPluginLogger().info("Closing SQLite database.");
                    try {
                        Utils.closeSQLite();
                    } catch (SQLException e) {
                        platform.getPluginLogger().severe("Failed to close SQLite database!");
                        platform.getPluginLogger().severe("Reason: " + e);
                    }
                }
                break;
            }
            case "mysql": {
                if(Utils.ds != null) {
                    platform.getPluginLogger().info("Closing MySQL database.");
                    Utils.closeMySQL();
                }
                break;
            }
        }

        platform.getPluginLogger().info("Unload tasks finished. Thanks for use MiraiMC!");
    }
}
