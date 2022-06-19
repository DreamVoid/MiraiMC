package me.dreamvoid.miraimc.bukkit.event.friend;

import net.mamoe.mirai.event.events.FriendDeleteEvent;
import org.bukkit.Bukkit;

/**
 * (Bukkit) Mirai 核心事件 - 好友 - 好友已被删除
 */
public class MiraiFriendDeleteEvent extends AbstractFriendEvent {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event) {
        super(event);
    }
}
