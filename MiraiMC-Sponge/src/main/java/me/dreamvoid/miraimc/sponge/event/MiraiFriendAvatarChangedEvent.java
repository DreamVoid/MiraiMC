package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 好友头像改变
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendAvatarChangedEvent
 */
@Deprecated
public class MiraiFriendAvatarChangedEvent extends me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendAvatarChangedEvent {
    public MiraiFriendAvatarChangedEvent(FriendAvatarChangedEvent event, Cause cause) {
        super(event, cause);
    }
}
