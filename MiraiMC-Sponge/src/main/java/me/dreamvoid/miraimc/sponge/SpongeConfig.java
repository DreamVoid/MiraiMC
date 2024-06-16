package me.dreamvoid.miraimc.sponge;

import me.dreamvoid.miraimc.internal.config.PluginConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class SpongeConfig extends PluginConfig {
    private final SpongePlugin plugin;
    private static HashMap<String, Object> map;

    public SpongeConfig(SpongePlugin plugin){
        this.plugin = plugin;
        PluginConfig.PluginDir = plugin.getDataFolder();
        INSTANCE = this;
    }

    private static String getString(String path, String defaults){
        String[] args = path.split("\\.");
        HashMap<String, Object> maps = map;

        for(int i = 0; i < args.length; i++){
            if(i != args.length - 1) {
                Object o = maps.get(args[i]);

                if(o == null){
                    return defaults;
                }

                if(o instanceof HashMap<?, ?>){
                    maps = (HashMap<String, Object>) o;
                }
            } else {
                Object o = maps.get(args[i]);
                if(o instanceof String){
                    return (String) o;
                } else {
                    return String.valueOf(o);
                }
            }
        }
        return defaults;
    }

    private static int getInt(String path, int defaults){
        String[] args = path.split("\\.");
        HashMap<String, Object> maps = map;

        for(int i = 0; i < args.length; i++){
            if(i != args.length - 1) {
                Object o = maps.get(args[i]);

                if(o == null){
                    return defaults;
                }

                if(o instanceof HashMap<?, ?>){
                    maps = (HashMap<String, Object>) o;
                }
            } else {
                Object o = maps.get(args[i]);
                if(o instanceof Integer){
                    return (int) o;
                } else {
                    throw new IllegalStateException(path + " is not a integer value");
                }
            }
        }
        return defaults;
    }

    private static long getLong(String path, long defaults){
        String[] args = path.split("\\.");
        HashMap<String, Object> maps = map;

        for(int i = 0; i < args.length; i++){
            if(i != args.length - 1) {
                Object o = maps.get(args[i]);

                if(o == null){
                    return defaults;
                }

                if(o instanceof HashMap<?, ?>){
                    maps = (HashMap<String, Object>) o;
                }
            } else {
                Object o = maps.get(args[i]);
                if(o instanceof Long){
                    return (long) o;
                } else {
                    throw new IllegalStateException(path + " is not a long value");
                }
            }
        }
        return defaults;
    }

    private static boolean getBoolean(String path, boolean defaults){
        String[] args = path.split("\\.");
        HashMap<String, Object> maps = map;

        for(int i = 0; i < args.length; i++){
            if(i != args.length - 1) {
                Object o = maps.get(args[i]);

                if(o == null){
                    return defaults;
                }

                if(o instanceof HashMap<?, ?>){
                    maps = (HashMap<String, Object>) o;
                }
            } else {
                Object o = maps.get(args[i]);
                if(o instanceof Boolean){
                    return (boolean) o;
                } else {
                    throw new IllegalStateException(path + " is not a boolean value");
                }
            }
        }
        return defaults;
    }

    public void loadConfig() throws IOException {
        if(!PluginConfig.PluginDir.exists() && !PluginConfig.PluginDir.mkdirs()) throw new RuntimeException("Failed to create data folder!");
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream is = plugin.getClass().getResourceAsStream("/config.yml")) {
                assert is != null;
                Files.copy(is, file.toPath());
            }
        }

        Yaml yaml = new Yaml();
        map = yaml.loadAs(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8), HashMap.class);

        General.AllowBStats = getBoolean("general.allow-bStats", General.AllowBStats);
        General.CheckUpdate = getBoolean("general.check-update", General.CheckUpdate);
        General.DisableSafeWarningMessage = getBoolean("general.disable-safe-warning-message",General.DisableSafeWarningMessage);
        General.MiraiWorkingDir = getString("general.mirai-working-dir",General.MiraiWorkingDir);
        General.MiraiCoreVersion = getString("general.mirai-core-version",General.MiraiCoreVersion);
        General.MavenRepoUrl = getString("general.maven-repo-url",General.MavenRepoUrl);
        General.EnableHttpApi = getBoolean("general.enable-http-api",General.EnableHttpApi);
        General.AutoOpenQRCodeFile = getBoolean("general.auto-open-qrcode-file",General.AutoOpenQRCodeFile);
        General.LogEvents = getBoolean("general.log-events",General.LogEvents);

        Bot.DisableNetworkLogs = getBoolean("bot.disable-network-logs",Bot.DisableNetworkLogs);
        Bot.DisableBotLogs = getBoolean("bot.disable-bot-logs",Bot.DisableBotLogs);
        Bot.UseMinecraftLogger.BotLogs = getBoolean("bot.use-minecraft-logger.bot-logs",Bot.UseMinecraftLogger.BotLogs);
        Bot.UseMinecraftLogger.NetworkLogs = getBoolean("bot.use-minecraft-logger.network-logs",Bot.UseMinecraftLogger.NetworkLogs);
        Bot.ContactCache.EnableFriendListCache = getBoolean("bot.contact-cache.enable-friend-list-cache",Bot.ContactCache.EnableFriendListCache);
        Bot.ContactCache.EnableGroupMemberListCache = getBoolean("bot.contact-cache.enable-group-member-list-cache",Bot.ContactCache.EnableGroupMemberListCache);
        Bot.ContactCache.SaveIntervalMillis = getLong("bot.contact-cache.save-interval-millis",Bot.ContactCache.SaveIntervalMillis);
        Bot.RegisterEncryptService = getBoolean("bot.register-encrypt-service",Bot.RegisterEncryptService);
        Bot.UpdateProtocolVersion = getBoolean("bot.update-protocol-version",Bot.UpdateProtocolVersion);

        Database.Type = getString("database.type",Database.Type).toLowerCase();
        Database.Drivers.SQLite.Path = getString("database.settings.sqlite.path", Database.Drivers.SQLite.Path);
        Database.Drivers.MySQL.Address = getString("database.settings.mysql.address",Database.Drivers.MySQL.Address);
        Database.Drivers.MySQL.Username = getString("database.settings.mysql.username", Database.Drivers.MySQL.Username);
        Database.Drivers.MySQL.Password = getString("database.settings.mysql.password", Database.Drivers.MySQL.Password);
        Database.Drivers.MySQL.Database = getString("database.settings.mysql.database", Database.Drivers.MySQL.Database);
        Database.Drivers.MySQL.Parameters = getString("database.settings.mysql.parameters", Database.Drivers.MySQL.Parameters);
        Database.Settings.Prefix = getString("database.settings.prefix", Database.Settings.Prefix);
        Database.Settings.Pool.ConnectionTimeout = getInt("database.pool.connectionTimeout", Database.Settings.Pool.ConnectionTimeout);
        Database.Settings.Pool.IdleTimeout = getInt("database.pool.connectionTimeout", Database.Settings.Pool.IdleTimeout);
        Database.Settings.Pool.MaxLifetime = getInt("database.pool.maxLifetime", Database.Settings.Pool.MaxLifetime);
        Database.Settings.Pool.MaximumPoolSize = getInt("database.pool.maximumPoolSize", Database.Settings.Pool.MaximumPoolSize);
        Database.Settings.Pool.KeepaliveTime = getInt("database.pool.keepaliveTime", Database.Settings.Pool.KeepaliveTime);
        Database.Settings.Pool.MinimumIdle = getInt("database.pool.minimumIdle", Database.Settings.Pool.MinimumIdle);

        HttpApi.Url = getString("http-api.url", HttpApi.Url);
        HttpApi.MessageFetch.Interval = getInt("http-api.message-fetch.interval", HttpApi.MessageFetch.Interval);
        HttpApi.MessageFetch.Count = getInt("http-api.message-fetch.count", HttpApi.MessageFetch.Count);
    }
}
