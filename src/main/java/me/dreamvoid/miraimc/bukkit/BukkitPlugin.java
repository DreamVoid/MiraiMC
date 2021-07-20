package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiAutoLogin;
import me.dreamvoid.miraimc.internal.MiraiEvent;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.Bot;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;

public class BukkitPlugin extends JavaPlugin {

    private MiraiEvent MiraiEvent;
    private MiraiBot MiraiBot;
    private Config Config;
    public MiraiAutoLogin MiraiAutoLogin;

    @Override // 加载插件
    public void onLoad() {
        this.Config = new Config(this);
        this.MiraiEvent = new MiraiEvent();
        this.MiraiBot = new MiraiBot();
        this.MiraiAutoLogin = new MiraiAutoLogin(this);
    }

    @Override // 启用插件
    public void onEnable() {
        Config.loadConfig();

        getLogger().info("Mirai working dir: " + Config.config.getString("general.mirai-working-dir", "default"));

        getLogger().info("Starting bot event listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Registering commands.");
        for (String s : Arrays.asList("mirai", "miraimc")) { Objects.requireNonNull(getCommand(s)).setExecutor(new CommandHandler(this)); }

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

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
