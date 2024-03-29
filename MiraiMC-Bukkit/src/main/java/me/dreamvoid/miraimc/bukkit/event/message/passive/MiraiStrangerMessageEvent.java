package me.dreamvoid.miraimc.bukkit.event.message.passive;

import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import net.mamoe.mirai.event.events.StrangerMessageEvent;

/**
 * (Bukkit) 消息 - 被动收到消息 - 陌生人消息
 */
@SuppressWarnings("unused")
public class MiraiStrangerMessageEvent extends AbstractMessageEvent {
    public MiraiStrangerMessageEvent(StrangerMessageEvent event) {
        super(event);
    }

    public MiraiStrangerMessageEvent(long BotID, FetchMessage.Data data) {
        super(BotID, data);
    }
}
