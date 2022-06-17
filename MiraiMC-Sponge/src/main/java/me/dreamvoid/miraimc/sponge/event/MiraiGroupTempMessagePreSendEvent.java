package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.GroupTempMessagePreSendEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 主动发送消息前 - 群临时会话消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.presend.MiraiGroupTempMessagePreSendEvent
 */
@Deprecated
public class MiraiGroupTempMessagePreSendEvent extends me.dreamvoid.miraimc.sponge.event.message.presend.MiraiGroupTempMessagePreSendEvent {
    public MiraiGroupTempMessagePreSendEvent(GroupTempMessagePreSendEvent event, Cause cause) {
        super(event, cause);
    }
}
