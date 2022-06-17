package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.OtherClientMessageEvent;

/**
 * 被动收到消息 - 其他客户端消息
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.message.passive.MiraiOtherClientMessageEvent
 */
@Deprecated
public final class MiraiOtherClientMessageEvent extends me.dreamvoid.miraimc.bungee.event.message.passive.MiraiOtherClientMessageEvent {
    public MiraiOtherClientMessageEvent(OtherClientMessageEvent event) {
        super(event);
    }
}
