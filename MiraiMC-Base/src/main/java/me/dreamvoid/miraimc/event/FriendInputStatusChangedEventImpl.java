package me.dreamvoid.miraimc.event;

/**
 * 好友输入状态改变
 */
public interface FriendInputStatusChangedEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取好友QQ号
     * @return QQ号
     */
    long getFriendID();

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
     * 判断好友是否正在输入
     * @return 是否正在输入
     */
    boolean isInputting();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}