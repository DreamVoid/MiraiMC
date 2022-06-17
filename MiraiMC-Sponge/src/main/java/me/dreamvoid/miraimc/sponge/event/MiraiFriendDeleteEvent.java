package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.FriendDeleteEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 好友已被删除
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendDeleteEvent
 */
@Deprecated
public class MiraiFriendDeleteEvent extends me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendDeleteEvent {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event, Cause cause) {
        super(event, cause);
    }
}
