package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.FriendAddEvent;

/**
 * 成功添加了一个新好友
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.nukkit.event.friend.MiraiFriendAddEvent}
 * @see me.dreamvoid.miraimc.nukkit.event.friend.MiraiFriendAddEvent
 */
@Deprecated
public class MiraiFriendAddEvent extends me.dreamvoid.miraimc.nukkit.event.friend.MiraiFriendAddEvent {
    public MiraiFriendAddEvent(FriendAddEvent event) {
        super(event);
    }
}
