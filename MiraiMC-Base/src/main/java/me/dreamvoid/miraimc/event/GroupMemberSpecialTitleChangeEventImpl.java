package me.dreamvoid.miraimc.event;

/**
 * 群成员 - 名片和头衔 - 成员群头衔改动
 */
public interface GroupMemberSpecialTitleChangeEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取群成员更改之前的特殊头衔
     * @return 群头衔
     */
    String getOldNick();

    /**
     * 获取群成员更改之后的特殊头衔
     * @return 群头衔
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
     * 获取操作管理员QQ号
     * 如果不存在，返回0
     * @return QQ号
     */
    long getOperatorID();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
