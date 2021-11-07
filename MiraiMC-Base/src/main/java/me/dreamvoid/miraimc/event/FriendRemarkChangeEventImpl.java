package me.dreamvoid.miraimc.event;

/**
 * 好友昵称改变
 */
public interface FriendRemarkChangeEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取好友更名之前的昵称
     * @return 昵称
     */
    String getOldRemark();

    /**
     * 获取好友更名之后的昵称
     * @return 昵称
     */
    String getNewRemark();

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
