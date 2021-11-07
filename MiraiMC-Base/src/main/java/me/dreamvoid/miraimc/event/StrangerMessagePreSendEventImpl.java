package me.dreamvoid.miraimc.event;

/**
 * 主动发送消息前 - 陌生人消息
 */
public interface StrangerMessagePreSendEventImpl {
    /**
     * 返回发送这条信息的机器人ID
     * @return 机器人ID
     */
    long getBotID();

    /**
     * 返回接收这条信息的目标QQ
     * @return 目标ID
     */
    long getTargetID();

    /**
     * 返回接收这条信息的目标昵称
     * @return 目标昵称
     */
    String getTargetNick();

    /**
     * 返回接收者的备注名
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
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}