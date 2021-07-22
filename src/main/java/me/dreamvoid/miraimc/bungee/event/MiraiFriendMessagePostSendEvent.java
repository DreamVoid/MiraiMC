package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.FriendMessagePostSendEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * 主动发送消息后 - 好友消息
 */
public class MiraiFriendMessagePostSendEvent extends Event {

    public MiraiFriendMessagePostSendEvent(FriendMessagePostSendEvent event) {
        this.event = event;
    }

    private final FriendMessagePostSendEvent event;

    /**
     * 返回发送这条信息的机器人ID
     * @return 机器人ID
     */
    public long getBotID(){
        return event.getBot().getId();
    }

    /**
     * 返回目标好友的QQ号
     * @return 好友QQ号
     */
    public long getFriendID(){
        return event.getTarget().getId();
    }

    /**
     * 返回目标好友的昵称
     * @return 昵称
     */
    public String getFriendNickName(){ return event.getTarget().getNick(); }

    /**
     * 返回目标好友的备注名
     * @return 备注名
     */
    public String getFriendRemark(){ return event.getTarget().getRemark(); }

    /**
     * 返回将发送的消息内容
     * @return 消息内容
     */
    public String getMessage(){ return event.getMessage().contentToString(); }

}
