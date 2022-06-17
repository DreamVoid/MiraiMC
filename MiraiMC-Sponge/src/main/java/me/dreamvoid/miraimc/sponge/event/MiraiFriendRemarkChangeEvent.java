package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.FriendRemarkChangeEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 好友昵称改变
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendRemarkChangeEvent
 */
@Deprecated
public class MiraiFriendRemarkChangeEvent extends me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendRemarkChangeEvent {
    public MiraiFriendRemarkChangeEvent(FriendRemarkChangeEvent event, Cause cause) {
        super(event, cause);
    }
}
