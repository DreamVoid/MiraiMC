package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.FriendMessagePostSendEvent;

/**
 * 主动发送消息后 - 好友消息
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.message.postsend.MiraiFriendMessagePostSendEvent
 */
@Deprecated
public class MiraiFriendMessagePostSendEvent extends me.dreamvoid.miraimc.nukkit.event.message.postsend.MiraiFriendMessagePostSendEvent {
    public MiraiFriendMessagePostSendEvent(FriendMessagePostSendEvent event) {
        super(event);
    }
}
