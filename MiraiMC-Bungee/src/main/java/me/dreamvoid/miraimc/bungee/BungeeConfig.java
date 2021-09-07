package me.dreamvoid.miraimc.bungee;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static me.dreamvoid.miraimc.internal.Config.*;

public class BungeeConfig {
    private Configuration bungeeConfig;
    private BungeePlugin BungeePlugin;
    private static BungeeConfig Instance;

    public BungeeConfig(BungeePlugin bungee) {
        BungeePlugin = bungee;
        PluginDir = bungee.getDataFolder();
        Instance = this;
    }

    public void loadConfig() {
        try {
            if (!BungeePlugin.getDataFolder().exists()) {
                if(!BungeePlugin.getDataFolder().mkdir()) throw new IOException();
            }
            File file = new File(BungeePlugin.getDataFolder(), "config.yml");
            if (!file.exists()) {
                try (InputStream in = BungeePlugin.getResourceAsStream("config.yml")) {
                    Files.copy(in, file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bungeeConfig = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(new File(BungeePlugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gen_AllowBStats = bungeeConfig.getBoolean("general.allow-bStats",true);
        Gen_DisableSafeWarningMessage = bungeeConfig.getBoolean("general.disable-safe-warning-message",false);
        Gen_MiraiWorkingDir = bungeeConfig.getString("general.mirai-working-dir","default");
        Gen_AddProperties_MiraiNoDesktop = bungeeConfig.getBoolean("general.add-properties.mirai.no-desktop",true);
        Gen_AddProperties_MiraiSliderCaptchaSupported = bungeeConfig.getBoolean("general.add-properties.mirai.slider.captcha.supported",true);

        Bot_DisableNetworkLogs = bungeeConfig.getBoolean("bot.disable-network-logs",false);
        Bot_DisableBotLogs = bungeeConfig.getBoolean("bot.disable-bot-logs",false);
        Bot_UseBukkitLogger_BotLogs = bungeeConfig.getBoolean("bot.use-bukkit-logger.bot-logs",true);
        Bot_UseBukkitLogger_NetworkLogs = bungeeConfig.getBoolean("bot.use-bukkit-logger.network-logs",true);
        Bot_LogEvents = bungeeConfig.getBoolean("bot.log-events",true);
        Bot_ContactCache_EnableFriendListCache = bungeeConfig.getBoolean("bot.contact-cache.enable-friend-list-cache",false);
        Bot_ContactCache_EnableGroupMemberListCache = bungeeConfig.getBoolean("bot.contact-cache.enable-group-member-list-cache",false);
        Bot_ContactCache_SaveIntervalMillis = bungeeConfig.getLong("bot.contact-cache.save-interval-millis",60000);

        DB_Type = bungeeConfig.getString("database.type","sqlite").toLowerCase();
        DB_MySQL_Address = bungeeConfig.getString("database.mysql.address", "localhost");
        DB_MySQL_Username = bungeeConfig.getString("database.mysql.username", "miraimc");
        DB_MySQL_Password = bungeeConfig.getString("database.mysql.password", "miraimc");
        DB_MySQL_Database = bungeeConfig.getString("database.mysql.database", "miraimc");
        DB_MySQL_Poll_ConnectionTimeout = bungeeConfig.getInt("database.mysql.pool.connectionTimeout",30000);
        DB_MySQL_Poll_IdleTimeout = bungeeConfig.getInt("database.mysql.pool.connectionTimeout",600000);
        DB_MySQL_Poll_MaxLifetime = bungeeConfig.getInt("database.mysql.pool.maxLifetime",1800000);
        DB_MySQL_Poll_MaximumPoolSize = bungeeConfig.getInt("database.mysql.pool.maximumPoolSize",15);
        DB_MySQL_Poll_KeepaliveTime = bungeeConfig.getInt("database.mysql.pool.keepaliveTime",0);
        DB_MySQL_Poll_MinimumIdle = bungeeConfig.getInt("database.mysql.pool.minimumIdle",0);
    }

    public static void reloadConfig() {
        Instance.loadConfig();
    }
}
