package me.dreamvoid.miraimc.internal.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.internal.Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL implements Database {
    private static HikariDataSource ds; // MySQL

    @Override
    public void initialize() throws ClassNotFoundException {
        String driver;
        if (Utils.findClass("com.mysql.cj.jdbc.Driver")){
            driver = "com.mysql.cj.jdbc.Driver";
        } else if (Utils.findClass("com.mysql.jdbc.Driver")){
            driver = "com.mysql.jdbc.Driver";
        } else throw new ClassNotFoundException("Both \"com.mysql.cj.jdbc.Driver\" and \"com.mysql.jdbc.Driver\" not found.");

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setPoolName("MiraiMC-MySQL");
        config.setJdbcUrl("jdbc:mysql://" + MiraiMCConfig.Database.Settings.MySQL.Address + "/" + MiraiMCConfig.Database.Settings.MySQL.Database + MiraiMCConfig.Database.Settings.MySQL.Parameters);
        config.setUsername(MiraiMCConfig.Database.Settings.MySQL.Username);
        config.setPassword(MiraiMCConfig.Database.Settings.MySQL.Password);
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
