package me.dreamvoid.miraimc.bukkit.event.message.passive;

import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import net.mamoe.mirai.event.events.StrangerMessageEvent;

/**
 * (Bukkit) Mirai 核心事件 - 消息 - 被动收到消息 - 陌生人消息
 */
public class MiraiStrangerMessageEvent extends AbstractMessageEvent {
    public MiraiStrangerMessageEvent(StrangerMessageEvent event) {
        super(event);
    }

    public MiraiStrangerMessageEvent(long BotID, FetchMessage.Data data) {
        super(BotID, data);
    }
}
