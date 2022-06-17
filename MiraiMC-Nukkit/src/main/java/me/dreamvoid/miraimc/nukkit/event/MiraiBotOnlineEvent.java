package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.BotOnlineEvent;

/**
 * Bot 登录完成
 * @see me.dreamvoid.miraimc.nukkit.event.bot.MiraiBotOnlineEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.nukkit.event.bot.MiraiBotOnlineEvent}
 */
@Deprecated
public class MiraiBotOnlineEvent extends me.dreamvoid.miraimc.nukkit.event.bot.MiraiBotOnlineEvent {
    public MiraiBotOnlineEvent(BotOnlineEvent event) {
        super(event);
    }
}
