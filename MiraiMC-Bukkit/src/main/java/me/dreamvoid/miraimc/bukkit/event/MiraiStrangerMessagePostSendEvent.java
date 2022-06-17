package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.StrangerMessagePostSendEvent;

/**
 * 主动发送消息后 - 陌生人消息
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.message.postsend.MiraiStrangerMessagePostSendEvent
 */
@Deprecated
public class MiraiStrangerMessagePostSendEvent extends me.dreamvoid.miraimc.bukkit.event.message.postsend.MiraiStrangerMessagePostSendEvent {
    public MiraiStrangerMessagePostSendEvent(StrangerMessagePostSendEvent event) {
        super(event);
    }
}
