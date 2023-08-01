package me.dreamvoid.miraimc.velocity.event.friend;

import net.mamoe.mirai.event.events.FriendAddEvent;

/**
 * (Velocity) 好友 - 成功添加了一个新好友
 */
@SuppressWarnings("unused")
public class MiraiFriendAddEvent extends AbstractFriendEvent {
    public MiraiFriendAddEvent(FriendAddEvent event) {
        super(event);
    }
}
