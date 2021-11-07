package me.dreamvoid.miraimc.event;

/**
 * 群成员 - 成员列表变更 - 成员已经离开群
 */
public interface GroupMemberLeaveEventImpl {
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
     * 获取退出群的成员QQ
     * @return 成员QQ
     */
    long getTargetID();
    /**
     * 返回操作管理员的QQ。
     * 如果成员为主动退群，则返回 0
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
