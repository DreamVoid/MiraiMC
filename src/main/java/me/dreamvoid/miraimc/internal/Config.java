package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.bukkit.PluginEventHandler;
import me.dreamvoid.miraimc.bukkit.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Logger;

public class Config {
    public static YamlConfiguration config;
    public static File PluginDir;
    private static BukkitPlugin plugin;
    private static Logger logger;

    public Config(BukkitPlugin plugin){
        Config.plugin = plugin;
        logger = Utils.Logger;
    }

    public void loadConfig() {
        PluginDir = plugin.getDataFolder();
        File configure = new File(PluginDir, "config.yml");
        if(!(configure.exists())){ plugin.saveDefaultConfig(); }

        // 加载配置文件
        config = YamlConfiguration.loadConfiguration(new File(PluginDir, "config.yml"));

        // bStats统计
        if(config.getBoolean("general.allow-bStats", true)) {
            int pluginId = 11534;
            new Metrics(plugin, pluginId);
        }

        // 安全警告
        if(!(config.getBoolean("general.disable-safe-warning-message",false))){
            logger.warning("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            logger.warning("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        if(config.getBoolean("bot.log-events",true)){
            Bukkit.getPluginManager().registerEvents(new PluginEventHandler(), plugin);
        }
    }

    public static void reloadConfig() {
        plugin.reloadConfig();
    }
}
