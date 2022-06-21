package me.dreamvoid.miraimc.velocity.event.friend;

import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;

/**
 * (Velocity) Mirai 核心事件 - 好友 - 好友头像改变
 */
public class MiraiFriendAvatarChangedEvent extends AbstractFriendEvent {
    public MiraiFriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
        super(event);
    }
}
