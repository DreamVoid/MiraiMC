package me.dreamvoid.miraimc.event;

/**
 * Bot 离线
 */
public interface BotOfflineEventImpl {
    /**
     * 重新建立连接
     * @return 成功返回true，失败返回false
     */
    boolean reconnect();

    /**
     * 关闭机器人线程
     * [!]
     */
    void close();

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getID();

    /**
     * 获取机器人昵称
     * @return 机器人昵称
     */
    String getNick();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
