package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.StrangerMessagePostSendEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 主动发送消息后 - 陌生人消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.postsend.MiraiStrangerMessagePostSendEvent
 */
@Deprecated
public class MiraiStrangerMessagePostSendEvent extends me.dreamvoid.miraimc.sponge.event.message.postsend.MiraiStrangerMessagePostSendEvent {
    public MiraiStrangerMessagePostSendEvent(StrangerMessagePostSendEvent event, Cause cause) {
        super(event, cause);
    }
}
