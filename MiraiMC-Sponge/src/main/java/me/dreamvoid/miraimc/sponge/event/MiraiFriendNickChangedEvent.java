package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.FriendNickChangedEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 好友昵称改变
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendNickChangedEvent
 */
@Deprecated
public class MiraiFriendNickChangedEvent extends me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendNickChangedEvent {
    public MiraiFriendNickChangedEvent(FriendNickChangedEvent event, Cause cause) {
        super(event, cause);
    }
}
