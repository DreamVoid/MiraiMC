package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.StrangerMessagePreSendEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 主动发送消息前 - 陌生人消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.presend.MiraiStrangerMessagePreSendEvent
 */
@Deprecated
public class MiraiStrangerMessagePreSendEvent extends me.dreamvoid.miraimc.sponge.event.message.presend.MiraiStrangerMessagePreSendEvent {
    public MiraiStrangerMessagePreSendEvent(StrangerMessagePreSendEvent event, Cause cause) {
        super(event, cause);
    }
}