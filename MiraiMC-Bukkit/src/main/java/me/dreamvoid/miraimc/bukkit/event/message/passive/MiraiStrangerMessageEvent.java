package me.dreamvoid.miraimc.bukkit.event.message.passive;

import net.mamoe.mirai.event.events.StrangerMessageEvent;

/**
 * 陌生人消息
 */
public class MiraiStrangerMessageEvent extends AbstractMessageEvent {

    public MiraiStrangerMessageEvent(StrangerMessageEvent event) {
        super(event);
    }
}
