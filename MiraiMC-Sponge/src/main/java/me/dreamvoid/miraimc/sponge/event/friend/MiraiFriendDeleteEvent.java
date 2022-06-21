package me.dreamvoid.miraimc.sponge.event.friend;

import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.FriendDeleteEvent;

/**
 * (Sponge) Mirai 核心事件 - 好友 - 好友已被删除
 */
public class MiraiFriendDeleteEvent extends AbstractFriendEvent {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event, Cause cause) {
        super(event, cause);
    }
}
