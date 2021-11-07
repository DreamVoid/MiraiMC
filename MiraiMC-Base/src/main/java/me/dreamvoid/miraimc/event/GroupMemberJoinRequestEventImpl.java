package me.dreamvoid.miraimc.event;

/**
 * 群成员 - 成员列表变更 - 一个账号请求加入群
 */
public interface GroupMemberJoinRequestEventImpl {
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
     * 返回请求加群的成员的QQ号
     * @return 成员QQ号
     */
    long getRequestMemberID();

    /**
     * 返回邀请者的QQ号
     * 如果没有邀请者，则返回 0
     * @return 邀请者QQ号
     */
    long getInviterID();

    /**
     * 获取事件ID用于处理加群事件
     * @return 事件ID
     */
    long getEventID();

    /**
     * 获取加群时填写的附言
     * @return 附言
     */
    String getMessage();

    /**
     * 同意请求
     */
    void setAccept();

    /**
     * 忽略请求
     * @param setBlacklist 是否拒绝目标再次申请加群
     */
    void setIgnore(boolean setBlacklist);

    /**
     * 拒绝请求
     * @param setBlacklist 是否拒绝目标再次申请加群
     */
    void setDeny(boolean setBlacklist);

    /**
     * 拒绝请求
     */
    void setDeny();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
