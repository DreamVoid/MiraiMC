package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.FriendNickChangedEvent;

/**
 * 好友昵称改变
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.friend.MiraiFriendNickChangedEvent
 */
@Deprecated
public class MiraiFriendNickChangedEvent extends me.dreamvoid.miraimc.nukkit.event.friend.MiraiFriendNickChangedEvent {
    public MiraiFriendNickChangedEvent(FriendNickChangedEvent event) {
        super(event);
    }
}
