package me.dreamvoid.miraimc.nukkit.event.friend;

import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;

/**
 * (Nukkit) Mirai 核心事件 - 好友 - 好友头像改变
 */
public class MiraiFriendAvatarChangedEvent extends AbstractFriendEvent {
    public MiraiFriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
        super(event);
    }
}
