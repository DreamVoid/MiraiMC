package me.dreamvoid.miraimc.velocity;

import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.internal.ConfigSerializable;
import me.dreamvoid.miraimc.internal.Utils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class VelocityConfig extends MiraiMCConfig {
    private final VelocityPlugin plugin;

    public VelocityConfig(VelocityPlugin plugin){
        this.plugin = plugin;
        PluginDir = plugin.getDataFolder();
        INSTANCE = this;
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

        Yaml yaml = new Yaml(new CustomClassLoaderConstructor(Utils.getClassLoader()));
        String config = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8).replace("-", "__").replace("__ ", "- ");
        ConfigSerializable serializable = yaml.loadAs(config, ConfigSerializable.class);

        General.AllowBStats = serializable.general.allow__bStats;
        General.CheckUpdate = serializable.general.check__update;
        General.DisableSafeWarningMessage = serializable.general.disable__safe__warning__message;
        General.MiraiWorkingDir = serializable.general.mirai__working__dir;
        General.MiraiCoreVersion = serializable.general.mirai__core__version;
        General.MavenRepoUrl = serializable.general.maven__repo__url;
        General.EnableHttpApi = serializable.general.enable__http__api;
        General.AutoOpenQRCodeFile = serializable.general.auto__open__qrcode__file;
        General.LogEvents = serializable.general.log__events;

        Bot.DisableNetworkLogs = serializable.bot.disable__network__logs;
        Bot.DisableBotLogs = serializable.bot.disable__bot__logs;
        Bot.UseMinecraftLogger.BotLogs = serializable.bot.use__minecraft__logger.bot__logs;
        Bot.UseMinecraftLogger.NetworkLogs = serializable.bot.use__minecraft__logger.network__logs;
        Bot.ContactCache.EnableFriendListCache = serializable.bot.contact__cache.enable__friend__list__cache;
        Bot.ContactCache.EnableGroupMemberListCache = serializable.bot.contact__cache.enable__group__member__list__cache;
        Bot.ContactCache.SaveIntervalMillis = serializable.bot.contact__cache.save__interval__millis;
        Bot.RegisterEncryptService = serializable.bot.register__encrypt__service;
        Bot.UpdateProtocolVersion = serializable.bot.update__protocol__version;

        Database.Type = serializable.database.type;
        Database.Drivers.SQLite.Path = serializable.database.drivers.sqlite.path;
        Database.Drivers.MySQL.Address = serializable.database.drivers.mysql.address;
        Database.Drivers.MySQL.Username = serializable.database.drivers.mysql.username;
        Database.Drivers.MySQL.Password = serializable.database.drivers.mysql.password;
        Database.Drivers.MySQL.Database = serializable.database.drivers.mysql.database;
        Database.Drivers.MySQL.Parameters = serializable.database.drivers.mysql.parameters;
        Database.Settings.Pool.ConnectionTimeout = serializable.database.settings.pool.connectionTimeout;
        Database.Settings.Pool.IdleTimeout = serializable.database.settings.pool.idleTimeout;
        Database.Settings.Pool.MaxLifetime = serializable.database.settings.pool.maxLifetime;
        Database.Settings.Pool.MaximumPoolSize = serializable.database.settings.pool.maximumPoolSize;
        Database.Settings.Pool.KeepaliveTime = serializable.database.settings.pool.keepaliveTime;
        Database.Settings.Pool.MinimumIdle = serializable.database.settings.pool.minimumIdle;

        HttpApi.Url = serializable.http__api.url;
        HttpApi.MessageFetch.Interval = serializable.http__api.message__fetch.interval;
        HttpApi.MessageFetch.Count = serializable.http__api.message__fetch.count;
    }
}
