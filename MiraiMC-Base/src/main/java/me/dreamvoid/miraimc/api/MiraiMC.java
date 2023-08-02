package me.dreamvoid.miraimc.api;

import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.database.DatabaseManager;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * MiraiMC 插件接口
 */
@SuppressWarnings("unused")
public class MiraiMC {
    /**
     * 添加一个MC-QQ绑定
     * 如果数据库已经有相关数据，将会直接替换
     * @param uuid 玩家UUID
     * @param account 玩家QQ号
     * @deprecated 请使用 {@link #addBind(UUID, long)}
     */
    @Deprecated
    public static void addBinding(String uuid, long account) {
        String createTable = "CREATE TABLE IF NOT EXISTS miraimc_binding (uuid TINYTEXT NOT NULL, qqid long NOT NULL);";
        String selectUUID = "SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1";
        String selectAccount = "SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1";
        String updateUUID = "UPDATE miraimc_binding SET uuid='" + uuid + "' WHERE qqid=" + account + ";";
        String updateAccount = "UPDATE miraimc_binding SET qqid=" + account + " WHERE uuid='" + uuid + "';";
        String insert = "insert into miraimc_binding values('" + uuid + "', " + account + ");";

        try (Connection connection = DatabaseManager.getDatabase().getConnection()){
            connection.prepareStatement(createTable).executeUpdate();

            ResultSet resultSetUUID = connection.prepareStatement(selectUUID).executeQuery();
            ResultSet resultSetAccount = connection.prepareStatement(selectAccount).executeQuery();

            if (!resultSetUUID.isBeforeFirst() && resultSetAccount.isBeforeFirst()) {
                connection.prepareStatement(updateUUID).executeUpdate();
            } else if (resultSetUUID.isBeforeFirst() && !resultSetAccount.isBeforeFirst()) {
                connection.prepareStatement(updateAccount).executeUpdate();
            } else if (!resultSetUUID.isBeforeFirst() && !resultSetAccount.isBeforeFirst()) {
                connection.prepareStatement(insert).executeUpdate();
            }

            resultSetUUID.close();
            resultSetAccount.close();
        } catch (SQLException e){
            Utils.logger.warning("处理数据库数据时出现异常，原因: " + e);
        }
    }
    /**
     * 添加一个MC-QQ绑定
     * 如果数据库已经有相关数据，将会直接替换
     * @param uuid 玩家UUID
     * @param account 玩家QQ号
     */
    public static void addBind(UUID uuid, long account) {
        String createTable = "CREATE TABLE IF NOT EXISTS miraimc_binding (uuid TINYTEXT NOT NULL, qqid long NOT NULL);";
        String selectUUID = "SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1";
        String selectAccount = "SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1";
        String updateUUID = "UPDATE miraimc_binding SET uuid='" + uuid + "' WHERE qqid=" + account + ";";
        String updateAccount = "UPDATE miraimc_binding SET qqid=" + account + " WHERE uuid='" + uuid + "';";
        String insert = "insert into miraimc_binding values('" + uuid + "', " + account + ");";

        try (Connection connection = DatabaseManager.getDatabase().getConnection()){
            connection.prepareStatement(createTable).executeUpdate();

            ResultSet resultSetUUID = connection.prepareStatement(selectUUID).executeQuery();
            ResultSet resultSetAccount = connection.prepareStatement(selectAccount).executeQuery();

            if (!resultSetUUID.isBeforeFirst() && resultSetAccount.isBeforeFirst()) {
                connection.prepareStatement(updateUUID).executeUpdate();
            } else if (resultSetUUID.isBeforeFirst() && !resultSetAccount.isBeforeFirst()) {
                connection.prepareStatement(updateAccount).executeUpdate();
            } else if (!resultSetUUID.isBeforeFirst() && !resultSetAccount.isBeforeFirst()) {
                connection.prepareStatement(insert).executeUpdate();
            }

            resultSetUUID.close();
            resultSetAccount.close();
        } catch (SQLException e){
            Utils.logger.warning("处理数据库数据时出现异常，原因: " + e);
        }
    }

    /**
     * 移除一个Minecraft账号绑定的QQ
     * @param uuid 玩家UUID
     * @deprecated 请使用 {@link #removeBind(UUID)}
     */
    @Deprecated
    public static void removeBinding(String uuid) {
        String createTable = "CREATE TABLE IF NOT EXISTS miraimc_binding (uuid TINYTEXT NOT NULL, qqid long NOT NULL);";
        String select = "SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1;";
        String delete = "DELETE FROM miraimc_binding WHERE uuid='" + uuid + "';";

        try (Connection connection = DatabaseManager.getDatabase().getConnection()){
            connection.prepareStatement(createTable).executeUpdate();

            ResultSet resultSet = connection.prepareStatement(select).executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                connection.prepareStatement(delete).executeUpdate();
            }
            resultSet.close();
        } catch (SQLException e) {
            Utils.logger.warning("处理数据库数据时出现异常，原因: " + e);
        }
    }
    /**
     * 移除一个Minecraft账号绑定的QQ
     * @param uuid 玩家UUID
     */
    public static void removeBind(UUID uuid) {
        String createTable = "CREATE TABLE IF NOT EXISTS miraimc_binding (uuid TINYTEXT NOT NULL, qqid long NOT NULL);";
        String select = "SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1;";
        String delete = "DELETE FROM miraimc_binding WHERE uuid='" + uuid + "';";

        try (Connection connection = DatabaseManager.getDatabase().getConnection()){
            connection.prepareStatement(createTable).executeUpdate();

            ResultSet resultSet = connection.prepareStatement(select).executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                connection.prepareStatement(delete).executeUpdate();
            }
            resultSet.close();
        } catch (SQLException e) {
            Utils.logger.warning("处理MySQL数据时出现异常，原因: " + e);
        }
    }

    /**
     * 移除一个QQ账号绑定的玩家
     * @param account 玩家QQ号
     */
    public static void removeBind(long account) {
        String createTable = "CREATE TABLE IF NOT EXISTS miraimc_binding (uuid TINYTEXT NOT NULL, qqid long NOT NULL);";
        String select = "SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1;";
        String delete = "DELETE FROM miraimc_binding WHERE qqid=" + account+";";

        try (Connection connection = DatabaseManager.getDatabase().getConnection()){
            connection.prepareStatement(createTable).executeUpdate();

            ResultSet resultSet = connection.prepareStatement(select).executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                connection.prepareStatement(delete).executeUpdate();
            }
            resultSet.close();

        } catch (SQLException e) {
            Utils.logger.warning("处理MySQL数据时出现异常，原因: " + e);
        }
    }

    /**
     * 获取Minecraft账号绑定的QQ号
     * 如果不存在，返回0
     * @param uuid 玩家UUID
     * @return QQ号
     * @deprecated 请使用 {@link #getBind(UUID)}
     */
    @Deprecated
    public static long getBinding(String uuid){
        long account = 0L;

        String createTable = "CREATE TABLE IF NOT EXISTS miraimc_binding (uuid TINYTEXT NOT NULL, qqid long NOT NULL);";
        String select = "SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1;";

        try (Connection connection = DatabaseManager.getDatabase().getConnection()){
            connection.prepareStatement(createTable).executeUpdate();

            ResultSet resultSet = connection.prepareStatement(select).executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                account = resultSet.getLong("qqid");
            }
            resultSet.close();

        } catch (SQLException e) {
            Utils.logger.warning("处理数据库数据时出现异常，原因: " + e);
        }
        return account;
    }

    /**
     * 获取Minecraft账号绑定的QQ号
     * 如果不存在，返回0
     * @param uuid 玩家UUID
     * @return QQ号
     */
    public static long getBind(UUID uuid){
        long account = 0L;

        String createTable = "CREATE TABLE IF NOT EXISTS miraimc_binding (uuid TINYTEXT NOT NULL, qqid long NOT NULL);";
        String select = "SELECT * FROM miraimc_binding WHERE uuid='" + uuid + "' LIMIT 1;";

        try (Connection connection = DatabaseManager.getDatabase().getConnection()){
            connection.prepareStatement(createTable).executeUpdate();

            ResultSet resultSet = connection.prepareStatement(select).executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                account = resultSet.getLong("qqid");
            }
            resultSet.close();
        } catch (SQLException e) {
            Utils.logger.warning("处理数据库数据时出现异常，原因: " + e);
        }
        return account;
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
        UUID uuid = null;

        String createTable = "CREATE TABLE IF NOT EXISTS miraimc_binding (uuid TINYTEXT NOT NULL, qqid long NOT NULL);";
        String select = "SELECT * FROM miraimc_binding WHERE qqid=" + account + " LIMIT 1;";

        try (Connection connection = DatabaseManager.getDatabase().getConnection()){
            connection.prepareStatement(createTable).executeUpdate();

            ResultSet resultSet = connection.prepareStatement(select).executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                uuid = UUID.fromString(resultSet.getString("uuid"));
            }
            resultSet.close();
        } catch (SQLException e) {
            Utils.logger.warning("处理MySQL数据时出现异常，原因: " + e);
        }
        return uuid;
    }
}
