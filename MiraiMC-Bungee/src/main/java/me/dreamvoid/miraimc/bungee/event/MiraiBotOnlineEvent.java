package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.BotOnlineEvent;

/**
 * Bot 登录完成
 * @see me.dreamvoid.miraimc.bungee.event.bot.MiraiBotOnlineEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.bungee.event.bot.MiraiBotOnlineEvent}
 */
@Deprecated
public class MiraiBotOnlineEvent extends me.dreamvoid.miraimc.bungee.event.bot.MiraiBotOnlineEvent {
    public MiraiBotOnlineEvent(BotOnlineEvent event) {
        super(event);
    }
}
