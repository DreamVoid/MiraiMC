package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.bungee.BungeePlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Utils {
    public static Logger logger;

    public static Connection connection;

    public Utils(BukkitPlugin plugin){
        logger = plugin.getLogger();
    }
    public Utils(BungeePlugin bungee){
        logger = bungee.getLogger();
    }

    public static void initializeSQLite() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + new File(Config.PluginDir,"database.db").getPath());
    }

    public static void closeSQLite() throws SQLException {
        connection.close();
    }
}
