package me.dreamvoid.miraimc.event;

/**
 * 被动收到消息 - 其他客户端消息
 */
public interface OtherClientMessageEventImpl {
    /**
     * 返回接收到这条信息的机器人ID
     * @return 机器人ID
     */
    long getBotID();

    /**
     * 返回发送这条信息的发送者名称
     * @return 发送者名称
     */
    String getSenderNick();

    /**
     * 返回接收到的消息内容
     * @return 消息内容
     */
    String getMessage();

    /**
     * 返回接收到这条信息的时间
     * @return 发送时间
     */
    int getTime();

    /**
     * (?)获取发送设备的种类
     * @return 客户端种类
     */
    String getDeviceKind();

    /**
     * (?)获取发送设备的名称
     * @return 设备名称
     */
    String getDeviceName();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
