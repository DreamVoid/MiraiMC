package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.FriendInputStatusChangedEvent;

/**
 * 好友输入状态改变
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.friend.MiraiFriendInputStatusChangedEvent
 */
@Deprecated
public class MiraiFriendInputStatusChangedEvent extends me.dreamvoid.miraimc.bungee.event.friend.MiraiFriendInputStatusChangedEvent {
    public MiraiFriendInputStatusChangedEvent(FriendInputStatusChangedEvent event) {
        super(event);
    }
}
