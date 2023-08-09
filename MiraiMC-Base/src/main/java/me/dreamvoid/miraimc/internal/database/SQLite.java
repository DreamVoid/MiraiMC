package me.dreamvoid.miraimc.internal.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.loader.LibraryLoader;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLite implements Database {
    private static HikariDataSource ds; // SQLite

    @Override
    public void initialize() throws ClassNotFoundException {
        /*
        try {
            Class<HikariDataSource> clazz = HikariDataSource.class;
            Field field = clazz.getDeclaredField("LOGGER");
            field.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(ds, Utils.getLogger());
        } catch (Exception e){
            e.printStackTrace();
        }
        */

        String driver;
        if(Utils.findClass("org.sqlite.JDBC")){
            driver = "org.sqlite.JDBC";
        } else {
            try {
                LibraryLoader.loadJarMaven("org.xerial", "sqlite-jdbc", "3.36.0.3");
                initialize();
                return;
            } catch (IOException e) {
                throw new ClassNotFoundException("Local library not found and maven download failed: " + e);
            }
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setPoolName("MiraiMC-SQLite");
        config.setJdbcUrl("jdbc:sqlite:" + new File(MiraiMCConfig.Database.Settings.SQLite.Path.replace("%plugin_folder%", MiraiMCConfig.PluginDir.toPath().toString())).toPath());
        config.setConnectionTimeout(MiraiMCConfig.Database.Pool.ConnectionTimeout);
        config.setIdleTimeout(MiraiMCConfig.Database.Pool.IdleTimeout);
        config.setMaxLifetime(MiraiMCConfig.Database.Pool.MaxLifetime);
        config.setMaximumPoolSize(MiraiMCConfig.Database.Pool.MaximumPoolSize);
        config.setKeepaliveTime(MiraiMCConfig.Database.Pool.KeepaliveTime);
        config.setMinimumIdle(MiraiMCConfig.Database.Pool.MinimumIdle);
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
