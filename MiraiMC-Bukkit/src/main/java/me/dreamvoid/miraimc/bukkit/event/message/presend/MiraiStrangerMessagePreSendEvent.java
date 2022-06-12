package me.dreamvoid.miraimc.bukkit.event.message.presend;

import net.mamoe.mirai.event.events.StrangerMessagePreSendEvent;

/**
 * 陌生人消息
 */
public class MiraiStrangerMessagePreSendEvent extends AbstractMessagePreSendEvent {
    public MiraiStrangerMessagePreSendEvent(StrangerMessagePreSendEvent event) {
        super(event);
        this.event = event;
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