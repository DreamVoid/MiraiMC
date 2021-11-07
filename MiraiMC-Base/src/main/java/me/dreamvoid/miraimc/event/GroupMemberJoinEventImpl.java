package me.dreamvoid.miraimc.event;

/**
 * 群成员 - 成员列表变更 - 成员已经加入群
 */
public interface GroupMemberJoinEventImpl {
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
     * 返回新成员的QQ号
     * @return 成员QQ号
     */
    long getNewMemberID();

    /**
     * 返回邀请者的QQ号
     * 如果成员为主动加群，则返回 0
     * @return 邀请者QQ号
     */
    long getInviterID();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
