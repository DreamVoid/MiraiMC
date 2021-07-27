package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.bungee.BungeePlugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Config {
    private static Config Instance;

    private Configuration bungeeConfig;
    private static BukkitPlugin BukkitPlugin = null;
    private BungeePlugin BungeePlugin = null;

    public static File PluginDir;

    public static boolean Gen_AllowBstats;
    public static boolean Gen_DisableSafeWarningMessage;
    public static String Gen_MiraiWorkingDir;
    
    public static boolean Bot_DisableNetworkLogs;
    public static boolean Bot_DisableBotLogs;
    public static boolean Bot_UseBukkitLogger_BotLogs;
    public static boolean Bot_UseBukkitLogger_NetworkLogs;
    public static boolean Bot_LogEvents;
    public static boolean Bot_ContactCache_EnableFriendListCache;
    public static boolean Bot_ContactCache_EnableGroupMemberListCache;
    public static long Bot_ContactCache_SaveIntervalMillis;

    public static String DB_Type;

    public Config(BukkitPlugin plugin){
        BukkitPlugin = plugin;
        PluginDir = plugin.getDataFolder();
        Instance = this;
    }

    public Config(BungeePlugin bungee) {
        BungeePlugin = bungee;
        PluginDir = bungee.getDataFolder();
        Instance = this;
    }

    public void loadConfig() {
        BukkitPlugin.saveDefaultConfig();

        Gen_AllowBstats = BukkitPlugin.getConfig().getBoolean("general.allow-bStats",true);
        Gen_DisableSafeWarningMessage = BukkitPlugin.getConfig().getBoolean("general.disable-safe-warning-message",false);
        Gen_MiraiWorkingDir = BukkitPlugin.getConfig().getString("general.mirai-working-dir","default");
        
        Bot_DisableNetworkLogs = BukkitPlugin.getConfig().getBoolean("bot.disable-network-logs",false);
        Bot_DisableBotLogs = BukkitPlugin.getConfig().getBoolean("bot.disable-bot-logs",false);
        Bot_UseBukkitLogger_BotLogs = BukkitPlugin.getConfig().getBoolean("bot.use-bukkit-logger.bot-logs",true);
        Bot_UseBukkitLogger_NetworkLogs = BukkitPlugin.getConfig().getBoolean("bot.use-bukkit-logger.network-logs",true);
        Bot_LogEvents = BukkitPlugin.getConfig().getBoolean("bot.log-events",true);
        Bot_ContactCache_EnableFriendListCache = BukkitPlugin.getConfig().getBoolean("bot.contact-cache.enable-friend-list-cache",false);
        Bot_ContactCache_EnableGroupMemberListCache = BukkitPlugin.getConfig().getBoolean("bot.contact-cache.enable-group-member-list-cache",false);
        Bot_ContactCache_SaveIntervalMillis = BukkitPlugin.getConfig().getLong("bot.contact-cache.save-interval-millis",60000);

        DB_Type = BukkitPlugin.getConfig().getString("database.type","sqlite").toLowerCase();
    }
    public void loadConfigBungee() {
        try {
            if (!BungeePlugin.getDataFolder().exists()) {
                if(!BungeePlugin.getDataFolder().mkdir()) throw new IOException();
            }
            File file = new File(BungeePlugin.getDataFolder(), "config.yml");
            if (!file.exists()) {
                try (InputStream in = BungeePlugin.getResourceAsStream("config.yml")) {
                    Files.copy(in, file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bungeeConfig = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(new File(BungeePlugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gen_AllowBstats = bungeeConfig.getBoolean("general.allow-bStats",true);
        Gen_DisableSafeWarningMessage = bungeeConfig.getBoolean("general.disable-safe-warning-message",false);
        Gen_MiraiWorkingDir = bungeeConfig.getString("general.mirai-working-dir","default");

        Bot_DisableNetworkLogs = bungeeConfig.getBoolean("bot.disable-network-logs",false);
        Bot_DisableBotLogs = bungeeConfig.getBoolean("bot.disable-bot-logs",false);
        Bot_UseBukkitLogger_BotLogs = bungeeConfig.getBoolean("bot.use-bukkit-logger.bot-logs",true);
        Bot_UseBukkitLogger_NetworkLogs = bungeeConfig.getBoolean("bot.use-bukkit-logger.network-logs",true);
        Bot_LogEvents = bungeeConfig.getBoolean("bot.log-events",true);
        Bot_ContactCache_EnableFriendListCache = bungeeConfig.getBoolean("bot.contact-cache.enable-friend-list-cache",false);
        Bot_ContactCache_EnableGroupMemberListCache = bungeeConfig.getBoolean("bot.contact-cache.enable-group-member-list-cache",false);
        Bot_ContactCache_SaveIntervalMillis = bungeeConfig.getLong("bot.contact-cache.save-interval-millis",60000);

    }

    public static void reloadConfig() {
        Instance.loadConfig();
    }
}
