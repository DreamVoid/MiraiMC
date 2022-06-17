package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.FriendMessagePostSendEvent;

/**
 * 主动发送消息后 - 好友消息
 * @deprecated
 * @see me.dreamvoid.miraimc.velocity.event.message.postsend.MiraiFriendMessagePostSendEvent
 */
@Deprecated
public class MiraiFriendMessagePostSendEvent extends me.dreamvoid.miraimc.velocity.event.message.postsend.MiraiFriendMessagePostSendEvent {
    public MiraiFriendMessagePostSendEvent(FriendMessagePostSendEvent event) {
        super(event);
    }
}
