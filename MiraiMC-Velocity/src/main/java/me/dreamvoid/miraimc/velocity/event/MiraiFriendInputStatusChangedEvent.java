package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.FriendInputStatusChangedEvent;

/**
 * 好友输入状态改变
 * @deprecated
 * @see me.dreamvoid.miraimc.velocity.event.friend.MiraiFriendInputStatusChangedEvent
 */
@Deprecated
public class MiraiFriendInputStatusChangedEvent extends me.dreamvoid.miraimc.velocity.event.friend.MiraiFriendInputStatusChangedEvent {
    public MiraiFriendInputStatusChangedEvent(FriendInputStatusChangedEvent event) {
        super(event);
    }
}
