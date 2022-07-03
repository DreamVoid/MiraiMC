package me.dreamvoid.miraimc.bukkit.event.friend;

import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;

/**
 * (Bukkit) 好友 - 好友头像改变
 */
public class MiraiFriendAvatarChangedEvent extends AbstractFriendEvent {
    public MiraiFriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
        super(event);
    }
}
