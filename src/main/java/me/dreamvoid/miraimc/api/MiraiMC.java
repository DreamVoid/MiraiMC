package me.dreamvoid.miraimc.api;

import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * MiraiMC 插件接口
 */
public class MiraiMC {
    private static BukkitPlugin pluginStatic = null;

    /**
     * 初始化 MiraiMC 插件接口
     * @param plugin MiraiMC 插件主类
     */
    public MiraiMC(BukkitPlugin plugin){
        pluginStatic = plugin;
    }
    
    /**
     * 添加一个MC-QQ绑定
     * 如果数据库已经有相关数据，将会直接替换
     * @param uuid 玩家UUID
     * @param account 玩家QQ号
     */
    public static void addBinding(String uuid, long account) {
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        Statement statement = Utils.connection.createStatement();
                        Statement statement1 = Utils.connection.createStatement();
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                        // 如果没有找到记录为false，找到就是true
                        ResultSet resultSetUUID = statement.executeQuery("SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1");
                        ResultSet resultSetAccount = statement1.executeQuery("SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1");

                        if (!resultSetUUID.isBeforeFirst() && resultSetAccount.isBeforeFirst()) {
                            statement.executeUpdate("UPDATE miraimc_binding SET uuid='" + uuid + "' WHERE qqid=" + account + ";");
                        } else if (resultSetUUID.isBeforeFirst() && !resultSetAccount.isBeforeFirst()){
                            statement.executeUpdate("UPDATE miraimc_binding SET qqid=" + account + " WHERE uuid='" + uuid + "';");
                        } else if(!resultSetUUID.isBeforeFirst() && !resultSetAccount.isBeforeFirst()){
                            statement.executeUpdate("insert into miraimc_binding values('" + uuid + "', " + account + ")");
                        }

                        resultSetUUID.close();
                        resultSetAccount.close();
                        statement.close();
                        statement1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(pluginStatic);
        }
    }

    /**
     * 移除一个MC-QQ绑定
     * @param uuid 玩家UUID
     */
    public static void removeBinding(String uuid) {
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        Statement statement = Utils.connection.createStatement();
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                        // 如果没有找到记录为false，找到就是true
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1");

                        if (resultSet.isBeforeFirst()) {
                            statement.executeUpdate("DELETE FROM miraimc_binding WHERE uuid='" + uuid + "'");
                        }
                        resultSet.close();
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(pluginStatic);
        }
    }
    /**
     * 移除一个MC-QQ绑定
     * @param account 玩家QQ号
     */
    public static void removeBinding(long account) {
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        Statement statement = Utils.connection.createStatement();
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                        // 如果没有找到记录为false，找到就是true
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1");

                        if (resultSet.isBeforeFirst()) {
                            statement.executeUpdate("DELETE FROM miraimc_binding WHERE qqid=" + account);
                        }
                        resultSet.close();
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(pluginStatic);
        }
    }

    /**
     * 获取Minecraft账号绑定的QQ号
     * 如果不存在，返回0
     * @param uuid 玩家UUID
     */
    public static long getBinding(String uuid){
        final long[] account = {0L};
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        Statement statement = Utils.connection.createStatement();
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                        // 如果没有找到记录为false，找到就是true
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1");

                        if (resultSet.isBeforeFirst()) {
                            account[0] = resultSet.getLong("qqid");
                        }
                        resultSet.close();
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(pluginStatic);
        }
        return account[0];
    }
    /**
     * 获取QQ号绑定的Minecraft账号
     * 此方法返回数据库记录的UUID
     * 如果不存在，返回空文本
     * @param account 玩家QQ号
     */
    public static String getBinding(long account){
        final String[] uuid = {""};
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        Statement statement = Utils.connection.createStatement();
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                        // 如果没有找到记录为false，找到就是true
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1");

                        if (resultSet.isBeforeFirst()) {
                            uuid[0] = resultSet.getString("uuid");
                        }
                        resultSet.close();
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(pluginStatic);
        }
        return uuid[0];
    }
    /**
     * 获取QQ号绑定的Minecraft账号
     * 此方法返回通过UUID获取的玩家名
     * 如果不存在，返回空文本
     * @param account 玩家QQ号
     */
    public static String getBindingName(long account){
        final String[] uuid = {""};
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        Statement statement = Utils.connection.createStatement();
                        statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                        // 如果没有找到记录为false，找到就是true
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1");

                        if (resultSet.isBeforeFirst()) {
                            uuid[0] = resultSet.getString("uuid");
                        }
                        resultSet.close();
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(pluginStatic);
        }
        if(!(uuid[0].equalsIgnoreCase(""))){
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid[0])); // 对于此方法来说，任何玩家都存在.
            return player.getName();
        }
        return "";
    }
}
