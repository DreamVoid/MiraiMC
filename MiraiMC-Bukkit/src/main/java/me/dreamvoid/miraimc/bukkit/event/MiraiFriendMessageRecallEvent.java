package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.MessageRecallEvent;

/**
 * 消息撤回 - 好友撤回
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.message.recall.MiraiFriendMessageRecallEvent
 */
@Deprecated
public class MiraiFriendMessageRecallEvent extends me.dreamvoid.miraimc.bukkit.event.message.recall.MiraiFriendMessageRecallEvent {
    public MiraiFriendMessageRecallEvent(MessageRecallEvent.FriendRecall event) {
        super(event);
    }
}
