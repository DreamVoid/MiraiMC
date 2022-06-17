package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.StrangerMessageEvent;

/**
 * 被动收到消息 - 陌生人消息
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.message.passive.MiraiStrangerMessageEvent
 */
@Deprecated
public class MiraiStrangerMessageEvent extends me.dreamvoid.miraimc.bungee.event.message.passive.MiraiStrangerMessageEvent {
    public MiraiStrangerMessageEvent(StrangerMessageEvent event) {
        super(event);
    }
}
