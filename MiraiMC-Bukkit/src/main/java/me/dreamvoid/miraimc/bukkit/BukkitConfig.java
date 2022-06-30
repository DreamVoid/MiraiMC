package me.dreamvoid.miraimc.bukkit;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static me.dreamvoid.miraimc.internal.Config.*;

public class BukkitConfig {
    private final BukkitPlugin plugin;
    private static BukkitConfig Instance;

    public BukkitConfig(BukkitPlugin plugin){
        Instance = this;
        this.plugin = plugin;
        PluginDir = plugin.getDataFolder();
    }

    public void loadConfig() throws IOException, InvalidConfigurationException {
        plugin.saveDefaultConfig();
        YamlConfiguration y = new YamlConfiguration();
        y.options().pathSeparator('\\');
        y.load(new InputStreamReader(plugin.getResource("config.yml"), StandardCharsets.UTF_8));
        plugin.getConfig().setDefaults(y);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();

        Gen_AllowBStats = plugin.getConfig().getBoolean("general.allow-bStats",true);
        Gen_CheckUpdate = plugin.getConfig().getBoolean("general.check-update",true);
        Gen_DisableSafeWarningMessage = plugin.getConfig().getBoolean("general.disable-safe-warning-message",false);
        Gen_MiraiWorkingDir = plugin.getConfig().getString("general.mirai-working-dir","default");
        Gen_AddProperties_MiraiNoDesktop = plugin.getConfig().getBoolean("general.add-properties.mirai.no-desktop",true);
        Gen_AddProperties_MiraiSliderCaptchaSupported = plugin.getConfig().getBoolean("general.add-properties.mirai.slider.captcha.supported",true);
        Gen_MiraiCoreVersion = plugin.getConfig().getString("general.mirai-core-version","latest");
        Gen_MavenRepoUrl = plugin.getConfig().getString("general.maven-repo-url","https://repo1.maven.org/maven2");
        Gen_EnableHttpApi = plugin.getConfig().getBoolean("general.enable-http-api",false);
        Gen_LegacyEventSupport = plugin.getConfig().getBoolean("general.legacy-event-support",false);

        Bot_DisableNetworkLogs = plugin.getConfig().getBoolean("bot.disable-network-logs",false);
        Bot_DisableBotLogs = plugin.getConfig().getBoolean("bot.disable-bot-logs",false);
        Bot_UseMinecraftLogger_BotLogs = plugin.getConfig().getBoolean("bot.use-minecraft-logger.bot-logs",true);
        Bot_UseMinecraftLogger_NetworkLogs = plugin.getConfig().getBoolean("bot.use-minecraft-logger.network-logs",true);
        Bot_LogEvents = plugin.getConfig().getBoolean("bot.log-events",true);
        Bot_ContactCache_EnableFriendListCache = plugin.getConfig().getBoolean("bot.contact-cache.enable-friend-list-cache",false);
        Bot_ContactCache_EnableGroupMemberListCache = plugin.getConfig().getBoolean("bot.contact-cache.enable-group-member-list-cache",false);
        Bot_ContactCache_SaveIntervalMillis = plugin.getConfig().getLong("bot.contact-cache.save-interval-millis",60000);

        DB_Type = plugin.getConfig().getString("database.type","sqlite").toLowerCase();
        DB_MySQL_Address = plugin.getConfig().getString("database.mysql.address","localhost");
        DB_MySQL_Username = plugin.getConfig().getString("database.mysql.username", "miraimc");
        DB_MySQL_Password = plugin.getConfig().getString("database.mysql.password", "miraimc");
        DB_MySQL_Database = plugin.getConfig().getString("database.mysql.database", "miraimc");
        DB_MySQL_Poll_ConnectionTimeout = plugin.getConfig().getInt("database.mysql.pool.connectionTimeout",30000);
        DB_MySQL_Poll_IdleTimeout = plugin.getConfig().getInt("database.mysql.pool.connectionTimeout",600000);
        DB_MySQL_Poll_MaxLifetime = plugin.getConfig().getInt("database.mysql.pool.maxLifetime",1800000);
        DB_MySQL_Poll_MaximumPoolSize = plugin.getConfig().getInt("database.mysql.pool.maximumPoolSize",15);
        DB_MySQL_Poll_KeepaliveTime = plugin.getConfig().getInt("database.mysql.pool.keepaliveTime",0);
        DB_MySQL_Poll_MinimumIdle = plugin.getConfig().getInt("database.mysql.pool.minimumIdle",0);

        HTTPAPI_Url = plugin.getConfig().getString("httpapi.url", "http://localhost:8080");
        HTTPAPI_MessageFetch_Interval = plugin.getConfig().getInt("httpapi.message-fetch.interval", 10);
        HTTPAPI_MessageFetch_Count = plugin.getConfig().getInt("httpapi.message-fetch.count", 10);
    }

    public static void reloadConfig() throws IOException, InvalidConfigurationException {
        Instance.loadConfig();
    }
}
