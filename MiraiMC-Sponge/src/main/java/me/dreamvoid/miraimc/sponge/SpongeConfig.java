package me.dreamvoid.miraimc.sponge;

import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.internal.ConfigSerializable;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class SpongeConfig extends MiraiMCConfig {
    private final SpongePlugin plugin;

    public SpongeConfig(SpongePlugin plugin){
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

        Yaml yaml = new Yaml();
        String config = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8).replace("-", "__");
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

        Database.Type = serializable.database.type;
        Database.MySQL.Address = serializable.database.mysql.address;
        Database.MySQL.Username = serializable.database.mysql.username;
        Database.MySQL.Password = serializable.database.mysql.password;
        Database.MySQL.Database = serializable.database.mysql.database;
        Database.MySQL.Poll.ConnectionTimeout = serializable.database.mysql.pool.connectionTimeout;
        Database.MySQL.Poll.IdleTimeout = serializable.database.mysql.pool.idleTimeout;
        Database.MySQL.Poll.MaxLifetime = serializable.database.mysql.pool.maxLifetime;
        Database.MySQL.Poll.MaximumPoolSize = serializable.database.mysql.pool.maximumPoolSize;
        Database.MySQL.Poll.KeepaliveTime = serializable.database.mysql.pool.keepaliveTime;
        Database.MySQL.Poll.MinimumIdle = serializable.database.mysql.pool.minimumIdle;

        HttpApi.Url = serializable.http__api.url;
        HttpApi.MessageFetch.Interval = serializable.http__api.message__fetch.interval;
        HttpApi.MessageFetch.Count = serializable.http__api.message__fetch.count;
    }
}
