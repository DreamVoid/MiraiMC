package me.dreamvoid.miraimc.event;

/**
 * 群设置 - 匿名聊天状态改变
 */
public interface GroupAllowAnonymousChatEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取哈希值
     * @return 哈希值
     */
    int getHashCode();

    /**
     * 获取群号
     * @return 群号
     */
    long getGroupID();

    /**
     * 获取操作管理员QQ号
     * 如果不存在，返回0
     * @return QQ号
     */
    long getOperatorID();

    /**
     * 获取群当前是否允许匿名聊天
     * @return 是否允许匿名聊天
     */
    boolean isAllowAnonymousChat();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
