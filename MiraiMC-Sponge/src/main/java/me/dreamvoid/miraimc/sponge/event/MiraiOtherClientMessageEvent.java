package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.OtherClientMessageEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 被动收到消息 - 其他客户端消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.passive.MiraiOtherClientMessageEvent
 */
@Deprecated
public final class MiraiOtherClientMessageEvent extends me.dreamvoid.miraimc.sponge.event.message.passive.MiraiOtherClientMessageEvent {
    public MiraiOtherClientMessageEvent(OtherClientMessageEvent event, Cause cause) {
        super(event, cause);
    }
}
