package me.dreamvoid.miraimc.event;

/**
 * 一个账号请求添加机器人为好友
 */
public interface NewFriendRequestEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取事件ID
     * @return 事件ID
     */
    long getEventID();

    /**
     * 接受请求
     */
    void setAcceptRequest();

    /**
     * 拒绝请求
     * @param setBlacklist 是否加入黑名单
     */
    void setDenyRequest(boolean setBlacklist);

    /**
     * 获取请求者QQ号
     * @return QQ号
     */
    long getFromID();

    /**
     * 获取请求者昵称
     * @return 昵称
     */
    String getFromNick();

    /**
     * 获取来源群名称
     * 如果不存在来源群，则返回空文本
     * @return 群名称
     */
    String getFromGroupName();

    /**
     * 获取来源群号
     * # TO DO: 如果群号不存在，返回什么？
     * @return 群号
     */
    long getFromGroupID();

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
