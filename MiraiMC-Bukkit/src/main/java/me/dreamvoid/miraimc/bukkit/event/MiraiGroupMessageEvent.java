package me.dreamvoid.miraimc.bukkit.event;

import me.dreamvoid.miraimc.internal.httpapi.response.FetchMessage;
import me.dreamvoid.miraimc.internal.httpapi.type.Message;
import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * 被动收到消息 - 群消息
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
 */
@Deprecated
public final class MiraiGroupMessageEvent extends me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent {
    public MiraiGroupMessageEvent(GroupMessageEvent event) {
        super(event);
    }

    public MiraiGroupMessageEvent(long BotAccount, FetchMessage.Sender sender, Message message) {
        super(BotAccount, sender, message);
    }
}
