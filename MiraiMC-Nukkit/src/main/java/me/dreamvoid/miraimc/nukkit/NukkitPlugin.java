package me.dreamvoid.miraimc.nukkit;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import me.dreamvoid.miraimc.interfaces.IMiraiAutoLogin;
import me.dreamvoid.miraimc.interfaces.IMiraiEvent;
import me.dreamvoid.miraimc.LifeCycle;
import me.dreamvoid.miraimc.interfaces.Platform;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.interfaces.PluginConfig;
import me.dreamvoid.miraimc.loader.LibraryLoader;
import me.dreamvoid.miraimc.nukkit.commands.MiraiCommand;
import me.dreamvoid.miraimc.nukkit.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.nukkit.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.nukkit.utils.MetricsLite;

import java.net.URLClassLoader;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class NukkitPlugin extends PluginBase implements Platform {
    private static NukkitPlugin nukkitPlugin;

    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;
    private final LifeCycle lifeCycle;
    private Logger NukkitLogger;
    private PluginConfig config;
    private final LibraryLoader loader;

    public static NukkitPlugin getInstance() {
        return nukkitPlugin;
    }

    public NukkitPlugin(){
        lifeCycle = new LifeCycle(this);
        lifeCycle.startUp(new NukkitLogger("MiraiMC-Nukkit", Server.getInstance().getLogger()));
        loader = new LibraryLoader((URLClassLoader) this.getClass().getClassLoader());
    }

    @Override
    public void onLoad() {
        nukkitPlugin = this;
        try {
            NukkitLogger = new NukkitLogger("MiraiMC-Nukkit", getLogger());
            config = new NukkitConfig(this);
            lifeCycle.preLoad();

            MiraiAutoLogin = new MiraiAutoLogin(this);
            MiraiEvent = new MiraiEvent(this);
        } catch (Exception e) {
            Utils.resolveException(e, NukkitLogger, "加载 MiraiMC 阶段 1 时出现异常！");
        }
    }

    @Override
    public void onEnable() {
        try {
            lifeCycle.postLoad();

            // 注册命令 // TODO: 把Nukkit的注册命令并入主代码
            getLogger().info("正在注册命令.");
            getServer().getCommandMap().register("", new MiraiCommand());
            getServer().getCommandMap().register("", new MiraiMcCommand());
            getServer().getCommandMap().register("", new MiraiVerifyCommand());

            // 监听事件
            if (config.General_LogEvents) {
                getLogger().info("正在注册事件监听器.");
                this.getServer().getPluginManager().registerEvents(new Events(this), this);
            }

            // bStats统计
            if (config.General_AllowBStats && !getDescription().getVersion().contains("dev")) {
                getLogger().info("正在初始化 bStats 统计.");
                int pluginId = 12744;
                new MetricsLite(this, pluginId);
            }
        } catch (Exception e){
            Utils.resolveException(e, NukkitLogger, "加载 MiraiMC 阶段 2 时出现异常！");
        }
    }

    @Override
    public void onDisable() {
        lifeCycle.unload();
    }

    @Override
    public String getPlayerName(UUID uuid) {
        return getServer().getOfflinePlayer(uuid).getName();
    }

    @Override
    public UUID getPlayerUUID(String name) {
        return getServer().getOfflinePlayer(name).getUniqueId();
    }

    @Override
    public void runTaskAsync(Runnable task) {
        getServer().getScheduler().scheduleTask(this, task, true);
    }

    @Override
    public void runTaskLaterAsync(Runnable task, long delay) {
        getServer().getScheduler().scheduleDelayedTask(this, task, Integer.parseInt(String.valueOf(delay)), true);
    }

    @Override
    public void runTaskTimerAsync(Runnable task, long delay, long period) {
        getServer().getScheduler().scheduleDelayedRepeatingTask(this, task, Integer.parseInt(String.valueOf(delay)), Integer.parseInt(String.valueOf(period)), true);
    }

    @Override
    public String getPluginName() {
        return getDescription().getName();
    }

    @Override
    public String getPluginVersion() {
        return getDescription().getVersion();
    }

    @Override
    public List<String> getAuthors() {
        return getDescription().getAuthors();
    }

    @Override
    public Logger getPluginLogger() {
        return NukkitLogger;
    }

    @Override
    public ClassLoader getPluginClassLoader() {
        return this.getClass().getClassLoader();
    }

    @Override
    public IMiraiAutoLogin getAutoLogin() {
        return MiraiAutoLogin;
    }

    @Override
    public IMiraiEvent getMiraiEvent() {
        return MiraiEvent;
    }

    @Override
    public LibraryLoader getLibraryLoader() {
        return loader;
    }

    @Override
    public String getType() {
        return "Nukkit";
    }

    @Override
    public PluginConfig getPluginConfig() {
        return config;
    }
}
