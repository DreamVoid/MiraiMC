package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;

/**
 * 好友头像改变
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.friend.MiraiFriendAvatarChangedEvent
 */
@Deprecated
public class MiraiFriendAvatarChangedEvent extends me.dreamvoid.miraimc.nukkit.event.friend.MiraiFriendAvatarChangedEvent {
    public MiraiFriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
        super(event);
    }
}
