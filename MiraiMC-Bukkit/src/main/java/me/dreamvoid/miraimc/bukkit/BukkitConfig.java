package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.MiraiMCConfig;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
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
        try{
            plugin.saveDefaultConfig();
            YamlConfiguration y = new YamlConfiguration();
            y.options().pathSeparator('\\');
            y.load(new InputStreamReader(plugin.getResource("config.yml"), StandardCharsets.UTF_8));
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
        Database.MySQL.Address = plugin.getConfig().getString("database.mysql.address","localhost");
        Database.MySQL.Username = plugin.getConfig().getString("database.mysql.username", "miraimc");
        Database.MySQL.Password = plugin.getConfig().getString("database.mysql.password", "miraimc");
        Database.MySQL.Database = plugin.getConfig().getString("database.mysql.database", "miraimc");
        Database.MySQL.Poll.ConnectionTimeout = plugin.getConfig().getInt("database.mysql.pool.connectionTimeout",30000);
        Database.MySQL.Poll.IdleTimeout = plugin.getConfig().getInt("database.mysql.pool.connectionTimeout",600000);
        Database.MySQL.Poll.MaxLifetime = plugin.getConfig().getInt("database.mysql.pool.maxLifetime",1800000);
        Database.MySQL.Poll.MaximumPoolSize = plugin.getConfig().getInt("database.mysql.pool.maximumPoolSize",15);
        Database.MySQL.Poll.KeepaliveTime = plugin.getConfig().getInt("database.mysql.pool.keepaliveTime",0);
        Database.MySQL.Poll.MinimumIdle = plugin.getConfig().getInt("database.mysql.pool.minimumIdle",0);

        HttpApi.Url = plugin.getConfig().getString("http-api.url", "http://localhost:8080");
        HttpApi.MessageFetch.Interval = plugin.getConfig().getInt("http-api.message-fetch.interval", 10);
        HttpApi.MessageFetch.Count = plugin.getConfig().getInt("http-api.message-fetch.count", 10);
    }
}
