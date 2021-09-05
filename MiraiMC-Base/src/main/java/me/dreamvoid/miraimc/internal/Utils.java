package me.dreamvoid.miraimc.internal;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Utils {
    public static Logger logger;

    public static Connection connection; // SQLite
    public static HikariDataSource ds; // MySQL

    public static void initUtils(Logger logger){
        Utils.logger = logger;
    }

    public static void initializeSQLite() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + new File(Config.PluginDir,"database.db").getPath());
    }

    public static void closeSQLite() throws SQLException {
        connection.close();
    }

    public static void initializeMySQL(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver"); // 1.17才有cj.jdbc，所以不换
        config.setJdbcUrl("jdbc:mysql://" + Config.DB_MySQL_Address + "/" + Config.DB_MySQL_Database);
        config.setUsername(Config.DB_MySQL_Username);
        config.setPassword(Config.DB_MySQL_Password);
        config.setConnectionTimeout(Config.DB_MySQL_Poll_ConnectionTimeout);
        config.setIdleTimeout(Config.DB_MySQL_Poll_IdleTimeout);
        config.setMaxLifetime(Config.DB_MySQL_Poll_MaxLifetime);
        config.setMaximumPoolSize(Config.DB_MySQL_Poll_MaximumPoolSize);
        config.setKeepaliveTime(Config.DB_MySQL_Poll_KeepaliveTime);
        config.setMinimumIdle(Config.DB_MySQL_Poll_MinimumIdle);
        config.addDataSourceProperty("cachePrepStmts", "true" );
        config.addDataSourceProperty("prepStmtCacheSize", "250" );
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048" );

        ds = new HikariDataSource(config);
    }

    public static void closeMySQL(){
        ds.close();
    }
}
