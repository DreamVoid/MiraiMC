package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * 被动收到消息 - 群消息
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.message.passive.MiraiGroupMessageEvent
 */
@Deprecated
public final class MiraiGroupMessageEvent extends me.dreamvoid.miraimc.nukkit.event.message.passive.MiraiGroupMessageEvent {
    public MiraiGroupMessageEvent(GroupMessageEvent event) {
        super(event);
    }

    public MiraiGroupMessageEvent(long BotAccount, FetchMessage.Data data) {
        super(BotAccount, data);
    }
}
