package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.BotOnlineEvent;

/**
 * Bot 登录完成
 * @see me.dreamvoid.miraimc.bukkit.event.bot.MiraiBotOnlineEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.bukkit.event.bot.MiraiBotOnlineEvent}
 */
@Deprecated
public class MiraiBotOnlineEvent extends me.dreamvoid.miraimc.bukkit.event.bot.MiraiBotOnlineEvent {
    public MiraiBotOnlineEvent(BotOnlineEvent event) {
        super(event);
    }
}
