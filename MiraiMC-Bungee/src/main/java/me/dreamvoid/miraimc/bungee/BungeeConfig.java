package me.dreamvoid.miraimc.bungee;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static me.dreamvoid.miraimc.internal.Config.*;

public class BungeeConfig {
    private Configuration bungeeConfig;
    private final BungeePlugin BungeePlugin;
    private static BungeeConfig Instance;

    public BungeeConfig(BungeePlugin bungee) {
        BungeePlugin = bungee;
        PluginDir = bungee.getDataFolder();
        Instance = this;
    }

    public void loadConfig() {
        try {
            if (!BungeePlugin.getDataFolder().exists() && !BungeePlugin.getDataFolder().mkdirs()) throw new RuntimeException("Failed to create folder " + BungeePlugin.getDataFolder().getPath());
            File file = new File(BungeePlugin.getDataFolder(), "config.yml");
            if (!file.exists()) {
                try (InputStream in = BungeePlugin.getResourceAsStream("config.yml")) {
                    Files.copy(in, file.toPath());
                }
            }
            bungeeConfig = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(new File(BungeePlugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        General.AllowBStats = bungeeConfig.getBoolean("general.allow-bStats",true);
        General.CheckUpdate = bungeeConfig.getBoolean("general.check-update",true);
        General.DisableSafeWarningMessage = bungeeConfig.getBoolean("general.disable-safe-warning-message",false);
        General.MiraiWorkingDir = bungeeConfig.getString("general.mirai-working-dir","default");
        General.MiraiCoreVersion = bungeeConfig.getString("general.mirai-core-version","latest");
        General.MavenRepoUrl = bungeeConfig.getString("general.maven-repo-url","https://repo1.maven.org/maven2");
        General.EnableHttpApi = bungeeConfig.getBoolean("general.enable-http-api",false);
        General.LegacyEventSupport = bungeeConfig.getBoolean("general.legacy-event-support",false);

        Bot.DisableNetworkLogs = bungeeConfig.getBoolean("bot.disable-network-logs",false);
        Bot.DisableBotLogs = bungeeConfig.getBoolean("bot.disable-bot-logs",false);
        Bot.UseMinecraftLogger.BotLogs = bungeeConfig.getBoolean("bot.use-minecraft-logger.bot-logs",true);
        Bot.UseMinecraftLogger.NetworkLogs = bungeeConfig.getBoolean("bot.use-minecraft-logger.network-logs",true);
        Bot.LogEvents = bungeeConfig.getBoolean("bot.log-events",true);
        Bot.ContactCache.EnableFriendListCache = bungeeConfig.getBoolean("bot.contact-cache.enable-friend-list-cache",false);
        Bot.ContactCache.EnableGroupMemberListCache = bungeeConfig.getBoolean("bot.contact-cache.enable-group-member-list-cache",false);
        Bot.ContactCache.SaveIntervalMillis = bungeeConfig.getLong("bot.contact-cache.save-interval-millis",60000);

        Database.Type = bungeeConfig.getString("database.type","sqlite").toLowerCase();
        Database.MySQL.Address = bungeeConfig.getString("database.mysql.address", "localhost");
        Database.MySQL.Username = bungeeConfig.getString("database.mysql.username", "miraimc");
        Database.MySQL.Password = bungeeConfig.getString("database.mysql.password", "miraimc");
        Database.MySQL.Database = bungeeConfig.getString("database.mysql.database", "miraimc");
        Database.MySQL.Poll.ConnectionTimeout = bungeeConfig.getInt("database.mysql.pool.connectionTimeout",30000);
        Database.MySQL.Poll.IdleTimeout = bungeeConfig.getInt("database.mysql.pool.connectionTimeout",600000);
        Database.MySQL.Poll.MaxLifetime = bungeeConfig.getInt("database.mysql.pool.maxLifetime",1800000);
        Database.MySQL.Poll.MaximumPoolSize = bungeeConfig.getInt("database.mysql.pool.maximumPoolSize",15);
        Database.MySQL.Poll.KeepaliveTime = bungeeConfig.getInt("database.mysql.pool.keepaliveTime",0);
        Database.MySQL.Poll.MinimumIdle = bungeeConfig.getInt("database.mysql.pool.minimumIdle",0);
    }

    public static void reloadConfig() {
        Instance.loadConfig();
    }
}
