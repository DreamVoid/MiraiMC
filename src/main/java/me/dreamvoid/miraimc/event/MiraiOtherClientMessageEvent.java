package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.OtherClientMessageEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class MiraiOtherClientMessageEvent extends Event {

    public MiraiOtherClientMessageEvent(OtherClientMessageEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final OtherClientMessageEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 返回接收到这条信息的机器人ID
     * @return 机器人ID
     */
    public long getBotID(){
        return event.getBot().getId();
    }

    /**
     * 返回发送这条信息的发送者名称
     * @return 发送者名称
     */
    public String getSenderNick(){
        return event.getSenderName();
    }

    /**
     * 返回接收到的消息内容
     * @return 消息内容
     */
    public String getMessage(){
        return event.getMessage().serializeToMiraiCode();
    }

    /**
     * 返回接收到这条信息的时间
     * @return 发送时间
     */
    public int getTime(){ return event.getTime(); }

    /**
     * (?)获取发送设备的种类
     * @return 客户端种类
     */
    public String getDeviceKind(){
        return event.getClient().getInfo().getDeviceKind();
    }

    /**
     * (?)获取发送设备的名称
     * @return 设备名称
     */
    public String getDeviceName(){
        return event.getClient().getInfo().getDeviceName();
    }

}
