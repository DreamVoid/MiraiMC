package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;

/**
 * 好友头像改变
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.friend.MiraiFriendAvatarChangedEvent
 */
@Deprecated
public class MiraiFriendAvatarChangedEvent extends me.dreamvoid.miraimc.bukkit.event.friend.MiraiFriendAvatarChangedEvent {
    public MiraiFriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
        super(event);
    }
}
