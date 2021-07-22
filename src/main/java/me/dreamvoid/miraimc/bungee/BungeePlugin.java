package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bungee.commands.MiraiCommand;
import me.dreamvoid.miraimc.bungee.utils.Metrics;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlugin extends Plugin {
    private MiraiEvent MiraiEvent;
    private MiraiBot MiraiBot;
    private Config PluginConfig;
    private me.dreamvoid.miraimc.bukkit.MiraiAutoLogin MiraiAutoLogin;

    @Override
    public void onLoad() {
        new Utils(this);
        this.PluginConfig = new Config(this);
        this.MiraiEvent = new MiraiEvent();
        this.MiraiBot = new MiraiBot();
        //this.MiraiAutoLogin = new MiraiAutoLogin(this); // TO DO: 这里载入有问题，要修复
    }

    @Override
    public void onEnable() {
        PluginConfig.loadConfigBungee();

        getLogger().info("Mirai working dir: " + Config.Gen_MiraiWorkingDir);

        getLogger().info("Starting bot event listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Registering commands.");
        ProxyServer.getInstance().getPluginManager().registerCommand(this,new MiraiCommand(this,"mirai"));

        //getLogger().info("Loading auto-login file.");
        //MiraiAutoLogin.loadFile();
        //MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        // bStats统计
        //if(Config.Gen_AllowBstats) {
        if(false) {
            int pluginId = 11534;
            new Metrics(this, pluginId);
        }

        // 安全警告
        if(!(Config.Gen_DisableSafeWarningMessage)){
            getLogger().warning("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warning("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        getLogger().info("All tasks done. Welcome to use MiraiMC!");
    }

    @Override
    public void onDisable() {
    }
}
