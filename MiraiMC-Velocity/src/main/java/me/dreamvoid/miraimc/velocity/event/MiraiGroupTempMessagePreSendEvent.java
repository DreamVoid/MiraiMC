package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.GroupTempMessagePreSendEvent;

/**
 * 主动发送消息前 - 群临时会话消息
 * @deprecated
 * @see me.dreamvoid.miraimc.velocity.event.message.presend.MiraiGroupTempMessagePreSendEvent
 */
@Deprecated
public class MiraiGroupTempMessagePreSendEvent extends me.dreamvoid.miraimc.velocity.event.message.presend.MiraiGroupTempMessagePreSendEvent {
    public MiraiGroupTempMessagePreSendEvent(GroupTempMessagePreSendEvent event) {
        super(event);
    }
}
