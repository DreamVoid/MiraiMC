package me.dreamvoid.miraimc.bungee.event.friend;

import net.mamoe.mirai.event.events.FriendDeleteEvent;
import net.md_5.bungee.api.ProxyServer;

/**
 * (BungeeCord) Mirai 核心事件 - 好友 - 好友已被删除
 */
public class MiraiFriendDeleteEvent extends AbstractFriendEvent {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event) {
        super(event);
    }
}
