package me.dreamvoid.miraimc.event;

/**
 * 戳一戳
 */
public interface NudgeEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取发送者ID
     * @return 发送者ID
     */
    long getFromID();

    /**
     * 获取发送者昵称
     * @return 发送者昵称
     */
    String getFromNick();

    /**
     * 获取接收者ID
     * @return 接收者ID
     */
    long getTargetID();

    /**
     * 获取接收者昵称
     * @return 接收者昵称
     */
    String getTargetNick();

    /**
     * (?)获取操作
     * @return 操作内容
     */
    String getAction();

    /**
     * (?)获取后缀
     * @return 后缀内容
     */
    String getSuffix();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
