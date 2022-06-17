package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.MessageRecallEvent;

/**
 * 消息撤回 - 群消息
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.message.recall.MiraiGroupMessageRecallEvent
 */
@Deprecated
public class MiraiGroupMessageRecallEvent extends me.dreamvoid.miraimc.nukkit.event.message.recall.MiraiGroupMessageRecallEvent {
    public MiraiGroupMessageRecallEvent(MessageRecallEvent.GroupRecall event) {
        super(event);
    }
}
