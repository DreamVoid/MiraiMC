package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.FriendDeleteEvent;

/**
 * 好友已被删除
 * @deprecated
 * @see me.dreamvoid.miraimc.velocity.event.friend.MiraiFriendDeleteEvent
 */
@Deprecated
public class MiraiFriendDeleteEvent extends me.dreamvoid.miraimc.velocity.event.friend.MiraiFriendDeleteEvent {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event) {
        super(event);
    }
}
