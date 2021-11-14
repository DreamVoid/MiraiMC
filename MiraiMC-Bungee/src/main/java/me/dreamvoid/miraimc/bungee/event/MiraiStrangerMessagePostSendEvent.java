package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.StrangerMessagePostSendEvent;
import net.mamoe.mirai.message.data.QuoteReply;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * 主动发送消息后 - 陌生人消息
 */
public class MiraiStrangerMessagePostSendEvent extends Event {

    public MiraiStrangerMessagePostSendEvent(StrangerMessagePostSendEvent event) {
        this.event = event;
    }

    private final StrangerMessagePostSendEvent event;

    /**
     * 返回发送这条信息的机器人ID
     * @return 机器人ID
     */
    public long getBotID(){
        return event.getBot().getId();
    }

    /**
     * 返回接收这条信息的目标QQ
     * @return 目标ID
     */
    public long getTargetID(){
        return event.getTarget().getId();
    }

    /**
     * 返回接收这条信息的目标昵称
     * @return 目标昵称
     */
    public String getTargetNick(){
        return event.getTarget().getNick();
    }

    /**
     * 返回接收者的备注名
     * @return 备注名
     */
    public String getFriendRemark(){ return event.getTarget().getRemark(); }

    /**
     * 返回接收到的消息内容转换到字符串的结果<br>
     * 此方法使用 contentToString()<br>
     * QQ 对话框中以纯文本方式会显示的消息内容，这适用于MC与QQ的消息互通等不方便展示原始内容的场景。<br>
     * 无法用纯文字表示的消息会丢失信息，如任何图片都是 [图片]
     * @return 转换字符串后的消息内容
     */
    public String getMessage(){
        return event.getMessage().contentToString();
    }

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
    public String getMessageContent(){
        return event.getMessage().contentToString();
    }

    /**
     * 返回接收到的消息内容<br>
     * 此方法使用 toString()<br>
     * Java 对象的 toString()，会尽可能包含多的信息用于调试作用，行为可能不确定<br>
     * 如需处理常规消息内容，请使用 {@link #getMessageContent()}
     * @return 原始消息内容
     */
    public String getMessageToString(){
        return event.getMessage().toString();
    }

    /**
     * 返回接收到的消息内容转换到Mirai Code的结果<br>
     * 此方法使用 serializeToMiraiCode()<br>
     * 转换为对应的 Mirai 码，消息的一种序列化方式
     * @return 带Mirai Code的消息内容
     */
    public String getMessageToMiraiCode(){
        return event.getMessage().serializeToMiraiCode();
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    /**
     * 返回被回复的消息的发送者
     * @return QQ号
     */
    public long getQuoteReplySenderID() {
        QuoteReply quoteReply = event.getMessage().get(QuoteReply.Key);
        return !Objects.isNull(quoteReply) ? quoteReply.getSource().getFromId() : 0;
    }

    /**
     * 返回被回复的消息内容转换到字符串的结果，如果不存在回复消息，返回null<br>
     * 此方法使用 contentToString()<br>
     * QQ 对话框中以纯文本方式会显示的消息内容，这适用于MC与QQ的消息互通等不方便展示原始内容的场景。<br>
     * 无法用纯文字表示的消息会丢失信息，如任何图片都是 [图片]
     * @return 被回复的转换字符串后的消息内容
     */
    @Nullable
    public String getQuoteReplyMessage() {
        QuoteReply quoteReply = event.getMessage().get(QuoteReply.Key);
        return !Objects.isNull(quoteReply) ? quoteReply.getSource().getOriginalMessage().contentToString() : null;
    }

    /**
     * 返回被回复的消息内容转换到字符串的结果，如果不存在回复消息，返回null<br>
     * 此方法使用 toString()<br>
     * Java 对象的 toString()，会尽可能包含多的信息用于调试作用，行为可能不确定<br>
     * 如需处理常规消息内容，请使用 {@link #getQuoteReplyMessage()}
     * @return 原始消息内容
     */
    @Nullable
    public String getQuoteReplyMessageToString() {
        QuoteReply quoteReply = event.getMessage().get(QuoteReply.Key);
        return !Objects.isNull(quoteReply) ? quoteReply.getSource().getOriginalMessage().toString() : null;
    }

    /**
     * 返回被回复的消息内容转换到字符串的结果，如果不存在回复消息，返回null<br>
     * 此方法使用 contentToString()<br>
     * QQ 对话框中以纯文本方式会显示的消息内容，这适用于MC与QQ的消息互通等不方便展示原始内容的场景。<br>
     * 无法用纯文字表示的消息会丢失信息，如任何图片都是 [图片]
     * @return 被回复的转换字符串后的消息内容
     * @see #getQuoteReplyMessage()
     */
    @Nullable
    public String getQuoteReplyMessageContent() {
        QuoteReply quoteReply = event.getMessage().get(QuoteReply.Key);
        return !Objects.isNull(quoteReply) ? quoteReply.getSource().getOriginalMessage().toString() : null;
    }

    /**
     * 返回被回复的消息内容转换到字符串的结果，如果不存在回复消息，返回null<br>
     * 此方法使用 serializeToMiraiCode()<br>
     * 转换为对应的 Mirai 码，消息的一种序列化方式
     * @return 带Mirai Code的消息内容
     */
    @Nullable
    public String getQuoteReplyMessageToMiraiCode() {
        QuoteReply quoteReply = event.getMessage().get(QuoteReply.Key);
        return !Objects.isNull(quoteReply) ? quoteReply.getSource().getOriginalMessage().serializeToMiraiCode() : null;
    }
}
