package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.MessageRecallEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 消息撤回 - 群消息
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.recall.MiraiGroupMessageRecallEvent
 */
@Deprecated
public class MiraiGroupMessageRecallEvent extends me.dreamvoid.miraimc.sponge.event.message.recall.MiraiGroupMessageRecallEvent {
    public MiraiGroupMessageRecallEvent(MessageRecallEvent.GroupRecall event, Cause cause) {
        super(event, cause);
    }
}
