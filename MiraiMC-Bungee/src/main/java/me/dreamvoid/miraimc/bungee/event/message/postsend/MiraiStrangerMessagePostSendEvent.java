package me.dreamvoid.miraimc.bungee.event.message.postsend;

import net.mamoe.mirai.event.events.StrangerMessagePostSendEvent;

/**
 * (BungeeCord) 消息 - 主动发送消息后 - 陌生人消息
 */
public class MiraiStrangerMessagePostSendEvent extends AbstractMessagePostSendEvent{
    public MiraiStrangerMessagePostSendEvent(StrangerMessagePostSendEvent event) {
        super(event);
        this.event = event;
    }

    private final StrangerMessagePostSendEvent event;

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
