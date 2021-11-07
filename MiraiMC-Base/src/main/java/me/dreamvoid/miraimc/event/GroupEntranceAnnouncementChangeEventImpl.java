package me.dreamvoid.miraimc.event;

/**
 * 群设置 - 入群公告改变
 */
public interface GroupEntranceAnnouncementChangeEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取群号
     * @return 群号
     */
    long getGroupID();

    /**
     * 获取操作管理员QQ号
     * @return QQ号
     */
    long getOperatorID();

    /**
     * 获取更换前的群公告内容
     * @return 群公告内容
     */
    String getOrigin();

    /**
     * 获取更换后的群公告内容
     * @return 群公告内容
     */
    String getNew();

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
