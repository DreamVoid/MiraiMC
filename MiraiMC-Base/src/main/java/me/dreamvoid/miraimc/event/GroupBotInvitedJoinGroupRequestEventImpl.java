package me.dreamvoid.miraimc.event;

/**
 * 群成员 - 成员列表变更 - 机器人被邀请加入群
 */
public interface GroupBotInvitedJoinGroupRequestEventImpl {
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
     * 返回目标群的群名称
     * @return 群名称
     */
    String getGroupName();

    /**
     * 返回邀请者的昵称
     * @return 邀请者昵称
     */
    String getInviterNick();

    /**
     * 返回邀请者的QQ号
     * @return 邀请者QQ号
     */
    long getInviterID();

    /**
     * 获取事件ID用于处理加群事件
     * @return 事件ID
     */
    long getEventID();

    /**
     * 同意请求
     */
    void setAccept();

    /**
     * 忽略请求
     */
    void setIgnore();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
