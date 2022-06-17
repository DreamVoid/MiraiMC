package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.GroupTempMessagePostSendEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 主动发送消息后 - 群临时会话消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.postsend.MiraiGroupTempMessagePostSendEvent
 */
@Deprecated
public class MiraiGroupTempMessagePostSendEvent extends me.dreamvoid.miraimc.sponge.event.message.postsend.MiraiGroupTempMessagePostSendEvent {
    public MiraiGroupTempMessagePostSendEvent(GroupTempMessagePostSendEvent event, Cause cause) {
        super(event, cause);
    }
}
