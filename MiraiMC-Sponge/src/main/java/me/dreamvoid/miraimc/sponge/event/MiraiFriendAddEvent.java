package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.FriendAddEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 成功添加了一个新好友
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendAddEvent}
 * @see me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendAddEvent
 */
@Deprecated
public class MiraiFriendAddEvent extends me.dreamvoid.miraimc.sponge.event.friend.MiraiFriendAddEvent {
    public MiraiFriendAddEvent(FriendAddEvent event, Cause cause) {
        super(event, cause);
    }
}
