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
 * 负责管理插件的整个生命周期，包括启动、加载和卸载等关键阶段
 */
public final class LifeCycle {
    private static Platform platform;
    private Logger logger;

    public LifeCycle(Platform plugin){
        platform = plugin;
    }

    /**
     * 获取当前平台实例
     * @return Platform 实例
     */
    public static Platform getPlatform() {
        return platform;
    }

    /**
     * 此方法应在插件实例化时调用，用于设置必要的运行环境，此时配置尚未初始化。
     * @param logger {@link java.util.logging.Logger} 实例。由于各平台初始化 Logger 的时机不一，因此需要一个 Logger 来辅助。
     */
    public void startUp(Logger logger) {
        logger.info("准备 MiraiMC 初始化.");

        // 设置Mirai相关系统属性
        System.setProperty("mirai.no-desktop", "MiraiMC");
        System.setProperty("mirai.slider.captcha.supported", "MiraiMC");

        // 初始化工具类
        Utils.setLogger(logger);
        Utils.setClassLoader(this.getClass().getClassLoader());

        logger.info("初始化任务完成.");
    }

    /**
     * 此方法应在插件各项准备工作均已完成时调用。此时插件已经准备就绪，可以开始初始化配置文件，加载 mirai 核心。
     */
    public void preLoad() throws IOException {
        logger = platform.getPluginLogger();
        Utils.setLogger(logger);
        Utils.setClassLoader(platform.getPluginClassLoader());

        logger.info("准备 MiraiMC 预加载.");

        // 加载配置
        logger.info("正在加载配置文件.");
        platform.getPluginConfig().loadConfig();
        
        // 开发版本强制使用最新核心
        if (platform.getPluginVersion().contains("+") &&
            platform.getPluginConfig().General_MiraiCoreVersion.equalsIgnoreCase("stable")) {
            platform.getPluginConfig().General_MiraiCoreVersion = "latest";
        }

        logger.info("Mirai 工作目录: " + platform.getPluginConfig().General_MiraiWorkingDir);

        // 加载 mirai 核心
        if(System.getProperty("MiraiMC.do-not-load-mirai-core") == null){
            logger.info("使用的 mirai 核心版本: " + platform.getPluginConfig().General_MiraiCoreVersion);
            if (platform.getPluginConfig().General_MiraiCoreVersion.equalsIgnoreCase("latest")) {
                MiraiLoader.loadMiraiCore();
            } else if (platform.getPluginConfig().General_MiraiCoreVersion.equalsIgnoreCase("stable")) {
                MiraiLoader.loadMiraiCore(MiraiLoader.getStableVersion(platform.getPluginVersion()));
            } else {
                MiraiLoader.loadMiraiCore(platform.getPluginConfig().General_MiraiCoreVersion);
            }
        } else {
            logger.info("MiraiMC 不会加载 mirai 核心, 请确保有其他插件能够加载 mirai 核心.");
        }

        logger.info("预加载任务完成.");
    }

    /**
     * 此方法应在 mirai 核心加载完毕、服务端准备就绪后调用。<br>
     * 此方法调用完成后，可以继续执行HTTP API监听等未完成的任务。
     */
    @SuppressWarnings("DefaultNotLastCaseInSwitch")
    public void postLoad() {
        logger.info("准备 MiraiMC 后加载.");

        // 数据库
        try {
            switch (platform.getPluginConfig().Database_Type.toLowerCase()){
                case "sqlite":
                default: {
                    logger.info("初始化 SQLite 数据库.");
                    DatabaseHandler.setDatabase(new SQLite());
                    break;
                }
                case "mysql": {
                    logger.info("初始化 MySQL 数据库.");
                    DatabaseHandler.setDatabase(new MySQL());
                    break;
                }
            }
            DatabaseHandler.getDatabase().initialize();
        } catch (ClassNotFoundException e) {
            logger.warning("无法初始化数据库，原因: " + e);
        }

        // 接入 mirai 事件
        logger.info("正在启动 Mirai 事件监听器.");
        platform.getMiraiEvent().startListenEvent();

        // 自动登录机器人
        logger.info("正在初始化自动登录机器人任务.");
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
                    logger.warning("检查更新时出现了一个异常: " + e);
                }
            },0, platform.getPluginConfig().General_CheckUpdatePeriod);
        }

        logger.info("某些加载任务将在之后继续。");
        logger.info("后加载任务完成. 欢迎使用 MiraiMC！");
    }

    public void unload() {
        logger.info("准备 MiraiMC 卸载.");

        // 取消所有的待验证机器人和已登录机器人进程
        logger.info("正在关闭所有机器人.");
        MiraiLoginSolver.cancelAll();
        MiraiBot.getOnlineBots().forEach(l -> MiraiBot.getBot(l).close());

        // 停止事件监听
        logger.info("正在停止事件监听器.");
        platform.getMiraiEvent().stopListenEvent();

        // 停止数据库
        logger.info("正在关闭数据库.");
        DatabaseHandler.getDatabase().close();

        logger.info("卸载任务完成. 感谢使用 MiraiMC！");
    }
}
