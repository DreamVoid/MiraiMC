package me.dreamvoid.miraimc.velocity;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static me.dreamvoid.miraimc.internal.Config.*;

public class VelocityConfig {
    private final VelocityPlugin plugin;
    private static VelocityConfig Instance;

    public VelocityConfig(VelocityPlugin plugin){
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
        Map<String, Object> obj = yaml.load(inputStream);

        Map<String, Object> general = !Objects.isNull(obj.get("general")) ? (Map<String, Object>) obj.get("general") : new HashMap<>();
        Gen_AllowBStats = !Objects.isNull(general.get("allow-bStats")) ? (Boolean) general.get("allow-bStats") : false;
        Gen_CheckUpdate = !Objects.isNull(general.get("check-update")) ? (Boolean) general.get("check-update") : false;
        Gen_DisableSafeWarningMessage = !Objects.isNull(general.get("disable-safe-warning-message")) ? (Boolean) general.get("disable-safe-warning-message") : false;
        Gen_MiraiWorkingDir = !Objects.isNull(general.get("mirai-working-dir")) ? String.valueOf(general.get("mirai-working-dir")) : "default";

        Map<String, Object> addProperties = !Objects.isNull(general.get("add-properties")) ? (Map<String, Object>) general.get("add-properties") : new HashMap<>();
        Gen_AddProperties_MiraiNoDesktop = !Objects.isNull(addProperties.get("mirai.no-desktop")) ? (Boolean) addProperties.get("mirai.no-desktop") : true;
        Gen_AddProperties_MiraiSliderCaptchaSupported = !Objects.isNull(addProperties.get("mirai.slider.captcha.supported")) ? (Boolean) addProperties.get("mirai.slider.captcha.supported") : true;

        Gen_MiraiCoreVersion = !Objects.isNull(general.get("mirai-core-version")) ? String.valueOf(general.get("mirai-core-version")) : "latest";
        Gen_MavenRepoUrl = !Objects.isNull(general.get("maven-repo-url")) ? String.valueOf(general.get("maven-repo-url")) : "https://maven.aliyun.com/nexus/content/groups/public/";
        Gen_EnableHttpApi = !Objects.isNull(general.get("enable-http-api")) ? (Boolean) general.get("enable-http-api") : false;
        Gen_LegacyEventSupport = !Objects.isNull(general.get("legacy-event-support")) ? (Boolean) general.get("legacy-event-support") : false;

        Map<String, Object> bot = !Objects.isNull(obj.get("bot")) ? (Map<String, Object>) obj.get("bot") : new HashMap<>();
        Bot_DisableNetworkLogs = !Objects.isNull(bot.get("disable-network-logs")) ? (Boolean) bot.get("disable-network-logs") : false;
        Bot_DisableBotLogs = !Objects.isNull(bot.get("disable-bot-logs")) ? (Boolean) bot.get("disable-bot-logs") : false;

        Map<String, Object> useBukkitLogger = !Objects.isNull(bot.get("use-minecraft-logger")) ? (Map<String, Object>) bot.get("use-minecraft-logger") : new HashMap<>();
        Bot_UseMinecraftLogger_BotLogs = !Objects.isNull(useBukkitLogger.get("bot-logs")) ? (Boolean) useBukkitLogger.get("bot-logs") : true;
        Bot_UseMinecraftLogger_NetworkLogs = !Objects.isNull(useBukkitLogger.get("network-logs")) ? (Boolean) useBukkitLogger.get("network-logs") : true;

        Bot_LogEvents = !Objects.isNull(bot.get("log-events")) ? (Boolean) bot.get("log-events") : true;

        Map<String, Object> contactCache = !Objects.isNull(bot.get("contact-cache")) ? (Map<String, Object>) bot.get("contact-cache") : new HashMap<>();
        Bot_ContactCache_EnableFriendListCache = !Objects.isNull(contactCache.get("enable-friend-list-cache")) ? (Boolean) contactCache.get("enable-friend-list-cache") : false;
        Bot_ContactCache_EnableGroupMemberListCache = !Objects.isNull(contactCache.get("enable-group-member-list-cache")) ? (Boolean) contactCache.get("enable-group-member-list-cache") : false;
        Bot_ContactCache_SaveIntervalMillis = !Objects.isNull(contactCache.get("save-interval-millis")) ? Long.parseLong(String.valueOf(contactCache.get("save-interval-millis"))) : 60000;

        Map<String, Object> database = !Objects.isNull(obj.get("database")) ? (Map<String, Object>) obj.get("database") : new HashMap<>();
        DB_Type = !Objects.isNull(database.get("type")) ? String.valueOf(database.get("type")).toLowerCase() : "sqlite";

        Map<String, Object> mysql = !Objects.isNull(database.get("mysql")) ? (Map<String, Object>) database.get("mysql") : new HashMap<>();
        DB_MySQL_Address = !Objects.isNull(mysql.get("address")) ? String.valueOf(mysql.get("address")) : "localhost";
        DB_MySQL_Username = !Objects.isNull(mysql.get("username")) ? String.valueOf(mysql.get("username")) : "miraimc";
        DB_MySQL_Password = !Objects.isNull(mysql.get("password")) ? String.valueOf(mysql.get("password")) : "miraimc";
        DB_MySQL_Database = !Objects.isNull(mysql.get("database")) ? String.valueOf(mysql.get("database")) : "miraimc";

        Map<String, Object> pool = !Objects.isNull(mysql.get("pool")) ? (Map<String, Object>) mysql.get("pool") : new HashMap<>();
        DB_MySQL_Poll_ConnectionTimeout = !Objects.isNull(pool.get("connectionTimeout")) ? (Integer) pool.get("connectionTimeout") : 30000;
        DB_MySQL_Poll_IdleTimeout = !Objects.isNull(pool.get("connectionTimeout")) ? (Integer) pool.get("connectionTimeout") : 600000;
        DB_MySQL_Poll_MaxLifetime = !Objects.isNull(pool.get("maxLifetime")) ? (Integer) pool.get("maxLifetime") : 1800000;
        DB_MySQL_Poll_MaximumPoolSize = !Objects.isNull(pool.get("maximumPoolSize")) ? (Integer) pool.get("maximumPoolSize") : 15;
        DB_MySQL_Poll_KeepaliveTime = !Objects.isNull(pool.get("keepaliveTime")) ? (Integer) pool.get("keepaliveTime") : 0;
        DB_MySQL_Poll_MinimumIdle = !Objects.isNull(pool.get("minimumIdle")) ? (Integer) pool.get("minimumIdle") : 5;
    }

    public static void reloadConfig() throws IOException{
        Instance.loadConfig();
    }
}
