package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.GroupTempMessageEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 被动收到消息 - 群临时会话消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.passive.MiraiGroupTempMessageEvent
 */
@Deprecated
public class MiraiGroupTempMessageEvent extends me.dreamvoid.miraimc.sponge.event.message.passive.MiraiGroupTempMessageEvent {
    public MiraiGroupTempMessageEvent(GroupTempMessageEvent event, Cause cause) {
        super(event, cause);
    }
}
