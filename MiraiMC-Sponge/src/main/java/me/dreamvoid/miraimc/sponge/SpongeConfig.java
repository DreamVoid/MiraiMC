package me.dreamvoid.miraimc.sponge;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

import static me.dreamvoid.miraimc.internal.Config.*;

public class SpongeConfig {
    private final SpongePlugin plugin;
    private static SpongeConfig Instance;

    public SpongeConfig(SpongePlugin plugin){
        Instance = this;
        this.plugin = plugin;
        PluginDir = plugin.getDataFolder();
    }

    public void loadConfig() throws IOException {
        if(!PluginDir.exists() && !PluginDir.mkdirs()) throw new RuntimeException("Failed to create data folder!");
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream is = plugin.getClass().getResourceAsStream("/config.yml")) {
                assert is != null;
                Files.copy(is, file.toPath());
            }
        }
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(file);
        Map<String, Object> obj = (Map<String, Object>) yaml.load(inputStream);

        Map<String, Object> general = (Map<String, Object>) obj.get("general");
        Gen_AllowBStats = (Boolean) general.get("allow-bStats");
        Gen_DisableSafeWarningMessage = (Boolean) general.get("disable-safe-warning-message");
        Gen_MiraiWorkingDir = String.valueOf(general.get("mirai-working-dir"));

        Map<String, Object> addProperties = (Map<String, Object>) general.get("add-properties");
        Gen_AddProperties_MiraiNoDesktop = (Boolean) addProperties.get("mirai.no-desktop");
        Gen_AddProperties_MiraiSliderCaptchaSupported = (Boolean) addProperties.get("mirai.slider.captcha.supported");

        Gen_MiraiCoreVersion = String.valueOf(general.get("mirai-core-version"));
        Gen_MavenRepoUrl = String.valueOf(general.get("maven-repo-url"));
        Gen_FriendlyException = (Boolean) general.get("friendly-exception");

        Map<String, Object> bot = (Map<String, Object>) obj.get("bot");
        Bot_DisableNetworkLogs = (Boolean) bot.get("disable-network-logs");
        Bot_DisableBotLogs = (Boolean) bot.get("disable-bot-logs");

        Map<String, Object> useBukkitLogger = (Map<String, Object>) bot.get("use-bukkit-logger");
        Bot_UseBukkitLogger_BotLogs = (Boolean) useBukkitLogger.get("bot-logs");
        Bot_UseBukkitLogger_NetworkLogs = (Boolean) useBukkitLogger.get("network-logs");

        Bot_LogEvents = (Boolean) bot.get("log-events");

        Map<String, Object> contactCache = (Map<String, Object>) bot.get("contact-cache");
        Bot_ContactCache_EnableFriendListCache = (Boolean) contactCache.get("enable-friend-list-cache");
        Bot_ContactCache_EnableGroupMemberListCache = (Boolean) contactCache.get("enable-group-member-list-cache");
        Bot_ContactCache_SaveIntervalMillis = Long.parseLong(String.valueOf(contactCache.get("save-interval-millis")));

        Map<String, Object> database = (Map<String, Object>) obj.get("database");
        DB_Type = String.valueOf(database.get("type")).toLowerCase();

        Map<String, Object> mysql = (Map<String, Object>) database.get("mysql");
        DB_MySQL_Address = String.valueOf(mysql.get("address"));
        DB_MySQL_Username = String.valueOf(mysql.get("username"));
        DB_MySQL_Password = String.valueOf(mysql.get("password"));
        DB_MySQL_Database = String.valueOf(mysql.get("database"));

        Map<String, Object> pool = (Map<String, Object>) mysql.get("pool");
        DB_MySQL_Poll_ConnectionTimeout = (Integer) pool.get("connectionTimeout");
        DB_MySQL_Poll_IdleTimeout = (Integer) pool.get("connectionTimeout");
        DB_MySQL_Poll_MaxLifetime = (Integer) pool.get("maxLifetime");
        DB_MySQL_Poll_MaximumPoolSize = (Integer) pool.get("maximumPoolSize");
        DB_MySQL_Poll_KeepaliveTime = (Integer) pool.get("keepaliveTime");
        DB_MySQL_Poll_MinimumIdle = (Integer) pool.get("minimumIdle");
    }

    public static void reloadConfig() throws IOException{
        Instance.loadConfig();
    }
}
