package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.BotEvent;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class BukkitPlugin extends JavaPlugin {

    private BotEvent BotEvent;

    @Override // 加载插件
    public void onLoad() {
        Config config = new Config(this);
        this.BotEvent =new BotEvent(this);
    }

    @Override // 启用插件
    public void onEnable() {
        Config.LoadConfig();

        Bukkit.getLogger().info("Starting bot event listener.");
        BotEvent.startListenEvent();

        Objects.requireNonNull(Bukkit.getPluginCommand("mirai")).setExecutor(new CommandHandler(this));
        Objects.requireNonNull(Bukkit.getPluginCommand("miraimc")).setExecutor(new CommandHandler(this));
    }

    @Override // 禁用插件
    public void onDisable() {
        Bukkit.getLogger().info("Stopping bot event listener.");
        BotEvent.stopListenEvent();
    }


}
