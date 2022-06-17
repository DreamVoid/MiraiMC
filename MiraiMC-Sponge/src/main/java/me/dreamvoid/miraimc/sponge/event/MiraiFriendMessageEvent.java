package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 被动收到消息 - 好友消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.passive.MiraiFriendMessageEvent
 */
@Deprecated
public final class MiraiFriendMessageEvent extends me.dreamvoid.miraimc.sponge.event.message.passive.MiraiFriendMessageEvent {
    public MiraiFriendMessageEvent(FriendMessageEvent event, Cause cause) {
        super(event, cause);
    }
}
