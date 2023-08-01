package me.dreamvoid.miraimc.bungee.event.friend;

import net.mamoe.mirai.event.events.FriendDeleteEvent;

/**
 * (BungeeCord) 好友 - 好友已被删除
 */
@SuppressWarnings("unused")
public class MiraiFriendDeleteEvent extends AbstractFriendEvent {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event) {
        super(event);
    }
}
