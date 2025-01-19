package me.dreamvoid.miraimc.interfaces;

import java.io.File;
import java.io.IOException;

public abstract class PluginConfig {
    public File PluginDir;

    public final void loadConfig() throws IOException {
        saveDefaultConfig();

        // general
        General_AllowBStats = getBoolean("general.allow-bStats", General_AllowBStats);
        General_CheckUpdate = getBoolean("general.check-update", General_CheckUpdate);
        General_CheckUpdatePeriod = getLong("general.check-update-period", General_CheckUpdatePeriod);
        General_DisableSafeWarningMessage = getBoolean("general.disable-safe-warning-message", General_DisableSafeWarningMessage);
        General_MiraiWorkingDir = getString("general.mirai-working-dir", General_MiraiWorkingDir);
        General_MiraiCoreVersion = getString("general.mirai-core-version", General_MiraiCoreVersion);
        General_MavenRepoUrl = getString("general.maven-repo-url", General_MavenRepoUrl);
        General_AutoOpenQRCodeFile = getBoolean("general.auto-open-qrcode-file", General_AutoOpenQRCodeFile);
        General_LogEvents = getBoolean("general.log-events", General_LogEvents);
        General_WebAPITimeout = getLong("general.webapi-timeout", General_WebAPITimeout);

        // bot
        Bot_DisableNetworkLogs = getBoolean("bot.disable-network-logs", Bot_DisableNetworkLogs);
        Bot_DisableBotLogs = getBoolean("bot.disable-bot-logs", Bot_DisableBotLogs);
        Bot_UseMinecraftLogger_BotLogs = getBoolean("bot.use-minecraft-logger.bot-logs", Bot_UseMinecraftLogger_BotLogs);
        Bot_UseMinecraftLogger_NetworkLogs = getBoolean("bot.use-minecraft-logger.network-logs", Bot_UseMinecraftLogger_NetworkLogs);
        Bot_ContactCache_EnableFriendListCache = getBoolean("bot.contact-cache.enable-friend-list-cache", Bot_ContactCache_EnableFriendListCache);
        Bot_ContactCache_EnableGroupMemberListCache = getBoolean("bot.contact-cache.enable-group-member-list-cache", Bot_ContactCache_EnableGroupMemberListCache);
        Bot_ContactCache_SaveIntervalMillis = getLong("bot.contact-cache.save-interval-millis", Bot_ContactCache_SaveIntervalMillis);

        // database
        Database_Type = getString("database.type", Database_Type).toLowerCase();
        // database.drivers.sqlite
        Database_Drivers_SQLite_Path = getString("database.drivers.sqlite.path", Database_Drivers_SQLite_Path);
        // database.drivers.mysql
        Database_Drivers_MySQL_Address = getString("database.drivers.mysql.address", Database_Drivers_MySQL_Address);
        Database_Drivers_MySQL_Username = getString("database.drivers.mysql.username", Database_Drivers_MySQL_Username);
        Database_Drivers_MySQL_Password = getString("database.drivers.mysql.password", Database_Drivers_MySQL_Password);
        Database_Drivers_MySQL_Database = getString("database.drivers.mysql.database", Database_Drivers_MySQL_Database);
        Database_Drivers_MySQL_Parameters = getString("database.drivers.mysql.parameters", Database_Drivers_MySQL_Parameters);
        // database.settings
        Database_Settings_Prefix = getString("database.settings.prefix", Database_Settings_Prefix);
        // database.settings.pool
        Database_Settings_Pool_ConnectionTimeout = getInt("database.settings.pool.connectionTimeout", Database_Settings_Pool_ConnectionTimeout);
        Database_Settings_Pool_IdleTimeout = getInt("database.settings.pool.connectionTimeout", Database_Settings_Pool_IdleTimeout);
        Database_Settings_Pool_MaxLifetime = getInt("database.settings.pool.maxLifetime", Database_Settings_Pool_MaxLifetime);
        Database_Settings_Pool_MaximumPoolSize = getInt("database.settings.pool.maximumPoolSize", Database_Settings_Pool_MaximumPoolSize);
        Database_Settings_Pool_KeepaliveTime = getInt("database.settings.pool.keepaliveTime", Database_Settings_Pool_KeepaliveTime);
        Database_Settings_Pool_MinimumIdle = getInt("database.settings.pool.minimumIdle", Database_Settings_Pool_MinimumIdle);
    }

    @SuppressWarnings("SameParameterValue")
    protected abstract void saveDefaultConfig() throws IOException;

    @SuppressWarnings("SameParameterValue")
    protected abstract String getString(String path, String defaults);

    @SuppressWarnings("SameParameterValue")
    protected abstract int getInt(String path, int defaults);

    @SuppressWarnings("SameParameterValue")
    protected abstract long getLong(String path, long defaults);

    @SuppressWarnings("SameParameterValue")
    protected abstract boolean getBoolean(String path, boolean defaults);

    // 配置部分

    public boolean General_AllowBStats = true;
    public boolean General_CheckUpdate = true;
    public long General_CheckUpdatePeriod = 1728000;
    public boolean General_DisableSafeWarningMessage = false;
    public String General_MiraiWorkingDir = "default";
    public String General_MiraiCoreVersion = "stable";
    public String General_MavenRepoUrl = "https://repo.huaweicloud.com/repository/maven/";
    public boolean General_AutoOpenQRCodeFile = false;
    public boolean General_LogEvents = true;
    public long General_WebAPITimeout = 43200000;

    public boolean Bot_DisableNetworkLogs = false;
    public boolean Bot_DisableBotLogs = false;
    public boolean Bot_UseMinecraftLogger_BotLogs = true;
    public boolean Bot_UseMinecraftLogger_NetworkLogs = true;
    public boolean Bot_ContactCache_EnableFriendListCache = false;
    public boolean Bot_ContactCache_EnableGroupMemberListCache = false;
    public long Bot_ContactCache_SaveIntervalMillis = 60000;

    public String Database_Type = "sqlite";
    public String Database_Drivers_SQLite_Path = "%plugin_folder%/database.db";
    public String Database_Drivers_MySQL_Address = "localhost";
    public String Database_Drivers_MySQL_Username = "miraimc";
    public String Database_Drivers_MySQL_Password = "miraimc";
    public String Database_Drivers_MySQL_Database = "miraimc";
    public String Database_Drivers_MySQL_Parameters = "?useSSL=false";
    public String Database_Settings_Prefix = "miraimc_";
    public int Database_Settings_Pool_ConnectionTimeout = 30000;
    public int Database_Settings_Pool_IdleTimeout = 600000;
    public int Database_Settings_Pool_MaxLifetime = 1800000;
    public int Database_Settings_Pool_MaximumPoolSize = 15;
    public int Database_Settings_Pool_KeepaliveTime = 0;
    public int Database_Settings_Pool_MinimumIdle = 5;
}
