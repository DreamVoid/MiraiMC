package me.dreamvoid.miraimc.nukkit.event.friend;

import net.mamoe.mirai.event.events.FriendDeleteEvent;

/**
 * (Nukkit) 好友 - 好友已被删除
 */
public class MiraiFriendDeleteEvent extends AbstractFriendEvent {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event) {
        super(event);
    }
}
