package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.GroupMessagePostSendEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 主动发送消息后 - 群消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.postsend.MiraiGroupMessagePostSendEvent
 */
@Deprecated
public class MiraiGroupMessagePostSendEvent extends me.dreamvoid.miraimc.sponge.event.message.postsend.MiraiGroupMessagePostSendEvent {
    public MiraiGroupMessagePostSendEvent(GroupMessagePostSendEvent event, Cause cause) {
        super(event, cause);
    }
}
