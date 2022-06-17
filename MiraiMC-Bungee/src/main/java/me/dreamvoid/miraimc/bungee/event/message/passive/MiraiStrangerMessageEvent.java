package me.dreamvoid.miraimc.bungee.event.message.passive;

import net.mamoe.mirai.event.events.StrangerMessageEvent;
import net.md_5.bungee.api.ProxyServer;

/**
 * (bungee) Mirai 核心事件 - 消息 - 被动收到消息 - 陌生人消息
 */
public class MiraiStrangerMessageEvent extends AbstractMessageEvent {

    public MiraiStrangerMessageEvent(StrangerMessageEvent event) {
        super(event);

        ProxyServer.getInstance().getPluginManager().callEvent(new me.dreamvoid.miraimc.bungee.event.MiraiStrangerMessageEvent(event));
    }
}
