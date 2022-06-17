package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.FriendRemarkChangeEvent;

/**
 * 好友昵称改变
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.friend.MiraiFriendRemarkChangeEvent
 */
@Deprecated
public class MiraiFriendRemarkChangeEvent extends me.dreamvoid.miraimc.bukkit.event.friend.MiraiFriendRemarkChangeEvent {
    public MiraiFriendRemarkChangeEvent(FriendRemarkChangeEvent event) {
        super(event);
    }
}
