package me.dreamvoid.miraimc.event;

/**
 * 消息撤回 - 好友撤回
 */
public interface FriendMessageRecallEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取被撤回信息的发送者昵称
     * @return 发送者昵称
     */
    String getSenderNick();

    /**
     * 获取被撤回信息的发送者ID
     * @return 发送者ID
     */
    long getSenderID();

    /**
     * 获取撤回信息的操作者昵称
     * @return 操作者昵称
     */
    String getOperatorNick();

    /**
     * 获取撤回信息的操作者ID
     * @return 操作者ID
     */
    long getOperatorID();

    /**
     * 获取信息发送时间
     * @return 发送时间
     */
    long getMessageTime();

    /**
     * (?)获取信息编号
     * @return 信息编号
     */
    int[] getMessageIds();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
