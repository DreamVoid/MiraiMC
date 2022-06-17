package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.FriendInputStatusChangedEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 好友输入状态改变
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendInputStatusChangedEvent
 */
@Deprecated
public class MiraiFriendInputStatusChangedEvent extends me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendInputStatusChangedEvent {
    public MiraiFriendInputStatusChangedEvent(FriendInputStatusChangedEvent event, Cause cause) {
        super(event, cause);
    }
}
