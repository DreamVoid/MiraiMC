package me.dreamvoid.miraimc.velocity.event.message.presend;

import me.dreamvoid.miraimc.velocity.VelocityPlugin;
import net.mamoe.mirai.event.events.StrangerMessagePreSendEvent;

/**
 * (bungee) Mirai 核心事件 - 消息 - 主动发送消息前 - 陌生人消息
 */
public class MiraiStrangerMessagePreSendEvent extends AbstractMessagePreSendEvent {
    public MiraiStrangerMessagePreSendEvent(StrangerMessagePreSendEvent event) {
        super(event);
        this.event = event;

        VelocityPlugin.INSTANCE.getServer().getEventManager().fire(new me.dreamvoid.miraimc.velocity.event.MiraiStrangerMessagePreSendEvent(event));
    }

    private final StrangerMessagePreSendEvent event;

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
}