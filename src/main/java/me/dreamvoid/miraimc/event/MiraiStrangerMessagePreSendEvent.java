package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.StrangerMessagePreSendEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MiraiStrangerMessagePreSendEvent extends Event {

    public MiraiStrangerMessagePreSendEvent(StrangerMessagePreSendEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final StrangerMessagePreSendEvent event;

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
     * 返回将发送的消息内容
     * @return 消息内容
     */
    public String getMessage(){
        return event.getMessage().contentToString();
    }

}
