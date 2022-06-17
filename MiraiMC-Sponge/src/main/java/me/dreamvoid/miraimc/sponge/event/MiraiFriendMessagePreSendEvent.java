package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.FriendMessagePreSendEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 主动发送消息前 - 好友消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.presend.MiraiFriendMessagePreSendEvent
 */
@Deprecated
public class MiraiFriendMessagePreSendEvent extends me.dreamvoid.miraimc.sponge.event.message.presend.MiraiFriendMessagePreSendEvent {
    public MiraiFriendMessagePreSendEvent(FriendMessagePreSendEvent event, Cause cause) {
        super(event, cause);
    }
}
