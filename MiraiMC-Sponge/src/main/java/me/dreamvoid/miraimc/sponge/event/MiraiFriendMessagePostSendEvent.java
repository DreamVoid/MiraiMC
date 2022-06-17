package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.FriendMessagePostSendEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 主动发送消息后 - 好友消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.postsend.MiraiFriendMessagePostSendEvent
 */
@Deprecated
public class MiraiFriendMessagePostSendEvent extends me.dreamvoid.miraimc.sponge.event.message.postsend.MiraiFriendMessagePostSendEvent {
    public MiraiFriendMessagePostSendEvent(FriendMessagePostSendEvent event, Cause cause) {
        super(event, cause);
    }
}
