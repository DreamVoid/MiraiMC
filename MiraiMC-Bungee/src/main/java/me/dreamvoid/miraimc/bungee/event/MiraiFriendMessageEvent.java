package me.dreamvoid.miraimc.bungee.event;

import me.dreamvoid.miraimc.internal.httpapi.type.Message;
import net.mamoe.mirai.event.events.FriendMessageEvent;

/**
 * 被动收到消息 - 好友消息
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.message.passive.MiraiFriendMessageEvent
 */
@Deprecated
public final class MiraiFriendMessageEvent extends me.dreamvoid.miraimc.bungee.event.message.passive.MiraiFriendMessageEvent {
    public MiraiFriendMessageEvent(FriendMessageEvent event) {
        super(event);
    }

    public MiraiFriendMessageEvent(long BotAccount, Message message) {
        super(BotAccount, message);
    }
}
