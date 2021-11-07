package me.dreamvoid.miraimc.event;

/**
 * 群设置 - 群名改变
 */
public interface GroupNameChangeEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取更换前的名称
     * @return 群名称
     */
    String getGroupOldName();

    /**
     * 获取更换后的名称
     * @return 群名称
     */
    String getGroupNewName();

    /**
     * 获取群号
     * @return 群号
     */
    long getGroupID();

    /**
     * 获取操作管理员QQ号
     * 如果不存在，返回0
     * @return QQ号
     */
    long getOperatorID();

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
