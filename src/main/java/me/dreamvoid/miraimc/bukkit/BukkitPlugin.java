package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.BotEvent;
import me.dreamvoid.miraimc.internal.Config;
import net.mamoe.mirai.Bot;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class BukkitPlugin extends JavaPlugin {

    private BotEvent BotEvent;
    private MiraiBot MiraiBot;

    @Override // 加载插件
    public void onLoad() {
        Config config = new Config(this);
        this.BotEvent = new BotEvent(this);
        this.MiraiBot = new MiraiBot(this.getLogger());
    }

    @Override // 启用插件
    public void onEnable() {
        Config.LoadConfig();

        getLogger().info("Starting bot event listener.");
        BotEvent.startListenEvent();

        getLogger().info("Registering commands.");
        Objects.requireNonNull(Bukkit.getPluginCommand("mirai")).setExecutor(new CommandHandler(this));
        Objects.requireNonNull(Bukkit.getPluginCommand("miraimc")).setExecutor(new CommandHandler(this));

        getLogger().info("All tasks done. Welcome to use MiraiMC!");
    }

    @Override // 禁用插件
    public void onDisable() {
        Bukkit.getLogger().info("Stopping bot event listener.");
        BotEvent.stopListenEvent();

        Bukkit.getLogger().info("Closing all bots");
        List<Long> BotList = MiraiBot.getOnlineBots();
        for (Long bots : BotList){ MiraiBot.doBotLogout(Bot.getInstance(bots)); }

        getLogger().info("All tasks done. Thanks for use MiraiMC!");


    }


}
