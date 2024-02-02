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
        General.AutoOpenQRCodeFile = config.getBoolean("general.auto-open-qrcode-file",false);
        General.LogEvents = config.getBoolean("general.log-events",true);

        Bot.DisableNetworkLogs = config.getBoolean("bot.disable-network-logs",false);
        Bot.DisableBotLogs = config.getBoolean("bot.disable-bot-logs",false);
        Bot.UseMinecraftLogger.BotLogs = config.getBoolean("bot.use-minecraft-logger.bot-logs",true);
        Bot.UseMinecraftLogger.NetworkLogs = config.getBoolean("bot.use-minecraft-logger.network-logs",true);
        Bot.ContactCache.EnableFriendListCache = config.getBoolean("bot.contact-cache.enable-friend-list-cache",false);
        Bot.ContactCache.EnableGroupMemberListCache = config.getBoolean("bot.contact-cache.enable-group-member-list-cache",false);
        Bot.ContactCache.SaveIntervalMillis = config.getLong("bot.contact-cache.save-interval-millis",60000);
        Bot.RegisterEncryptService = config.getBoolean("bot.register-encrypt-service",false);
        Bot.UpdateProtocolVersion = config.getBoolean("bot.update-protocol-version",false);

        Database.Type = config.getString("database.type","sqlite").toLowerCase();
        Database.Drivers.SQLite.Path = config.getString("database.settings.sqlite.path", "%plugin_folder%/database.db");
        Database.Drivers.MySQL.Address = config.getString("database.settings.mysql.address","localhost");
        Database.Drivers.MySQL.Username = config.getString("database.settings.mysql.username", "miraimc");
        Database.Drivers.MySQL.Password = config.getString("database.settings.mysql.password", "miraimc");
        Database.Drivers.MySQL.Database = config.getString("database.settings.mysql.database", "miraimc");
        Database.Drivers.MySQL.Parameters = config.getString("database.settings.mysql.parameters", "?useSSL=false");
        Database.Settings.Prefix = config.getString("database.settings.prefix", "miraimc_");
        Database.Settings.Pool.ConnectionTimeout = config.getInt("database.pool.connectionTimeout",30000);
        Database.Settings.Pool.IdleTimeout = config.getInt("database.pool.connectionTimeout",600000);
        Database.Settings.Pool.MaxLifetime = config.getInt("database.pool.maxLifetime",1800000);
        Database.Settings.Pool.MaximumPoolSize = config.getInt("database.pool.maximumPoolSize",15);
        Database.Settings.Pool.KeepaliveTime = config.getInt("database.pool.keepaliveTime",0);
        Database.Settings.Pool.MinimumIdle = config.getInt("database.pool.minimumIdle",0);

        HttpApi.Url = config.getString("http-api.url", "http://localhost:8080");
        HttpApi.MessageFetch.Interval = config.getInt("http-api.message-fetch.interval", 10);
        HttpApi.MessageFetch.Count = config.getInt("http-api.message-fetch.count", 10);
    }
}
