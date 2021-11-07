package me.dreamvoid.miraimc.event;

/**
 * 好友已被删除
 */
public interface FriendDeleteEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取好友昵称
     * @return 昵称
     */
    String getFriendNick();

    /**
     * (?) 获取好友昵称
     * @return 昵称
     */
    String getFriendRemark();

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
