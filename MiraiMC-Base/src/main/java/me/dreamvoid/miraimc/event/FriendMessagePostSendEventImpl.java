package me.dreamvoid.miraimc.event;

/**
 * 主动发送消息后 - 好友消息
 */
public interface FriendMessagePostSendEventImpl {
    /**
     * 返回发送这条信息的机器人ID
     * @return 机器人ID
     */
    long getBotID();

    /**
     * 返回目标好友的QQ号
     * @return 好友QQ号
     */
    long getFriendID();

    /**
     * 返回目标好友的昵称
     * @return 昵称
     */
    String getFriendNickName();

    /**
     * 返回目标好友的备注名
     * @return 备注名
     */
    String getFriendRemark();

    /**
     * 返回接收到的消息内容转换到字符串的结果<br>
     * 此方法使用 contentToString()<br>
     * QQ 对话框中以纯文本方式会显示的消息内容，这适用于MC与QQ的消息互通等不方便展示原始内容的场景。<br>
     * 无法用纯文字表示的消息会丢失信息，如任何图片都是 [图片]
     * @return 转换字符串后的消息内容
     */
    String getMessage();

    /**
     * 返回接收到的消息内容转换到字符串的结果<br>
     * 此方法使用 contentToString()<br>
     * QQ 对话框中以纯文本方式会显示的消息内容，这适用于MC与QQ的消息互通等不方便展示原始内容的场景。<br>
     * 无法用纯文字表示的消息会丢失信息，如任何图片都是 [图片]
     * @return 转换字符串后的消息内容
     * @see #getMessage() 
     * @deprecated 
     */
    @Deprecated
    String getMessageContent();

    /**
     * 返回接收到的消息内容<br>
     * 此方法使用 toString()<br>
     * Java 对象的 toString()，会尽可能包含多的信息用于调试作用，行为可能不确定<br>
     * 如需处理常规消息内容，请使用;
     * @return 原始消息内容
     */
    String getMessageToString();

    /**
     * 返回接收到的消息内容转换到Mirai Code的结果<br>
     * 此方法使用 serializeToMiraiCode()<br>
     * 转换为对应的 Mirai 码，消息的一种序列化方式
     * @return 带Mirai Code的消息内容
     */
    String getMessageToMiraiCode();

    /**
     * 撤回这条消息
     */
    void recall();

    /**
     * 等待指定时间后撤回这条消息<br>
     * 此方法执行异步(Async)任务
     * @param delayTime 延迟时间（毫秒）
     */
    void recall(long delayTime);

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}