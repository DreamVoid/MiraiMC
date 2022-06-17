package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.FriendMessagePreSendEvent;

/**
 * 主动发送消息前 - 好友消息
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.message.presend.MiraiFriendMessagePreSendEvent
 */
@Deprecated
public class MiraiFriendMessagePreSendEvent extends me.dreamvoid.miraimc.nukkit.event.message.presend.MiraiFriendMessagePreSendEvent {
    public MiraiFriendMessagePreSendEvent(FriendMessagePreSendEvent event) {
        super(event);
    }
}
