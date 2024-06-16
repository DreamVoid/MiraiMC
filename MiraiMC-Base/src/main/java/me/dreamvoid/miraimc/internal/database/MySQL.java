package me.dreamvoid.miraimc.internal.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.dreamvoid.miraimc.LifeCycle;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.config.PluginConfig;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQL implements Database {
    private static HikariDataSource ds; // MySQL

    @Override
    public void initialize() throws ClassNotFoundException {
        if(!Utils.findClass("com.zaxxer.hikari.HikariDataSource")){
            try {
                LifeCycle.getPlatform().getLibraryLoader().loadLibraryMaven("com.zaxxer", "HikariCP", Utils.getJavaVersion() >= 11 ? "5.1.0" : "4.0.3", PluginConfig.General.MavenRepoUrl, PluginConfig.PluginDir.toPath().resolve("libraries"));
            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new ClassNotFoundException("Couldn't find HikariCP library both local and remote.");
            }
        }

        String driver;
        if (Utils.findClass("com.mysql.cj.jdbc.Driver")){
            driver = "com.mysql.cj.jdbc.Driver";
        } else if (Utils.findClass("com.mysql.jdbc.Driver")){
            driver = "com.mysql.jdbc.Driver";
        } else throw new ClassNotFoundException("Both \"com.mysql.cj.jdbc.Driver\" and \"com.mysql.jdbc.Driver\" not found.");

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setPoolName("MiraiMC-MySQL");
        config.setJdbcUrl("jdbc:mysql://" + PluginConfig.Database.Drivers.MySQL.Address + "/" + PluginConfig.Database.Drivers.MySQL.Database + PluginConfig.Database.Drivers.MySQL.Parameters);
        config.setUsername(PluginConfig.Database.Drivers.MySQL.Username);
        config.setPassword(PluginConfig.Database.Drivers.MySQL.Password);
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
