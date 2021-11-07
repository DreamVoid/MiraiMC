package me.dreamvoid.miraimc.event;

/**
 * 群成员 - 成员权限 - 成员权限改变
 */
public interface GroupMemberPermissionChangeEventImpl {
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
     * 返回成员的原有权限。
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    int getOriginPermission();

    /**
     * 返回成员的新权限。
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    int getNewPermission();

    /**
     * 获取被操作成员QQ号
     * @return QQ号
     */
    long getMemberID();

    /**
     * 获取哈希值
     * @return 哈希值
     */
    int getHashCode();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
