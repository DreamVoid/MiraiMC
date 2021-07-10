package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiAutoLogin;
import me.dreamvoid.miraimc.internal.MiraiEvent;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.Bot;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;

public class BukkitPlugin extends JavaPlugin {

    private MiraiEvent MiraiEvent;
    private MiraiBot MiraiBot;
    private Config config;
    private java.util.logging.Logger Logger;
    public MiraiAutoLogin MiraiAutoLogin;

    @Override // 加载插件
    public void onLoad() {
        this.config = new Config(this);
        this.MiraiEvent = new MiraiEvent();
        this.MiraiBot = new MiraiBot();
        this.MiraiAutoLogin = new MiraiAutoLogin(this);
        this.Logger = Utils.getLogger();
    }

    @Override // 启用插件
    public void onEnable() {
        config.loadConfig();

        Logger.info("Mirai working dir: " + Config.config.getString("general.mirai-working-dir", "default"));

        Logger.info("Starting bot event listener.");
        MiraiEvent.startListenEvent();

        Logger.info("Registering commands.");
        for (String s : Arrays.asList("mirai", "miraimc")) { Objects.requireNonNull(getCommand(s)).setExecutor(new CommandHandler(this)); }

        Logger.info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        Logger.info("All tasks done. Welcome to use MiraiMC!");
    }

    @Override // 禁用插件
    public void onDisable() {
        Logger.info("Stopping bot event listener.");
        MiraiEvent.stopListenEvent();

        Logger.info("Closing all bots");
        for (long bots : MiraiBot.getOnlineBots()){ MiraiBot.doBotLogout(Bot.getInstance(bots)); }

        Logger.info("All tasks done. Thanks for use MiraiMC!");
    }


}
