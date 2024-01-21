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
        if (Utils.findClass("com.mysql.cj.jdbc.Driver")){
            driver = "com.mysql.cj.jdbc.Driver";
        } else if (Utils.findClass("com.mysql.jdbc.Driver")){
            driver = "com.mysql.jdbc.Driver";
        } else throw new ClassNotFoundException("Both \"com.mysql.cj.jdbc.Driver\" and \"com.mysql.jdbc.Driver\" not found.");

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setPoolName("MiraiMC-MySQL");
        config.setJdbcUrl("jdbc:mysql://" + MiraiMCConfig.Database.Drivers.MySQL.Address + "/" + MiraiMCConfig.Database.Drivers.MySQL.Database + MiraiMCConfig.Database.Drivers.MySQL.Parameters);
        config.setUsername(MiraiMCConfig.Database.Drivers.MySQL.Username);
        config.setPassword(MiraiMCConfig.Database.Drivers.MySQL.Password);
        config.setConnectionTimeout(MiraiMCConfig.Database.Settings.Pool.ConnectionTimeout);
        config.setIdleTimeout(MiraiMCConfig.Database.Settings.Pool.IdleTimeout);
        config.setMaxLifetime(MiraiMCConfig.Database.Settings.Pool.MaxLifetime);
        config.setMaximumPoolSize(MiraiMCConfig.Database.Settings.Pool.MaximumPoolSize);
        config.setKeepaliveTime(MiraiMCConfig.Database.Settings.Pool.KeepaliveTime);
        config.setMinimumIdle(MiraiMCConfig.Database.Settings.Pool.MinimumIdle);
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
