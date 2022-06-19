package me.dreamvoid.miraimc.bukkit.event.friend;

import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;
import org.bukkit.Bukkit;

/**
 * (Bukkit) Mirai 核心事件 - 好友 - 好友头像改变
 */
public class MiraiFriendAvatarChangedEvent extends AbstractFriendEvent {
    public MiraiFriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
        super(event);


    }
}
