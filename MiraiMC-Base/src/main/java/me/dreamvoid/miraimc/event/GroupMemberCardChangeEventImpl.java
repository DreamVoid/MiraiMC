package me.dreamvoid.miraimc.event;

/**
 * 群成员 - 名片和头衔 - 成员群名片改动
 */
public interface GroupMemberCardChangeEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取群成员更改之前的名片
     * @return 群名片
     */
    String getOldNick();

    /**
     * 获取群成员更改之后的名片
     * @return 群名片
     */
    String getNewNick();

    /**
     * 获取哈希值
     * @return 哈希值
     */
    int getHashCode();

    /**
     * 获取群成员QQ号
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
