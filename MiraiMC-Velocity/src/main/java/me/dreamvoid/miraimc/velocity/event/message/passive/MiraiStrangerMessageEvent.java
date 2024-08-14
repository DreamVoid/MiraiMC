package me.dreamvoid.miraimc.velocity.event.message.passive;

import net.mamoe.mirai.event.events.StrangerMessageEvent;

/**
 * (Velocity) 消息 - 被动收到消息 - 陌生人消息
 */
@SuppressWarnings("unused")
public class MiraiStrangerMessageEvent extends AbstractMessageEvent {
    public MiraiStrangerMessageEvent(StrangerMessageEvent event) {
        super(event);
    }
}
