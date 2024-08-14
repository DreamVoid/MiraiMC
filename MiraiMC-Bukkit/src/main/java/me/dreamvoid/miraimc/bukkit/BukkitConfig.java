package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.internal.config.PluginConfig;

public class BukkitConfig extends PluginConfig {
    private final BukkitPlugin plugin;

    public BukkitConfig(BukkitPlugin plugin){
        this.plugin = plugin;
        PluginDir = plugin.getDataFolder();
        INSTANCE = this;
    }

    @Override
    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        General.AllowBStats = plugin.getConfig().getBoolean("general.allow-bStats", General.AllowBStats);
        General.CheckUpdate = plugin.getConfig().getBoolean("general.check-update", General.CheckUpdate);
        General.DisableSafeWarningMessage = plugin.getConfig().getBoolean("general.disable-safe-warning-message",General.DisableSafeWarningMessage);
        General.MiraiWorkingDir = plugin.getConfig().getString("general.mirai-working-dir",General.MiraiWorkingDir);
        General.MiraiCoreVersion = plugin.getConfig().getString("general.mirai-core-version",General.MiraiCoreVersion);
        General.MavenRepoUrl = plugin.getConfig().getString("general.maven-repo-url",General.MavenRepoUrl);
        General.AutoOpenQRCodeFile = plugin.getConfig().getBoolean("general.auto-open-qrcode-file",General.AutoOpenQRCodeFile);
        General.LogEvents = plugin.getConfig().getBoolean("general.log-events",General.LogEvents);

        Bot.DisableNetworkLogs = plugin.getConfig().getBoolean("bot.disable-network-logs",Bot.DisableNetworkLogs);
        Bot.DisableBotLogs = plugin.getConfig().getBoolean("bot.disable-bot-logs",Bot.DisableBotLogs);
        Bot.UseMinecraftLogger.BotLogs = plugin.getConfig().getBoolean("bot.use-minecraft-logger.bot-logs",Bot.UseMinecraftLogger.BotLogs);
        Bot.UseMinecraftLogger.NetworkLogs = plugin.getConfig().getBoolean("bot.use-minecraft-logger.network-logs",Bot.UseMinecraftLogger.NetworkLogs);
        Bot.ContactCache.EnableFriendListCache = plugin.getConfig().getBoolean("bot.contact-cache.enable-friend-list-cache",Bot.ContactCache.EnableFriendListCache);
        Bot.ContactCache.EnableGroupMemberListCache = plugin.getConfig().getBoolean("bot.contact-cache.enable-group-member-list-cache",Bot.ContactCache.EnableGroupMemberListCache);
        Bot.ContactCache.SaveIntervalMillis = plugin.getConfig().getLong("bot.contact-cache.save-interval-millis",Bot.ContactCache.SaveIntervalMillis);
        Bot.RegisterEncryptService = plugin.getConfig().getBoolean("bot.register-encrypt-service",Bot.RegisterEncryptService);
        Bot.UpdateProtocolVersion = plugin.getConfig().getBoolean("bot.update-protocol-version",Bot.UpdateProtocolVersion);

        Database.Type = plugin.getConfig().getString("database.type",Database.Type).toLowerCase();
        Database.Drivers.SQLite.Path = plugin.getConfig().getString("database.settings.sqlite.path", Database.Drivers.SQLite.Path);
        Database.Drivers.MySQL.Address = plugin.getConfig().getString("database.settings.mysql.address",Database.Drivers.MySQL.Address);
        Database.Drivers.MySQL.Username = plugin.getConfig().getString("database.settings.mysql.username", Database.Drivers.MySQL.Username);
        Database.Drivers.MySQL.Password = plugin.getConfig().getString("database.settings.mysql.password", Database.Drivers.MySQL.Password);
        Database.Drivers.MySQL.Database = plugin.getConfig().getString("database.settings.mysql.database", Database.Drivers.MySQL.Database);
        Database.Drivers.MySQL.Parameters = plugin.getConfig().getString("database.settings.mysql.parameters", Database.Drivers.MySQL.Parameters);
        Database.Settings.Prefix = plugin.getConfig().getString("database.settings.prefix", Database.Settings.Prefix);
        Database.Settings.Pool.ConnectionTimeout = plugin.getConfig().getInt("database.pool.connectionTimeout", Database.Settings.Pool.ConnectionTimeout);
        Database.Settings.Pool.IdleTimeout = plugin.getConfig().getInt("database.pool.connectionTimeout", Database.Settings.Pool.IdleTimeout);
        Database.Settings.Pool.MaxLifetime = plugin.getConfig().getInt("database.pool.maxLifetime", Database.Settings.Pool.MaxLifetime);
        Database.Settings.Pool.MaximumPoolSize = plugin.getConfig().getInt("database.pool.maximumPoolSize", Database.Settings.Pool.MaximumPoolSize);
        Database.Settings.Pool.KeepaliveTime = plugin.getConfig().getInt("database.pool.keepaliveTime", Database.Settings.Pool.KeepaliveTime);
        Database.Settings.Pool.MinimumIdle = plugin.getConfig().getInt("database.pool.minimumIdle", Database.Settings.Pool.MinimumIdle);
    }
}
