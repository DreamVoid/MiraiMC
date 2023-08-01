package me.dreamvoid.miraimc.velocity.event.friend;

import net.mamoe.mirai.event.events.FriendDeleteEvent;

/**
 * (Velocity) 好友 - 好友已被删除
 */
@SuppressWarnings("unused")
public class MiraiFriendDeleteEvent extends AbstractFriendEvent {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event) {
        super(event);
    }
}
