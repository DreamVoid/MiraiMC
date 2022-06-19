package me.dreamvoid.miraimc.bungee.event.friend;

import net.mamoe.mirai.event.events.FriendAddEvent;
import net.md_5.bungee.api.ProxyServer;

/**
 * (BungeeCord) Mirai 核心事件 - 好友 - 成功添加了一个新好友
 */
public class MiraiFriendAddEvent extends AbstractFriendEvent {
    public MiraiFriendAddEvent(FriendAddEvent event) {
        super(event);

        ProxyServer.getInstance().getPluginManager().callEvent(new me.dreamvoid.miraimc.bungee.event.MiraiFriendAddEvent(event));
    }
}
