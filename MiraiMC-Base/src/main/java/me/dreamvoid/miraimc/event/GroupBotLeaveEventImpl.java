package me.dreamvoid.miraimc.event;

/**
 * 机器人被踢出群或在其他客户端主动退出一个群
 */
public interface GroupBotLeaveEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 返回退出群的群号
     * @return 群号
     */
    long getGroupID();

    /**
     * 返回操作管理员的QQ。
     * 如果机器人为主动退群，则返回 0
     * @return 操作者ID
     */
    long getOperator();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
