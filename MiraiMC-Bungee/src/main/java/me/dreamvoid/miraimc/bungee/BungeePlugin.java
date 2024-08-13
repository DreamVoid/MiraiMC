package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.IMiraiAutoLogin;
import me.dreamvoid.miraimc.IMiraiEvent;
import me.dreamvoid.miraimc.LifeCycle;
import me.dreamvoid.miraimc.Platform;
import me.dreamvoid.miraimc.bungee.utils.Metrics;
import me.dreamvoid.miraimc.bungee.utils.SpecialUtils;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.config.PluginConfig;
import me.dreamvoid.miraimc.internal.loader.LibraryLoader;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BungeePlugin extends Plugin implements Platform {
    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;
    private final LifeCycle lifeCycle;
    private final BungeeConfig platformConfig;
    private final LibraryLoader loader;

    public BungeePlugin(){
        lifeCycle = new LifeCycle(this);
        lifeCycle.startUp(getLogger());
        platformConfig = new BungeeConfig(this);
        loader = new LibraryLoader((URLClassLoader) this.getClass().getClassLoader());
    }

    @Override
    public void onLoad() {
        try {
            lifeCycle.preLoad();

            MiraiAutoLogin = new MiraiAutoLogin(this);
            MiraiEvent = new MiraiEvent();
        } catch (Exception e) {
            Utils.resolveException(e, getLogger(), "加载 MiraiMC 阶段 1 时出现异常！");
        }
    }

    @Override
    public void onEnable() {
        try {
            lifeCycle.postLoad();

            // 注册命令
            getLogger().info("Registering commands.");
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command("mirai", "miraimc.command.mirai") {
                @Override
                public void execute(CommandSender sender, String[] strings) {
                    new MiraiCommand().onCommand(SpecialUtils.getSender(sender), strings);
                }
            });
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command("miraimc", "miraimc.command.miraimc") {
                @Override
                public void execute(CommandSender sender, String[] strings) {
                    new MiraiMcCommand().onCommand(SpecialUtils.getSender(sender), strings);
                }
            });
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command("miraiverify", "miraimc.command.miraiverify") {
                @Override
                public void execute(CommandSender sender, String[] strings) {
                    new MiraiVerifyCommand().onCommand(SpecialUtils.getSender(sender), strings);
                }
            });

            // 监听事件
            if (PluginConfig.General.LogEvents) {
                getLogger().info("Registering events.");
                getProxy().getPluginManager().registerListener(this, new Events());
            }

            // bStats统计
            if (PluginConfig.General.AllowBStats && !getDescription().getVersion().contains("dev")) {
                getLogger().info("Initializing bStats metrics.");
                int pluginId = 12154;
                new Metrics(this, pluginId);
            }
        } catch (Exception e){
            Utils.resolveException(e, getLogger(), "加载 MiraiMC 阶段 2 时出现异常！");
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
    public LibraryLoader getLibraryLoader() {
        return loader;
    }

    @Override
    public String getType() {
        return "BungeeCord";
    }

    @Override
    public void runTaskLaterAsync(Runnable task, long delay) {
        getProxy().getScheduler().schedule(this,task, delay * 50, TimeUnit.MILLISECONDS);
    }

}
