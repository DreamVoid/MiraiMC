package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.GroupMessagePreSendEvent;

/**
 * 主动发送消息前 - 群消息
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.message.presend.MiraiGroupMessagePreSendEvent
 */
@Deprecated
public class MiraiGroupMessagePreSendEvent extends me.dreamvoid.miraimc.bukkit.event.message.presend.MiraiGroupMessagePreSendEvent {
    public MiraiGroupMessagePreSendEvent(GroupMessagePreSendEvent event) {
        super(event);
    }
}

