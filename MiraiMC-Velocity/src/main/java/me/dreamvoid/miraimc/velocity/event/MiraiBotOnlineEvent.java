package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.BotOnlineEvent;

/**
 * Bot 登录完成
 * @see me.dreamvoid.miraimc.velocity.event.bot.MiraiBotOnlineEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.velocity.event.bot.MiraiBotOnlineEvent}
 */
@Deprecated
public class MiraiBotOnlineEvent extends me.dreamvoid.miraimc.velocity.event.bot.MiraiBotOnlineEvent {
    public MiraiBotOnlineEvent(BotOnlineEvent event) {
        super(event);
    }
}
