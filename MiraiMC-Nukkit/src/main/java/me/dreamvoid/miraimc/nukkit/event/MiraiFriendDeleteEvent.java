package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.FriendDeleteEvent;

/**
 * 好友已被删除
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.friend.MiraiFriendDeleteEvent
 */
@Deprecated
public class MiraiFriendDeleteEvent extends me.dreamvoid.miraimc.nukkit.event.friend.MiraiFriendDeleteEvent {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event) {
        super(event);
    }
}
