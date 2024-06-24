package me.dreamvoid.miraimc.sponge.event.friend;

import org.spongepowered.api.event.Cause;
import net.mamoe.mirai.event.events.FriendDeleteEvent;

/**
 * (Sponge) 好友 - 好友已被删除
 */
@SuppressWarnings("unused")
public class MiraiFriendDeleteEvent extends AbstractFriendEvent {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event, Cause cause) {
        super(event, cause);
    }
}
