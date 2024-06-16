package me.dreamvoid.miraimc.internal.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.dreamvoid.miraimc.LifeCycle;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.config.PluginConfig;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLite implements Database {
    private static HikariDataSource ds; // SQLite

    @Override
    public void initialize() {
        String driver;
        if(Utils.findClass("org.sqlite.JDBC")){
            driver = "org.sqlite.JDBC";
        } else {
            try {
                LifeCycle.getPlatform().getLibraryLoader().loadLibraryMaven("org.xerial", "sqlite-jdbc", "3.36.0.3", PluginConfig.General.MavenRepoUrl, PluginConfig.PluginDir.toPath().resolve("libraries"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            initialize();
            return;
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setPoolName("MiraiMC-SQLite");
        config.setJdbcUrl("jdbc:sqlite:" + new File(PluginConfig.Database.Drivers.SQLite.Path.replace("%plugin_folder%", PluginConfig.PluginDir.toPath().toString())).toPath());
        config.setConnectionTimeout(PluginConfig.Database.Settings.Pool.ConnectionTimeout);
        config.setIdleTimeout(PluginConfig.Database.Settings.Pool.IdleTimeout);
        config.setMaxLifetime(PluginConfig.Database.Settings.Pool.MaxLifetime);
        config.setMaximumPoolSize(PluginConfig.Database.Settings.Pool.MaximumPoolSize);
        config.setKeepaliveTime(PluginConfig.Database.Settings.Pool.KeepaliveTime);
        config.setMinimumIdle(PluginConfig.Database.Settings.Pool.MinimumIdle);
        config.addDataSourceProperty("cachePrepStmts", "true" );
        config.addDataSourceProperty("prepStmtCacheSize", "250" );
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048" );

        ds = new HikariDataSource(config);
    }

    @Override
    public void close() {
        ds.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
