package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.*;
import me.dreamvoid.miraimc.bungee.utils.Metrics;
import me.dreamvoid.miraimc.bungee.utils.SpecialUtils;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BungeePlugin extends Plugin implements Platform {
    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;
    private final MiraiMCPlugin lifeCycle;
    private final MiraiMCConfig platformConfig;

    public BungeePlugin(){
        lifeCycle = new MiraiMCPlugin(this);
        lifeCycle.startUp(getLogger());
        platformConfig = new BungeeConfig(this);
    }

    @Override
    public void onLoad() {
        try {
            lifeCycle.preLoad();

            MiraiAutoLogin = new MiraiAutoLogin(this);
            MiraiEvent = new MiraiEvent();
        } catch (Exception e) {
            getLogger().warning("An error occurred while loading plugin.");
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        lifeCycle.postLoad();

        // 注册命令
        getLogger().info("Registering commands.");
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command("mirai","miraimc.command.mirai") {
            @Override
            public void execute(CommandSender sender, String[] strings) {
                new MiraiCommand().onCommand(SpecialUtils.getSender(sender), strings);
            }
        });
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command("miraimc","miraimc.command.miraimc") {
            @Override
            public void execute(CommandSender sender, String[] strings) {
                new MiraiMcCommand().onCommand(SpecialUtils.getSender(sender), strings);
            }
        });
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command("miraiverify","miraimc.command.miraiverify") {
            @Override
            public void execute(CommandSender sender, String[] strings) {
                new MiraiVerifyCommand().onCommand(SpecialUtils.getSender(sender), strings);
            }
        });

        // 监听事件
        if(MiraiMCConfig.General.LogEvents){
            getLogger().info("Registering events.");
            getProxy().getPluginManager().registerListener(this, new Events());
        }

        // bStats统计
        if(MiraiMCConfig.General.AllowBStats && !getDescription().getVersion().contains("dev")) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = 12154;
            new Metrics(this, pluginId);
        }

        // HTTP API
        if(MiraiMCConfig.General.EnableHttpApi){
            getLogger().info("Initializing HttpAPI async task.");
            getProxy().getScheduler().schedule(this, new MiraiHttpAPIResolver(this), 0, MiraiMCConfig.HttpApi.MessageFetch.Interval * 20, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void onDisable() {
        lifeCycle.unload();
    }

    @Override
    public String getPlayerName(UUID uuid) {
        return getProxy().getPlayer(uuid).getName();
    }

    @Override
    public UUID getPlayerUUID(String name) {
        return getProxy().getPlayer(name).getUniqueId();
    }

    @Override
    public void runTaskAsync(Runnable task) {
        getProxy().getScheduler().runAsync(this,task);
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
        return Collections.singletonList(getDescription().getAuthor());
    }

    @Override
    public Logger getPluginLogger() {
        return getLogger();
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

    @Override
    public void runTaskLaterAsync(Runnable task, long delay) {
        getProxy().getScheduler().schedule(this,task, delay * 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public int runTaskTimerAsync(Runnable task, long period) {
        return getProxy().getScheduler().schedule(this, task,0, period * 50,TimeUnit.MILLISECONDS).getId();
    }

    @Override
    public void cancelTask(int taskId) {
        getProxy().getScheduler().cancel(taskId);
    }
}
