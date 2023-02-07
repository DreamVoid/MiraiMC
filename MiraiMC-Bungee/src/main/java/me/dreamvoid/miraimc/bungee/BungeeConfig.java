package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.MiraiMCConfig;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BungeeConfig extends MiraiMCConfig {
    private final BungeePlugin plugin;

    public BungeeConfig(BungeePlugin plugin) {
        this.plugin = plugin;
        PluginDir = plugin.getDataFolder();
        INSTANCE = this;
    }

    @Override
    public void loadConfig() throws IOException {
        if (!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdirs()) throw new RuntimeException("Failed to create folder " + plugin.getDataFolder().getPath());
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            }
        }
        Configuration config = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));

        General.AllowBStats = config.getBoolean("general.allow-bStats",true);
        General.CheckUpdate = config.getBoolean("general.check-update",true);
        General.DisableSafeWarningMessage = config.getBoolean("general.disable-safe-warning-message",false);
        General.MiraiWorkingDir = config.getString("general.mirai-working-dir","default");
        General.MiraiCoreVersion = config.getString("general.mirai-core-version","latest");
        General.MavenRepoUrl = config.getString("general.maven-repo-url","https://repo1.maven.org/maven2");
        General.EnableHttpApi = config.getBoolean("general.enable-http-api",false);
        General.LegacyEventSupport = config.getBoolean("general.legacy-event-support",false);

        Bot.DisableNetworkLogs = config.getBoolean("bot.disable-network-logs",false);
        Bot.DisableBotLogs = config.getBoolean("bot.disable-bot-logs",false);
        Bot.UseMinecraftLogger.BotLogs = config.getBoolean("bot.use-minecraft-logger.bot-logs",true);
        Bot.UseMinecraftLogger.NetworkLogs = config.getBoolean("bot.use-minecraft-logger.network-logs",true);
        Bot.LogEvents = config.getBoolean("bot.log-events",true);
        Bot.ContactCache.EnableFriendListCache = config.getBoolean("bot.contact-cache.enable-friend-list-cache",false);
        Bot.ContactCache.EnableGroupMemberListCache = config.getBoolean("bot.contact-cache.enable-group-member-list-cache",false);
        Bot.ContactCache.SaveIntervalMillis = config.getLong("bot.contact-cache.save-interval-millis",60000);

        Database.Type = config.getString("database.type","sqlite").toLowerCase();
        Database.MySQL.Address = config.getString("database.mysql.address", "localhost");
        Database.MySQL.Username = config.getString("database.mysql.username", "miraimc");
        Database.MySQL.Password = config.getString("database.mysql.password", "miraimc");
        Database.MySQL.Database = config.getString("database.mysql.database", "miraimc");
        Database.MySQL.Poll.ConnectionTimeout = config.getInt("database.mysql.pool.connectionTimeout",30000);
        Database.MySQL.Poll.IdleTimeout = config.getInt("database.mysql.pool.connectionTimeout",600000);
        Database.MySQL.Poll.MaxLifetime = config.getInt("database.mysql.pool.maxLifetime",1800000);
        Database.MySQL.Poll.MaximumPoolSize = config.getInt("database.mysql.pool.maximumPoolSize",15);
        Database.MySQL.Poll.KeepaliveTime = config.getInt("database.mysql.pool.keepaliveTime",0);
        Database.MySQL.Poll.MinimumIdle = config.getInt("database.mysql.pool.minimumIdle",0);
    }
}
