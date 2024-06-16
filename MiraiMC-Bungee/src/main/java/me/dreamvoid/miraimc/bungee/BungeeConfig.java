package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.internal.config.PluginConfig;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BungeeConfig extends PluginConfig {
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

        General.AllowBStats = config.getBoolean("general.allow-bStats", General.AllowBStats);
        General.CheckUpdate = config.getBoolean("general.check-update", General.CheckUpdate);
        General.DisableSafeWarningMessage = config.getBoolean("general.disable-safe-warning-message",General.DisableSafeWarningMessage);
        General.MiraiWorkingDir = config.getString("general.mirai-working-dir",General.MiraiWorkingDir);
        General.MiraiCoreVersion = config.getString("general.mirai-core-version",General.MiraiCoreVersion);
        General.MavenRepoUrl = config.getString("general.maven-repo-url",General.MavenRepoUrl);
        General.EnableHttpApi = config.getBoolean("general.enable-http-api",General.EnableHttpApi);
        General.AutoOpenQRCodeFile = config.getBoolean("general.auto-open-qrcode-file",General.AutoOpenQRCodeFile);
        General.LogEvents = config.getBoolean("general.log-events",General.LogEvents);

        Bot.DisableNetworkLogs = config.getBoolean("bot.disable-network-logs",Bot.DisableNetworkLogs);
        Bot.DisableBotLogs = config.getBoolean("bot.disable-bot-logs",Bot.DisableBotLogs);
        Bot.UseMinecraftLogger.BotLogs = config.getBoolean("bot.use-minecraft-logger.bot-logs",Bot.UseMinecraftLogger.BotLogs);
        Bot.UseMinecraftLogger.NetworkLogs = config.getBoolean("bot.use-minecraft-logger.network-logs",Bot.UseMinecraftLogger.NetworkLogs);
        Bot.ContactCache.EnableFriendListCache = config.getBoolean("bot.contact-cache.enable-friend-list-cache",Bot.ContactCache.EnableFriendListCache);
        Bot.ContactCache.EnableGroupMemberListCache = config.getBoolean("bot.contact-cache.enable-group-member-list-cache",Bot.ContactCache.EnableGroupMemberListCache);
        Bot.ContactCache.SaveIntervalMillis = config.getLong("bot.contact-cache.save-interval-millis",Bot.ContactCache.SaveIntervalMillis);
        Bot.RegisterEncryptService = config.getBoolean("bot.register-encrypt-service",Bot.RegisterEncryptService);
        Bot.UpdateProtocolVersion = config.getBoolean("bot.update-protocol-version",Bot.UpdateProtocolVersion);

        Database.Type = config.getString("database.type",Database.Type).toLowerCase();
        Database.Drivers.SQLite.Path = config.getString("database.settings.sqlite.path", Database.Drivers.SQLite.Path);
        Database.Drivers.MySQL.Address = config.getString("database.settings.mysql.address",Database.Drivers.MySQL.Address);
        Database.Drivers.MySQL.Username = config.getString("database.settings.mysql.username", Database.Drivers.MySQL.Username);
        Database.Drivers.MySQL.Password = config.getString("database.settings.mysql.password", Database.Drivers.MySQL.Password);
        Database.Drivers.MySQL.Database = config.getString("database.settings.mysql.database", Database.Drivers.MySQL.Database);
        Database.Drivers.MySQL.Parameters = config.getString("database.settings.mysql.parameters", Database.Drivers.MySQL.Parameters);
        Database.Settings.Prefix = config.getString("database.settings.prefix", Database.Settings.Prefix);
        Database.Settings.Pool.ConnectionTimeout = config.getInt("database.pool.connectionTimeout", Database.Settings.Pool.ConnectionTimeout);
        Database.Settings.Pool.IdleTimeout = config.getInt("database.pool.connectionTimeout", Database.Settings.Pool.IdleTimeout);
        Database.Settings.Pool.MaxLifetime = config.getInt("database.pool.maxLifetime", Database.Settings.Pool.MaxLifetime);
        Database.Settings.Pool.MaximumPoolSize = config.getInt("database.pool.maximumPoolSize", Database.Settings.Pool.MaximumPoolSize);
        Database.Settings.Pool.KeepaliveTime = config.getInt("database.pool.keepaliveTime", Database.Settings.Pool.KeepaliveTime);
        Database.Settings.Pool.MinimumIdle = config.getInt("database.pool.minimumIdle", Database.Settings.Pool.MinimumIdle);

        HttpApi.Url = config.getString("http-api.url", HttpApi.Url);
        HttpApi.MessageFetch.Interval = config.getInt("http-api.message-fetch.interval", HttpApi.MessageFetch.Interval);
        HttpApi.MessageFetch.Count = config.getInt("http-api.message-fetch.count", HttpApi.MessageFetch.Count);
    }
}
