package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.FriendInputStatusChangedEvent;

/**
 * 好友输入状态改变
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.friend.MiraiFriendInputStatusChangedEvent
 */
@Deprecated
public class MiraiFriendInputStatusChangedEvent extends me.dreamvoid.miraimc.nukkit.event.friend.MiraiFriendInputStatusChangedEvent {
    public MiraiFriendInputStatusChangedEvent(FriendInputStatusChangedEvent event) {
        super(event);
    }
}
