package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 被动收到消息 - 群消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.passive.MiraiGroupMessageEvent
 */
@Deprecated
public final class MiraiGroupMessageEvent extends me.dreamvoid.miraimc.sponge.event.message.passive.MiraiGroupMessageEvent {
    public MiraiGroupMessageEvent(GroupMessageEvent event, Cause cause) {
        super(event, cause);
    }
}
