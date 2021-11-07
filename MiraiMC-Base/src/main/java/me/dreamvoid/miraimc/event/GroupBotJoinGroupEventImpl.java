package me.dreamvoid.miraimc.event;

/**
 * 机器人成功加入了一个新群
 */
public interface GroupBotJoinGroupEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 返回加入群的群号
     * @return 群号
     */
    long getGroupID();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
