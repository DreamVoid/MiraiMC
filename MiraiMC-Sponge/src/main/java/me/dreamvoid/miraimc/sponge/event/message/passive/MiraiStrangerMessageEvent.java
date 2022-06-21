package me.dreamvoid.miraimc.sponge.event.message.passive;

import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.StrangerMessageEvent;

/**
 * (Sponge) Mirai 核心事件 - 消息 - 被动收到消息 - 陌生人消息
 */
public class MiraiStrangerMessageEvent extends AbstractMessageEvent {

    public MiraiStrangerMessageEvent(StrangerMessageEvent event, Cause cause) {
        super(event, cause);
    }
}
