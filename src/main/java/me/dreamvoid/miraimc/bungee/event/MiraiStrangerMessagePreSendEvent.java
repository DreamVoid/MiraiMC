package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.StrangerMessagePreSendEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * 主动发送消息前 - 陌生人消息
 */
public class MiraiStrangerMessagePreSendEvent extends Event {

    public MiraiStrangerMessagePreSendEvent(StrangerMessagePreSendEvent event) {
        this.event = event;
    }

    private final StrangerMessagePreSendEvent event;

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
