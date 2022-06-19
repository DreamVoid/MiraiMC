package me.dreamvoid.miraimc.bungee.event.friend;

import net.mamoe.mirai.event.events.FriendInputStatusChangedEvent;
import net.md_5.bungee.api.ProxyServer;

/**
 * (BungeeCord) Mirai 核心事件 - 好友 - 好友输入状态改变
 */
public class MiraiFriendInputStatusChangedEvent extends AbstractFriendEvent {
    public MiraiFriendInputStatusChangedEvent(FriendInputStatusChangedEvent event) {
        super(event);
        this.event = event;

        ProxyServer.getInstance().getPluginManager().callEvent(new me.dreamvoid.miraimc.bungee.event.MiraiFriendInputStatusChangedEvent(event));
    }

    private final FriendInputStatusChangedEvent event;

    /**
     * 判断好友是否正在输入
     * @return 是否正在输入
     */
    public boolean isInputting(){
        return event.getInputting();
    }
}
