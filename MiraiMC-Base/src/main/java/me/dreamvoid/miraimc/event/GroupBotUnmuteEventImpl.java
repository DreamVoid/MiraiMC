package me.dreamvoid.miraimc.event;

/**
 * 机器人被取消禁言
 */
public interface GroupBotUnmuteEventImpl { 
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 返回目标群的群号
     * @return 群号
     */
    long getGroupID();

    /**
     * 返回执行解除禁言操作的管理员。
     * @return 管理员QQ
     */
    long getNewPermission();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
