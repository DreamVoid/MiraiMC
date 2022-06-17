package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.GroupMessagePostSendEvent;

/**
 * 主动发送消息后 - 群消息
 * @deprecated
 * @see me.dreamvoid.miraimc.velocity.event.message.postsend.MiraiGroupMessagePostSendEvent
 */
@Deprecated
public class MiraiGroupMessagePostSendEvent extends me.dreamvoid.miraimc.velocity.event.message.postsend.MiraiGroupMessagePostSendEvent {
    public MiraiGroupMessagePostSendEvent(GroupMessagePostSendEvent event) {
        super(event);
    }
}
