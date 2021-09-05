package me.dreamvoid.miraimc.bukkit;

import static me.dreamvoid.miraimc.internal.Config.*;

public class BukkitConfig {
    private static BukkitPlugin BukkitPlugin;
    private static BukkitConfig Instance;

    public BukkitConfig(BukkitPlugin plugin){
        BukkitPlugin = plugin;
        PluginDir = plugin.getDataFolder();
        Instance = this;
    }

    public void loadConfig() {
        BukkitPlugin.saveDefaultConfig();

        Gen_AllowBstats = BukkitPlugin.getConfig().getBoolean("general.allow-bStats",true);
        Gen_DisableSafeWarningMessage = BukkitPlugin.getConfig().getBoolean("general.disable-safe-warning-message",false);
        Gen_MiraiWorkingDir = BukkitPlugin.getConfig().getString("general.mirai-working-dir","default");
        Gen_AddProperties_MiraiNoDesktop = BukkitPlugin.getConfig().getBoolean("general.add-properties.mirai.no-desktop",true);
        Gen_AddProperties_MiraiSliderCaptchaSupported = BukkitPlugin.getConfig().getBoolean("general.add-properties.mirai.slider.captcha.supported",true);

        Bot_DisableNetworkLogs = BukkitPlugin.getConfig().getBoolean("bot.disable-network-logs",false);
        Bot_DisableBotLogs = BukkitPlugin.getConfig().getBoolean("bot.disable-bot-logs",false);
        Bot_UseBukkitLogger_BotLogs = BukkitPlugin.getConfig().getBoolean("bot.use-bukkit-logger.bot-logs",true);
        Bot_UseBukkitLogger_NetworkLogs = BukkitPlugin.getConfig().getBoolean("bot.use-bukkit-logger.network-logs",true);
        Bot_LogEvents = BukkitPlugin.getConfig().getBoolean("bot.log-events",true);
        Bot_ContactCache_EnableFriendListCache = BukkitPlugin.getConfig().getBoolean("bot.contact-cache.enable-friend-list-cache",false);
        Bot_ContactCache_EnableGroupMemberListCache = BukkitPlugin.getConfig().getBoolean("bot.contact-cache.enable-group-member-list-cache",false);
        Bot_ContactCache_SaveIntervalMillis = BukkitPlugin.getConfig().getLong("bot.contact-cache.save-interval-millis",60000);

        DB_Type = BukkitPlugin.getConfig().getString("database.type","sqlite").toLowerCase();
        DB_MySQL_Address = BukkitPlugin.getConfig().getString("database.mysql.address","localhost");
        DB_MySQL_Username = BukkitPlugin.getConfig().getString("database.mysql.username", "miraimc");
        DB_MySQL_Password = BukkitPlugin.getConfig().getString("database.mysql.password", "miraimc");
        DB_MySQL_Database = BukkitPlugin.getConfig().getString("database.mysql.database", "miraimc");
        DB_MySQL_Poll_ConnectionTimeout = BukkitPlugin.getConfig().getInt("database.mysql.pool.connectionTimeout",30000);
        DB_MySQL_Poll_IdleTimeout = BukkitPlugin.getConfig().getInt("database.mysql.pool.connectionTimeout",600000);
        DB_MySQL_Poll_MaxLifetime = BukkitPlugin.getConfig().getInt("database.mysql.pool.maxLifetime",1800000);
        DB_MySQL_Poll_MaximumPoolSize = BukkitPlugin.getConfig().getInt("database.mysql.pool.maximumPoolSize",15);
        DB_MySQL_Poll_KeepaliveTime = BukkitPlugin.getConfig().getInt("database.mysql.pool.keepaliveTime",0);
        DB_MySQL_Poll_MinimumIdle = BukkitPlugin.getConfig().getInt("database.mysql.pool.minimumIdle",0);
    }

    public static void reloadConfig() {
        Instance.loadConfig();
    }
}
