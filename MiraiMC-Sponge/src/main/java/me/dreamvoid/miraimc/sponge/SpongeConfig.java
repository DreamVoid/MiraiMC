package me.dreamvoid.miraimc.sponge;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

import static me.dreamvoid.miraimc.internal.Config.*;

public class SpongeConfig {
    private final SpongePlugin plugin;
    private static SpongeConfig Instance;

    public static ConfigurationLoader<CommentedConfigurationNode> loader;

    public SpongeConfig(SpongePlugin plugin){
        Instance = this;
        this.plugin = plugin;
        PluginDir = plugin.getDataFolder();
    }

    public void loadConfig() throws IOException {
        if(!PluginDir.exists() && !PluginDir.mkdirs()) throw new RuntimeException("Failed to create data folder!");
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream is = this.getClass().getResourceAsStream("/config.json")) {
                assert is != null;
                Files.copy(is, file.toPath());
            }
        }
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(file);
        Map<String, Object> obj = (Map<String, Object>) yaml.load(inputStream);
        System.out.println(obj);
        Map<String, Object> general = (Map<String, Object>) obj.get("general");
        System.out.println(general);
        
        Gen_AllowBStats = (Boolean) general.get("general.allow-bStats");
        Gen_DisableSafeWarningMessage = (Boolean) general.get("general.disable-safe-warning-message");
        Gen_MiraiWorkingDir = String.valueOf(general.get("general.mirai-working-dir"));
        Gen_AddProperties_MiraiNoDesktop = (Boolean) general.get("general.add-properties.mirai.no-desktop");
        Gen_AddProperties_MiraiSliderCaptchaSupported = (Boolean) general.get("general.add-properties.mirai.slider.captcha.supported");
        Gen_MiraiCoreVersion = String.valueOf(general.get("general.mirai-core-version"));
        Gen_MavenRepoUrl = String.valueOf(general.get("general.maven-repo-url"));
        Gen_FriendlyException = (Boolean) general.get("general.friendly-exception");

        Bot_DisableNetworkLogs = (Boolean) ((Map<?, ?>) obj).get("bot.disable-network-logs");
        Bot_DisableBotLogs = (Boolean) ((Map<?, ?>) obj).get("bot.disable-bot-logs");
        Bot_UseBukkitLogger_BotLogs = (Boolean) ((Map<?, ?>) obj).get("bot.use-bukkit-logger.bot-logs");
        Bot_UseBukkitLogger_NetworkLogs = (Boolean) ((Map<?, ?>) obj).get("bot.use-bukkit-logger.network-logs");
        Bot_LogEvents = (Boolean) ((Map<?, ?>) obj).get("bot.log-events");
        Bot_ContactCache_EnableFriendListCache = (Boolean) ((Map<?, ?>) obj).get("bot.contact-cache.enable-friend-list-cache");
        Bot_ContactCache_EnableGroupMemberListCache = (Boolean) ((Map<?, ?>) obj).get("bot.contact-cache.enable-group-member-list-cache");
        Bot_ContactCache_SaveIntervalMillis = (Long) ((Map<?, ?>) obj).get("bot.contact-cache.save-interval-millis");

        DB_Type = String.valueOf(((Map<?, ?>) obj).get("database.type")).toLowerCase();
        DB_MySQL_Address = String.valueOf(((Map<?, ?>) obj).get("database.mysql.address"));
        DB_MySQL_Username = String.valueOf(((Map<?, ?>) obj).get("database.mysql.username"));
        DB_MySQL_Password = String.valueOf(((Map<?, ?>) obj).get("database.mysql.password"));
        DB_MySQL_Database = String.valueOf(((Map<?, ?>) obj).get("database.mysql.database"));
        DB_MySQL_Poll_ConnectionTimeout = (Integer) ((Map<?, ?>) obj).get("database.mysql.pool.connectionTimeout");
        DB_MySQL_Poll_IdleTimeout = (Integer) ((Map<?, ?>) obj).get("database.mysql.pool.connectionTimeout");
        DB_MySQL_Poll_MaxLifetime = (Integer) ((Map<?, ?>) obj).get("database.mysql.pool.maxLifetime");
        DB_MySQL_Poll_MaximumPoolSize = (Integer) ((Map<?, ?>) obj).get("database.mysql.pool.maximumPoolSize");
        DB_MySQL_Poll_KeepaliveTime = (Integer) ((Map<?, ?>) obj).get("database.mysql.pool.keepaliveTime");
        DB_MySQL_Poll_MinimumIdle = (Integer) ((Map<?, ?>) obj).get("database.mysql.pool.minimumIdle");
    }

    public static void reloadConfig() throws IOException{
        Instance.loadConfig();
    }
}
