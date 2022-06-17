package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.GroupTempMessagePostSendEvent;

/**
 * 主动发送消息后 - 群临时会话消息
 * @deprecated
 * @see me.dreamvoid.miraimc.velocity.event.message.postsend.MiraiGroupTempMessagePostSendEvent
 */
@Deprecated
public class MiraiGroupTempMessagePostSendEvent extends me.dreamvoid.miraimc.velocity.event.message.postsend.MiraiGroupTempMessagePostSendEvent {
    public MiraiGroupTempMessagePostSendEvent(GroupTempMessagePostSendEvent event) {
        super(event);
    }
}
