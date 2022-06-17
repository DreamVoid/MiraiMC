package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.StrangerMessagePreSendEvent;

/**
 * 主动发送消息前 - 陌生人消息
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.message.presend.MiraiStrangerMessagePreSendEvent
 */
@Deprecated
public class MiraiStrangerMessagePreSendEvent extends me.dreamvoid.miraimc.nukkit.event.message.presend.MiraiStrangerMessagePreSendEvent {
    public MiraiStrangerMessagePreSendEvent(StrangerMessagePreSendEvent event) {
        super(event);
    }
}