package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.utils.Metrics;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.Bot;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;

public class BukkitPlugin extends JavaPlugin {

    private MiraiEvent MiraiEvent;
    private MiraiEventOld MiraiEventOld;
    private MiraiBot MiraiBot;
    private Config PluginConfig;
    public MiraiAutoLogin MiraiAutoLogin;

    @Override // 加载插件
    public void onLoad() {
        new Utils(this);
        this.PluginConfig = new Config(this);
        this.MiraiEvent = new MiraiEvent();
        this.MiraiEventOld = new MiraiEventOld();
        this.MiraiBot = new MiraiBot();
        this.MiraiAutoLogin = new MiraiAutoLogin(this);
    }

    @Override // 启用插件
    public void onEnable() {
        PluginConfig.loadConfig();

        getLogger().info("Mirai working dir: " + Config.Gen_MiraiWorkingDir);

        getLogger().info("Starting bot event listener.");
        MiraiEvent.startListenEvent();
        MiraiEventOld.startListenEvent();

        getLogger().info("Registering commands.");
        for (String s : Arrays.asList("mirai", "miraimc", "miraiverify")) { Objects.requireNonNull(getCommand(s)).setExecutor(new CommandProcessor(this)); }

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        // bStats统计
        if(Config.Gen_AllowBstats) {
            int pluginId = 11534;
            new Metrics(this, pluginId);
        }

        // 安全警告
        if(!(Config.Gen_DisableSafeWarningMessage)){
            getLogger().warning("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warning("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        if(Config.Bot_LogEvents){
            Bukkit.getPluginManager().registerEvents(new EventsProcessor(), this);
        }

        getLogger().info("All tasks done. Welcome to use MiraiMC!");
    }

    @Override // 禁用插件
    public void onDisable() {
        getLogger().info("Stopping bot event listener.");
        MiraiEvent.stopListenEvent();

        getLogger().info("Closing all bots");
        for (long bots : MiraiBot.getOnlineBots()){ MiraiBot.doBotLogout(Bot.getInstance(bots)); }

        getLogger().info("All tasks done. Thanks for use MiraiMC!");
    }


}
