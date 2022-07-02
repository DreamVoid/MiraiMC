package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import net.mamoe.mirai.event.events.FriendMessageEvent;

/**
 * 被动收到消息 - 好友消息
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.message.passive.MiraiFriendMessageEvent
 */
@Deprecated
public final class MiraiFriendMessageEvent extends me.dreamvoid.miraimc.nukkit.event.message.passive.MiraiFriendMessageEvent {
    public MiraiFriendMessageEvent(FriendMessageEvent event) {
        super(event);
    }

    public MiraiFriendMessageEvent(long BotAccount, FetchMessage.Data data) {
        super(BotAccount, data);
    }
}
