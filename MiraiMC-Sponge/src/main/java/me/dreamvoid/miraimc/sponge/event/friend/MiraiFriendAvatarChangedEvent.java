package me.dreamvoid.miraimc.sponge.event.friend;

import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;

/**
 * (Sponge) 好友 - 好友头像改变
 */
@SuppressWarnings("unused")
public class MiraiFriendAvatarChangedEvent extends AbstractFriendEvent {
    public MiraiFriendAvatarChangedEvent(FriendAvatarChangedEvent event, Cause cause) {
        super(event, cause);
    }
}
