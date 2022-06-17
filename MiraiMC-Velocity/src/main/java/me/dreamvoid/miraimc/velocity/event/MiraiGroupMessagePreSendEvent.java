package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.GroupMessagePreSendEvent;

/**
 * 主动发送消息前 - 群消息
 * @deprecated
 * @see me.dreamvoid.miraimc.velocity.event.message.presend.MiraiGroupMessagePreSendEvent
 */
@Deprecated
public class MiraiGroupMessagePreSendEvent extends me.dreamvoid.miraimc.velocity.event.message.presend.MiraiGroupMessagePreSendEvent {
    public MiraiGroupMessagePreSendEvent(GroupMessagePreSendEvent event) {
        super(event);
    }
}

