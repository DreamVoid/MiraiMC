package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.StrangerMessageEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 被动收到消息 - 陌生人消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.passive.MiraiStrangerMessageEvent
 */
@Deprecated
public class MiraiStrangerMessageEvent extends me.dreamvoid.miraimc.sponge.event.message.passive.MiraiStrangerMessageEvent {
    public MiraiStrangerMessageEvent(StrangerMessageEvent event, Cause cause) {
        super(event, cause);
    }
}
