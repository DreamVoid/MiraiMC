package me.dreamvoid.miraimc.api;

import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * MiraiMC 插件接口
 */
public class MiraiMC {
    /**
     * 添加一个MC-QQ绑定
     * 如果数据库已经有相关数据，将会直接替换
     * @param uuid 玩家UUID
     * @param account 玩家QQ号
     */
    public void addBinding(String uuid, long account) {
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            try {
                Statement statement = Utils.statement;
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                // 如果没有找到记录为false，找到就是true
                ResultSet resultSetUUID = statement.executeQuery("SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1");
                ResultSet resultSetAccount = statement.executeQuery("SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1");

                if (!resultSetUUID.isBeforeFirst() && !resultSetAccount.isBeforeFirst()) {
                    statement.executeUpdate("insert into miraimc_binding values('" + uuid + "', " + account + ")");
                } else {
                    if (!resultSetUUID.isBeforeFirst() && resultSetAccount.isBeforeFirst()) {
                        statement.executeUpdate("UPDATE miraimc_binding SET uuid='" + uuid + "' WHERE qqid=" + account + ";");
                    } else {
                        statement.executeUpdate("UPDATE miraimc_binding SET qqid=" + account + " WHERE uuid='" + uuid + "';");
                    }
                }
                resultSetUUID.close();
                resultSetAccount.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 移除一个MC-QQ绑定
     * @param uuid 玩家UUID
     */
    public void removeBinding(String uuid) {
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            try {
                Statement statement = Utils.statement;
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                // 如果没有找到记录为false，找到就是true
                ResultSet resultSet = statement.executeQuery("SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1");

                if (resultSet.isBeforeFirst()) {
                    statement.executeUpdate("DELETE FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1");
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 移除一个MC-QQ绑定
     * @param account 玩家QQ号
     */
    public void removeBinding(long account) {
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            try {
                Statement statement = Utils.statement;
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                // 如果没有找到记录为false，找到就是true
                ResultSet resultSet = statement.executeQuery("SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1");

                if (resultSet.isBeforeFirst()) {
                    statement.executeUpdate("DELETE FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1");
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取Minecraft账号绑定的QQ号
     * 如果不存在，返回0
     * @param uuid 玩家UUID
     */
    public long getBinding(String uuid){
        long account = 0L;
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            try {
                Statement statement = Utils.statement;
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                // 如果没有找到记录为false，找到就是true
                ResultSet resultSet = statement.executeQuery("SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1");

                if (resultSet.isBeforeFirst()) {
                    account = resultSet.getLong("qqid");
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return account;
    }
    /**
     * 获取QQ号绑定的Minecraft账号
     * 此方法返回数据库记录的UUID
     * 如果不存在，返回空文本
     * @param account 玩家QQ号
     */
    public String getBinding(long account){
        String uuid = "";
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            try {
                Statement statement = Utils.statement;
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                // 如果没有找到记录为false，找到就是true
                ResultSet resultSet = statement.executeQuery("SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1");

                if (resultSet.isBeforeFirst()) {
                    uuid = resultSet.getString("uuid");
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return uuid;
    }
    /**
     * 获取QQ号绑定的Minecraft账号
     * 此方法返回通过UUID获取的玩家名
     * 如果不存在，返回空文本
     * @param account 玩家QQ号
     */
    public String getBindingName(long account){
        String uuid = "";
        if(Config.DB_Type.equalsIgnoreCase("sqlite")) {
            try {
                Statement statement = Utils.statement;
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

                // 如果没有找到记录为false，找到就是true
                ResultSet resultSet = statement.executeQuery("SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1");

                if (resultSet.isBeforeFirst()) {
                    uuid = resultSet.getString("uuid");
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(!(uuid.equalsIgnoreCase(""))){
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid)); // 对于此方法来说，任何玩家都存在.
            return player.getName();
        }
        return "";
    }
}
