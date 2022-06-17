package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.MessageRecallEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 消息撤回 - 好友撤回
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.recall.MiraiFriendMessageRecallEvent
 */
@Deprecated
public class MiraiFriendMessageRecallEvent extends me.dreamvoid.miraimc.sponge.event.message.recall.MiraiFriendMessageRecallEvent {
    public MiraiFriendMessageRecallEvent(MessageRecallEvent.FriendRecall event, Cause cause) {
        super(event, cause);
    }
}
