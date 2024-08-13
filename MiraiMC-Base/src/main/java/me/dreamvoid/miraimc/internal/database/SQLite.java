package me.dreamvoid.miraimc.internal.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.config.PluginConfig;
import me.dreamvoid.miraimc.internal.loader.LibraryLoader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLite implements Database {
    private static HikariDataSource ds; // SQLite

    @Override
    public void initialize() throws ClassNotFoundException {
        LibraryLoader loader = MiraiMC.getPlatform().getLibraryLoader();
        if(!Utils.findClass("com.zaxxer.hikari.HikariDataSource")){
            try {
                loader.loadLibraryMaven("com.zaxxer", "HikariCP", Utils.getJavaVersion() >= 11 ? "5.1.0" : "4.0.3", PluginConfig.General.MavenRepoUrl, PluginConfig.PluginDir.toPath().resolve("libraries"));
            } catch (ParserConfigurationException | SAXException | IOException e) {
                throw new ClassNotFoundException("Couldn't find HikariCP library both local and remote.");
            }
        }

        String driver;
        if(Utils.findClass("org.sqlite.JDBC")){
            driver = "org.sqlite.JDBC";
        } else {
            try {
                loader.loadLibraryMaven("org.xerial", "sqlite-jdbc", "3.36.0.3", PluginConfig.General.MavenRepoUrl, PluginConfig.PluginDir.toPath().resolve("libraries"));
            } catch (Exception e) {
                throw new ClassNotFoundException("Couldn't find SQLite library both local and remote.");
            }

            initialize();
            return;
        }

        try {
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
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            ds = new HikariDataSource(config);
        } catch (AbstractMethodError error){
            Utils.resolveException(error, Utils.getLogger(), "发生了一个意料之中的问题，请查阅文档了解更多信息：https://docs.miraimc.dreamvoid.me/troubleshoot/faq");
        }
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
