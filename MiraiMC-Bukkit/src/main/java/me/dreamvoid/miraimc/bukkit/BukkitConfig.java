package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.MiraiMCConfig;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class BukkitConfig extends MiraiMCConfig {
    private final BukkitPlugin plugin;

    public BukkitConfig(BukkitPlugin plugin){
        this.plugin = plugin;
        PluginDir = plugin.getDataFolder();
        INSTANCE = this;
    }

    @Override
    public void loadConfig() throws IOException {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        try(InputStream resource = plugin.getResource("config.yml")){
            assert resource != null;
            YamlConfiguration y = new YamlConfiguration();
            y.options().pathSeparator('\\');
            y.load(new InputStreamReader(resource, StandardCharsets.UTF_8));
            plugin.getConfig().setDefaults(y);
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveConfig();
        } catch (InvalidConfigurationException e) {
            throw new IOException(e);
        }

        General.AllowBStats = plugin.getConfig().getBoolean("general.allow-bStats",true);
        General.CheckUpdate = plugin.getConfig().getBoolean("general.check-update",true);
        General.DisableSafeWarningMessage = plugin.getConfig().getBoolean("general.disable-safe-warning-message",false);
        General.MiraiWorkingDir = plugin.getConfig().getString("general.mirai-working-dir","default");
        General.MiraiCoreVersion = plugin.getConfig().getString("general.mirai-core-version","latest");
        General.MavenRepoUrl = plugin.getConfig().getString("general.maven-repo-url","https://repo1.maven.org/maven2");
        General.EnableHttpApi = plugin.getConfig().getBoolean("general.enable-http-api",false);
        General.AutoOpenQRCodeFile = plugin.getConfig().getBoolean("general.auto-open-qrcode-file",false);
        General.LogEvents = plugin.getConfig().getBoolean("general.log-events",true);

        Bot.DisableNetworkLogs = plugin.getConfig().getBoolean("bot.disable-network-logs",false);
        Bot.DisableBotLogs = plugin.getConfig().getBoolean("bot.disable-bot-logs",false);
        Bot.UseMinecraftLogger.BotLogs = plugin.getConfig().getBoolean("bot.use-minecraft-logger.bot-logs",true);
        Bot.UseMinecraftLogger.NetworkLogs = plugin.getConfig().getBoolean("bot.use-minecraft-logger.network-logs",true);
        Bot.ContactCache.EnableFriendListCache = plugin.getConfig().getBoolean("bot.contact-cache.enable-friend-list-cache",false);
        Bot.ContactCache.EnableGroupMemberListCache = plugin.getConfig().getBoolean("bot.contact-cache.enable-group-member-list-cache",false);
        Bot.ContactCache.SaveIntervalMillis = plugin.getConfig().getLong("bot.contact-cache.save-interval-millis",60000);
        Bot.RegisterEncryptService = plugin.getConfig().getBoolean("bot.register-encrypt-service",false);
        Bot.UpdateProtocolVersion = plugin.getConfig().getBoolean("bot.update-protocol-version",false);

        Database.Type = plugin.getConfig().getString("database.type","sqlite").toLowerCase();
        Database.Drivers.SQLite.Path = plugin.getConfig().getString("database.settings.sqlite.path", "%plugin_folder%/database.db");
        Database.Drivers.MySQL.Address = plugin.getConfig().getString("database.settings.mysql.address","localhost");
        Database.Drivers.MySQL.Username = plugin.getConfig().getString("database.settings.mysql.username", "miraimc");
        Database.Drivers.MySQL.Password = plugin.getConfig().getString("database.settings.mysql.password", "miraimc");
        Database.Drivers.MySQL.Database = plugin.getConfig().getString("database.settings.mysql.database", "miraimc");
        Database.Drivers.MySQL.Parameters = plugin.getConfig().getString("database.settings.mysql.parameters", "?useSSL=false");
        Database.Settings.Prefix = plugin.getConfig().getString("database.settings.prefix", "miraimc_");
        Database.Settings.Pool.ConnectionTimeout = plugin.getConfig().getInt("database.pool.connectionTimeout",30000);
        Database.Settings.Pool.IdleTimeout = plugin.getConfig().getInt("database.pool.connectionTimeout",600000);
        Database.Settings.Pool.MaxLifetime = plugin.getConfig().getInt("database.pool.maxLifetime",1800000);
        Database.Settings.Pool.MaximumPoolSize = plugin.getConfig().getInt("database.pool.maximumPoolSize",15);
        Database.Settings.Pool.KeepaliveTime = plugin.getConfig().getInt("database.pool.keepaliveTime",0);
        Database.Settings.Pool.MinimumIdle = plugin.getConfig().getInt("database.pool.minimumIdle",0);

        HttpApi.Url = plugin.getConfig().getString("http-api.url", "http://localhost:8080");
        HttpApi.MessageFetch.Interval = plugin.getConfig().getInt("http-api.message-fetch.interval", 10);
        HttpApi.MessageFetch.Count = plugin.getConfig().getInt("http-api.message-fetch.count", 10);
    }
}
