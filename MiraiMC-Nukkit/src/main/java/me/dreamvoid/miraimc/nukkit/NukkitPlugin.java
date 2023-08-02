package me.dreamvoid.miraimc.nukkit;

import cn.nukkit.plugin.PluginBase;
import me.dreamvoid.miraimc.IMiraiAutoLogin;
import me.dreamvoid.miraimc.IMiraiEvent;
import me.dreamvoid.miraimc.MiraiMCPlugin;
import me.dreamvoid.miraimc.Platform;
import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.internal.loader.LibraryLoader;
import me.dreamvoid.miraimc.nukkit.commands.MiraiCommand;
import me.dreamvoid.miraimc.nukkit.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.nukkit.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.nukkit.utils.MetricsLite;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class NukkitPlugin extends PluginBase implements Platform {
    private static NukkitPlugin nukkitPlugin;

    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;
    private MiraiMCPlugin lifeCycle;
    private MiraiMCConfig platformConfig;
    private Logger NukkitLogger;

    public static NukkitPlugin getInstance() {
        return nukkitPlugin;
    }

    @Override
    public void onLoad() {
        nukkitPlugin = this;
        try {
            NukkitLogger = new NukkitLogger("MiraiMC-Nukkit", this);
            lifeCycle = new MiraiMCPlugin(this);
            lifeCycle.startUp();
            platformConfig = new NukkitConfig(this);

            lifeCycle.preLoad();
            MiraiAutoLogin = new MiraiAutoLogin(this);
            MiraiEvent = new MiraiEvent(this);

            LibraryLoader.loadJarMaven("org.xerial", "sqlite-jdbc", "3.36.0.3");
        } catch (Exception e) {
            getLogger().warning("An error occurred while loading plugin." );
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        lifeCycle.postLoad();

        // 注册命令 // TODO: 把Nukkit的注册命令并入主代码
        getLogger().info("Registering commands.");
        getServer().getCommandMap().register("", new MiraiCommand());
        getServer().getCommandMap().register("", new MiraiMcCommand());
        getServer().getCommandMap().register("", new MiraiVerifyCommand());

        // 监听事件
        if(MiraiMCConfig.General.LogEvents){
            getLogger().info("Registering events.");
            this.getServer().getPluginManager().registerEvents(new Events(this), this);
        }

        // bStats统计
        if(MiraiMCConfig.General.AllowBStats && !getDescription().getVersion().contains("dev")) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = 12744;
            new MetricsLite(this, pluginId);
        }

        // HTTP API
        if(MiraiMCConfig.General.EnableHttpApi){
            getLogger().info("Initializing HttpAPI async task.");
            getServer().getScheduler().scheduleRepeatingTask(this, new MiraiHttpAPIResolver(this), Math.toIntExact(MiraiMCConfig.HttpApi.MessageFetch.Interval * 20), true);
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
        getServer().getScheduler().scheduleTask(this,task,true);
    }

    @Override
    public void runTaskLaterAsync(Runnable task, long delay) {
        getServer().getScheduler().scheduleDelayedTask(this, task, Integer.parseInt(String.valueOf(delay)),true);
    }

    @Override
    public int runTaskTimerAsync(Runnable task, long period) {
        return getServer().getScheduler().scheduleRepeatingTask(this, task, Integer.parseInt(String.valueOf(period)), true).getTaskId();
    }

    @Override
    public void cancelTask(int taskId) {
        getServer().getScheduler().cancelTask(taskId);
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
    public MiraiMCConfig getPluginConfig() {
        return platformConfig;
    }
}
