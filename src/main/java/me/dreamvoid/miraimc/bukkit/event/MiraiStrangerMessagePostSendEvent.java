package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.StrangerMessagePostSendEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 主动发送消息后 - 陌生人消息
 */
public class MiraiStrangerMessagePostSendEvent extends Event {

    public MiraiStrangerMessagePostSendEvent(StrangerMessagePostSendEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final StrangerMessagePostSendEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

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
     * 返回接收到的消息内容<br>
     * 此方法使用 toString()<br>
     * Java 对象的 toString()，会尽可能包含多的信息用于调试作用，行为可能不确定
     * @return 原始消息内容
     */
    public String getMessage(){
        return event.getMessage().toString();
    }

    /**
     * 返回接收到的消息内容转换到字符串的结果<br>
     * 此方法使用 serializeToMiraiCode()<br>
     * QQ 对话框中以纯文本方式会显示的消息内容。无法用纯文字表示的消息会丢失信息，如任何图片都是 [图片]
     * @return 转换字符串后的消息内容
     */
    public String getMessageContent(){
        return event.getMessage().contentToString();
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

}
