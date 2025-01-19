package me.dreamvoid.miraimc;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.interfaces.Platform;
import me.dreamvoid.miraimc.internal.MiraiLoader;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.PluginUpdate;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.database.DatabaseHandler;
import me.dreamvoid.miraimc.internal.database.MySQL;
import me.dreamvoid.miraimc.internal.database.SQLite;
import me.dreamvoid.miraimc.internal.webapi.Info;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * MiraiMC 生命周期
 */
public final class LifeCycle {
    private static Platform platform;
    private Logger logger;

    public LifeCycle(Platform plugin){
        platform = plugin;
    }

    public static Platform getPlatform() {
        return platform;
    }

    /**
     * 此方法应在插件实例化时调用，用于设置必要的运行环境，此时配置尚未初始化。
     * @param logger {@link java.util.logging.Logger} 实例。由于各平台初始化 Logger 的时机不一，因此需要一个 Logger 来辅助。
     */
    public void startUp(Logger logger) {
        logger.info("Preparing MiraiMC start-up.");

        System.setProperty("mirai.no-desktop", "MiraiMC");
        System.setProperty("mirai.slider.captcha.supported", "MiraiMC");

        Utils.setLogger(logger);
        Utils.setClassLoader(this.getClass().getClassLoader());

        logger.info("Start-up tasks finished.");
    }

    /**
     * 此方法应在插件各项准备工作均已完成时调用。此时插件已经准备就绪，可以开始初始化配置文件，加载 mirai 核心。
     */
    public void preLoad() throws IOException {
        logger = platform.getPluginLogger();
        Utils.setLogger(logger);
        Utils.setClassLoader(platform.getPluginClassLoader());

        logger.info("Preparing MiraiMC pre-load.");

        // 加载配置
        logger.info("Loading config.");
        platform.getPluginConfig().loadConfig();
        if(platform.getPluginVersion().contains("dev-") && platform.getPluginConfig().General_MiraiCoreVersion.equalsIgnoreCase("stable")) {
            platform.getPluginConfig().General_MiraiCoreVersion = "latest"; // Fix dev version
        }

        logger.info("Mirai working dir: " + platform.getPluginConfig().General_MiraiWorkingDir);

        // 加载 mirai 核心
        if(System.getProperty("MiraiMC.do-not-load-mirai-core") == null){
            logger.info("Selected mirai core version: " + platform.getPluginConfig().General_MiraiCoreVersion);
            if (platform.getPluginConfig().General_MiraiCoreVersion.equalsIgnoreCase("latest")) {
                MiraiLoader.loadMiraiCore();
            } else if (platform.getPluginConfig().General_MiraiCoreVersion.equalsIgnoreCase("stable")) {
                MiraiLoader.loadMiraiCore(MiraiLoader.getStableVersion(platform.getPluginVersion()));
            } else {
                MiraiLoader.loadMiraiCore(platform.getPluginConfig().General_MiraiCoreVersion);
            }
        } else {
            logger.info("MiraiMC will not load mirai core, please ensure you have custom mirai core loaded.");
        }

        logger.info("Pre-load tasks finished.");
    }

    /**
     * 此方法应在 mirai 核心加载完毕、服务端准备就绪后调用。<br>
     * 此方法调用完成后，可以继续执行HTTP API监听等未完成的任务。
     */
    @SuppressWarnings("DefaultNotLastCaseInSwitch")
    public void postLoad() {
        logger.info("Preparing MiraiMC post-load.");

        // 数据库
        try {
            switch (platform.getPluginConfig().Database_Type.toLowerCase()){
                case "sqlite":
                default: {
                    logger.info("Initializing SQLite database.");
                    DatabaseHandler.setDatabase(new SQLite());
                    break;
                }
                case "mysql": {
                    logger.info("Initializing MySQL database.");
                    DatabaseHandler.setDatabase(new MySQL());
                    break;
                }
            }
            DatabaseHandler.getDatabase().initialize();
        } catch (ClassNotFoundException e) {
            logger.warning("Failed to initialize database, reason: " + e);
        }

        // 接入 mirai 事件
        logger.info("Starting Mirai-Event listener.");
        platform.getMiraiEvent().startListenEvent();

        // 自动登录机器人
        logger.info("Starting auto-login bot.");
        platform.getAutoLogin().loadFile();
        platform.getAutoLogin().startAutoLogin();

        // 安全警告
        if(!(platform.getPluginConfig().General_DisableSafeWarningMessage)){
            logger.warning("确保您正在使用开源的 MiraiMC 插件，未知来源的插件可能会盗取您的账号！");
            logger.warning("请始终从 GitHub 或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        // 公告板
        platform.runTaskLaterAsync(() -> {
            try {
                List<String> announcement = Info.get(false).announcement;
                if(announcement != null && !announcement.isEmpty()){
                    logger.info("========== [ MiraiMC 公告版 ] ==========");
                    announcement.forEach(logger::info);
                    logger.info("=======================================");
                }
            } catch (IOException ignored) {}
        }, 40);

        // 检查更新
        if(platform.getPluginConfig().General_CheckUpdate && !platform.getPluginVersion().contains("dev")){
            platform.runTaskTimerAsync(() -> {
                logger.info("正在检查更新...");
                try {
                    PluginUpdate fetch = new PluginUpdate();
                    String version = !platform.getPluginVersion().contains("-") ? fetch.getLatestRelease() : fetch.getLatestPreRelease();
                    int versionCode = !platform.getPluginVersion().contains("-") ? fetch.getLatestReleaseNo() : fetch.getLatestPreReleaseNo();
                    if (fetch.isBlocked(platform.getPluginVersion())) {
                        logger.severe("当前版本已停用，继续使用将不会得到作者的任何支持！");
                        logger.severe("请立刻更新到最新版本: " + version);
                        logger.severe("从 GitHub 下载更新: https://github.com/DreamVoid/MiraiMC/releases/tag/v" + version);
                        logger.severe("从 Modrinth 下载更新: https://modrinth.com/plugin/miraimc/version/" + versionCode);
                    } else if (!platform.getPluginVersion().equals(version)) {
                        logger.warning("已找到新的插件更新，最新版本: " + version);
                        logger.warning("从 GitHub 下载更新: https://github.com/DreamVoid/MiraiMC/releases/tag/v" + version);
                        logger.warning("从 Modrinth 下载更新: https://modrinth.com/plugin/miraimc/version/" + versionCode);
                    } else {
                        logger.info("你使用的是最新版本的 MiraiMC！");
                    }
                } catch (IOException e) {
                    logger.warning("An error occurred while fetching the latest version, reason: " + e);
                }
            },0, platform.getPluginConfig().General_CheckUpdatePeriod);
        }

        logger.info("Some initialization tasks will continue to run afterwards.");
        logger.info("Post-load tasks finished. Welcome to use MiraiMC!");
    }

    public void unload() {
        logger.info("Preparing MiraiMC unload.");

        // 取消所有的待验证机器人和已登录机器人进程
        logger.info("Closing all bots");
        MiraiLoginSolver.cancelAll();
        MiraiBot.getOnlineBots().forEach(l -> MiraiBot.getBot(l).close());

        // 停止事件监听
        logger.info("Stopping bot event listener.");
        platform.getMiraiEvent().stopListenEvent();

        // 停止数据库
        logger.info("Closing database.");
        DatabaseHandler.getDatabase().close();

        logger.info("Unload tasks finished. Thanks for use MiraiMC!");
    }
}
