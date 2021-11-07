package me.dreamvoid.miraimc.event;

/**
 * 群成员 - 动作 - 群成员被取消禁言
 */
public interface GroupMemberUnmuteEventImpl {
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
     * 获取被操作群成员QQ号
     * @return QQ号
     */
    long getMemberID();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
