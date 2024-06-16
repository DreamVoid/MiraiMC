package me.dreamvoid.miraimc.api;

import me.dreamvoid.miraimc.internal.config.PluginConfig;
import me.dreamvoid.miraimc.internal.database.DatabaseHandler;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * MiraiMC 插件接口
 */
@SuppressWarnings("unused")
public class MiraiMC {
    private static final String prefix = PluginConfig.Database.Settings.Prefix;

    static {
        try {
            DatabaseHandler.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "bind (uuid TINYTEXT NOT NULL, qqid long NOT NULL)");
        } catch (SQLException e) {
            throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
        }
    }

    /**
     * 添加一个MC-QQ绑定
     * 如果数据库已经有相关数据，将会直接替换
     * @param uuid 玩家UUID
     * @param account 玩家QQ号
     */
    public static void addBind(UUID uuid, long account) {
        try(ResultSet resultSetUUID = DatabaseHandler.executeQuery("SELECT * FROM " + prefix + "bind WHERE uuid=? LIMIT 1", uuid);
            ResultSet resultSetAccount = DatabaseHandler.executeQuery("SELECT * FROM " + prefix + "bind WHERE qqid=? LIMIT 1", account)) {

            if (!resultSetUUID.isBeforeFirst() && resultSetAccount.isBeforeFirst()) {
                DatabaseHandler.executeUpdate("UPDATE " + prefix + "bind SET uuid=? WHERE qqid=?", uuid, account);
            } else if (resultSetUUID.isBeforeFirst() && !resultSetAccount.isBeforeFirst()) {
                DatabaseHandler.executeUpdate("UPDATE " + prefix + "bind SET qqid=? WHERE uuid=?", account, uuid);
            } else if (!resultSetUUID.isBeforeFirst() && !resultSetAccount.isBeforeFirst()) {
                DatabaseHandler.executeUpdate("INSERT INTO " + prefix + "bind VALUES(?,?)", uuid, account);
            }
        } catch (SQLException e){
            throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
        }
    }

    /**
     * 移除一个Minecraft账号绑定的QQ
     * @param uuid 玩家UUID
     */
    public static void removeBind(UUID uuid) {
        try (ResultSet resultSet = DatabaseHandler.executeQuery("SELECT * FROM " + prefix + "bind WHERE uuid=? LIMIT 1", uuid)){
            if (resultSet.next()) {
                DatabaseHandler.executeUpdate("DELETE FROM " + prefix + "bind WHERE uuid=?", uuid);
            }
        } catch (SQLException e) {
            throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
        }
    }

    /**
     * 移除一个QQ账号绑定的玩家
     * @param account 玩家QQ号
     */
    public static void removeBind(long account) {
        try (ResultSet resultSet = DatabaseHandler.executeQuery("SELECT * FROM " + prefix + "bind WHERE qqid=? LIMIT 1", account)){
            if (resultSet.next()) {
                DatabaseHandler.executeUpdate("DELETE FROM " + prefix + "bind WHERE qqid=?", account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
        }
    }

    /**
     * 获取Minecraft账号绑定的QQ号
     * 如果不存在，返回0
     * @param uuid 玩家UUID
     * @return QQ号
     */
    public static long getBind(UUID uuid){
        try (ResultSet resultSet = DatabaseHandler.executeQuery("SELECT * FROM " + prefix + "bind WHERE uuid=? LIMIT 1", uuid)){
            return resultSet.next() ? resultSet.getLong("qqid") : 0L;
        } catch (SQLException e) {
            throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
        }
    }

    /**
     * 获取QQ号绑定的Minecraft账号
     * 此方法返回数据库记录的UUID
     * 如果不存在，返回null
     * @param account 玩家QQ号
     * @return UUID
     */
    @Nullable
    public static UUID getBind(long account) {
        try (ResultSet resultSet = DatabaseHandler.executeQuery("SELECT * FROM " + prefix + "bind WHERE qqid=? LIMIT 1", account)){
            return resultSet.next() ? UUID.fromString(resultSet.getString("uuid")) : null;
        } catch (SQLException e) {
            throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
        }
    }
}
