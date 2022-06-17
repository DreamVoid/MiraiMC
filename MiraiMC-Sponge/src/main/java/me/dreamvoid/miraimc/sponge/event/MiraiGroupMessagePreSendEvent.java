package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.GroupMessagePreSendEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 主动发送消息前 - 群消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.presend.MiraiGroupMessagePreSendEvent
 */
@Deprecated
public class MiraiGroupMessagePreSendEvent extends me.dreamvoid.miraimc.sponge.event.message.presend.MiraiGroupMessagePreSendEvent {
    public MiraiGroupMessagePreSendEvent(GroupMessagePreSendEvent event, Cause cause) {
        super(event, cause);
    }
}

