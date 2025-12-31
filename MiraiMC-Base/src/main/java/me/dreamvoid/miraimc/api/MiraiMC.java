package me.dreamvoid.miraimc.api;

import me.dreamvoid.miraimc.LifeCycle;
import me.dreamvoid.miraimc.interfaces.Platform;
import me.dreamvoid.miraimc.interfaces.PluginConfig;

import org.jetbrains.annotations.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static me.dreamvoid.miraimc.internal.database.DatabaseHandler.getDatabase;

/**
 * MiraiMC 插件接口
 */
@SuppressWarnings("unused")
public class MiraiMC {
    /**
     * 获取 MiraiMC 用于适配不同平台的桥接接口<br>
     * 开发者可利用此接口为 MiraiMC 强关联的功能兼容不同的平台<br>
     * 在 Paper 和 Folia 中非常有用
     *
     * @return MiraiMC 桥接接口
     */
    public static Platform getPlatform() {
        return LifeCycle.getPlatform();
    }

    /**
     * 获取 MiraiMC 的配置
     * @return MiraiMC 配置
     */
    public static PluginConfig getConfig() {
        return LifeCycle.getPlatform().getPluginConfig();
    }

    /**
     * MiraiMC 绑定管理
     */
    public static class Bind {
        private static final String prefix = getConfig().Database_Settings_Prefix;

        static {
            try (Statement stmt = getDatabase().getConnection().createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "bind (uuid TINYTEXT NOT NULL, qqid long NOT NULL)");
            } catch (SQLException e) {
                throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
            }
        }

        /**
         * 添加一个MC-QQ绑定
         * 如果数据库已经有相关数据，将会直接替换
         *
         * @param uuid    玩家UUID
         * @param account 玩家QQ号
         */
        public static void addBind(UUID uuid, long account) {
            try (java.sql.Connection conn = getDatabase().getConnection()) {
                try(PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + prefix + "bind WHERE uuid=? LIMIT 1");
                    PreparedStatement pstmt1 = conn.prepareStatement("SELECT * FROM " + prefix + "bind WHERE qqid=? LIMIT 1")){
                    pstmt.setString(1, uuid.toString());
                    pstmt1.setLong(1, account);

                    try (ResultSet resultSetUUID = pstmt.executeQuery();
                         ResultSet resultSetAccount = pstmt1.executeQuery()) {
                        if (!resultSetUUID.isBeforeFirst() && resultSetAccount.isBeforeFirst()) {
                            try (PreparedStatement pstmt3 = conn.prepareStatement("UPDATE " + prefix + "bind SET uuid=? WHERE qqid=?")) {
                                pstmt3.setString(1, uuid.toString());
                                pstmt3.setLong(2, account);
                                pstmt3.executeUpdate();
                            }
                        } else if (resultSetUUID.isBeforeFirst() && !resultSetAccount.isBeforeFirst()) {
                            try (PreparedStatement pstmt3 = conn.prepareStatement("UPDATE " + prefix + "bind SET qqid=? WHERE uuid=?")) {
                                pstmt3.setLong(1, account);
                                pstmt3.setString(2, uuid.toString());
                                pstmt3.executeUpdate();
                            }
                        } else if (!resultSetUUID.isBeforeFirst() && !resultSetAccount.isBeforeFirst()) {
                            try (PreparedStatement pstmt3 = conn.prepareStatement("INSERT INTO " + prefix + "bind VALUES(?,?)")) {
                                pstmt3.setString(1, uuid.toString());
                                pstmt3.setLong(2, account);
                                pstmt3.executeUpdate();
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
            }
        }

        /**
         * 移除一个Minecraft账号绑定的QQ
         *
         * @param uuid 玩家UUID
         */
        public static void removeBind(UUID uuid) {
            try (java.sql.Connection conn = getDatabase().getConnection()) {
                try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + prefix + "bind WHERE uuid=? LIMIT 1")) {
                    pstmt.setString(1, uuid.toString());
                    try (ResultSet resultSet = pstmt.executeQuery()) {
                        if (resultSet.next()) {
                            try (PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM " + prefix + "bind WHERE uuid=?")) {
                                pstmt1.setString(1, uuid.toString());
                                pstmt1.executeUpdate();
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
            }
        }

        /**
         * 移除一个QQ账号绑定的玩家
         *
         * @param account 玩家QQ号
         */
        public static void removeBind(long account) {
            try (java.sql.Connection conn = getDatabase().getConnection()) {
                try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + prefix + "bind WHERE qqid=? LIMIT 1")) {
                    pstmt.setLong(1, account);
                    try (ResultSet resultSet = pstmt.executeQuery()) {
                        if (resultSet.next()) {
                            try (PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM " + prefix + "bind WHERE qqid=?")) {
                                pstmt1.setLong(1, account);
                                pstmt1.executeUpdate();
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
            }
        }

        /**
         * 获取Minecraft账号绑定的QQ号
         * 如果不存在，返回0
         *
         * @param uuid 玩家UUID
         * @return QQ号
         */
        public static long getBind(UUID uuid) {
            try(java.sql.Connection conn = getDatabase().getConnection();
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + prefix + "bind WHERE uuid=? LIMIT 1")){
                pstmt.setString(1, String.valueOf(uuid));
                try(ResultSet resultSet = pstmt.executeQuery()){
                    return resultSet.next() ? resultSet.getLong("qqid") : 0L;
                }
            } catch (SQLException e) {
                throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
            }
        }

        /**
         * 获取QQ号绑定的Minecraft账号
         * 此方法返回数据库记录的UUID
         * 如果不存在，返回null
         *
         * @param account 玩家QQ号
         * @return UUID
         */
        @Nullable
        public static UUID getBind(long account) {
            try(java.sql.Connection conn = getDatabase().getConnection();
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + prefix + "bind WHERE qqid=? LIMIT 1")){
                pstmt.setLong(1, account);
                try(ResultSet resultSet = pstmt.executeQuery()){
                    return resultSet.next() ? UUID.fromString(resultSet.getString("uuid")) : null;
                }
            } catch (SQLException e) {
                throw new RuntimeException("处理数据时出现异常，请检查MiraiMC数据库配置是否正确", e);
            }
        }
    }

    /**
     * 添加一个MC-QQ绑定
     * 如果数据库已经有相关数据，将会直接替换
     *
     * @param uuid    玩家UUID
     * @param account 玩家QQ号
     * @deprecated
     */
    @Deprecated
    public static void addBind(UUID uuid, long account) {
        getPlatform().getPluginLogger().warning("正在调用一个弃用的 MiraiMC 方法，请通知开发者尽快更新插件以避免未来出现问题！");
        Bind.addBind(uuid,account);
    }

    /**
     * 移除一个Minecraft账号绑定的QQ
     *
     * @param uuid 玩家UUID
     * @deprecated
     */
    @Deprecated
    public static void removeBind(UUID uuid) {
        getPlatform().getPluginLogger().warning("正在调用一个弃用的 MiraiMC 方法，请通知开发者尽快更新插件以避免未来出现问题！");
        Bind.removeBind(uuid);
    }

    /**
     * 移除一个QQ账号绑定的玩家
     *
     * @param account 玩家QQ号
     * @deprecated
     */
    @Deprecated
    public static void removeBind(long account) {
        getPlatform().getPluginLogger().warning("正在调用一个弃用的 MiraiMC 方法，请通知开发者尽快更新插件以避免未来出现问题！");
        Bind.removeBind(account);
    }

    /**
     * 获取Minecraft账号绑定的QQ号
     * 如果不存在，返回0
     *
     * @param uuid 玩家UUID
     * @return QQ号
     * @deprecated
     */
    @Deprecated
    public static long getBind(UUID uuid) {
        getPlatform().getPluginLogger().warning("正在调用一个弃用的 MiraiMC 方法，请通知开发者尽快更新插件以避免未来出现问题！");
        return Bind.getBind(uuid);
    }

    /**
     * 获取QQ号绑定的Minecraft账号
     * 此方法返回数据库记录的UUID
     * 如果不存在，返回null
     *
     * @param account 玩家QQ号
     * @return UUID
     * @deprecated
     */
    @Deprecated
    @Nullable
    public static UUID getBind(long account) {
        getPlatform().getPluginLogger().warning("正在调用一个弃用的 MiraiMC 方法，请通知开发者尽快更新插件以避免未来出现问题！");
        return Bind.getBind(account);
    }
}
