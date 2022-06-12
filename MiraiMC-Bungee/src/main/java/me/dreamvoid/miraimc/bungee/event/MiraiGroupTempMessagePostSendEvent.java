package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.GroupTempMessagePostSendEvent;

/**
 * 主动发送消息后 - 群临时会话消息
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.message.postsend.MiraiGroupTempMessagePostSendEvent
 */
@Deprecated
public class MiraiGroupTempMessagePostSendEvent extends me.dreamvoid.miraimc.bungee.event.message.postsend.MiraiGroupTempMessagePostSendEvent {
    public MiraiGroupTempMessagePostSendEvent(GroupTempMessagePostSendEvent event) {
        super(event);
    }
}
