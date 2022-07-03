package me.dreamvoid.miraimc.bukkit.event.friend;

import net.mamoe.mirai.event.events.FriendDeleteEvent;

/**
 * (Bukkit) 好友 - 好友已被删除
 */
public class MiraiFriendDeleteEvent extends AbstractFriendEvent {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event) {
        super(event);
    }
}
