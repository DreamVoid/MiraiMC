package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.BotReloginEvent;

/**
 * Bot 重新登录
 * @see me.dreamvoid.miraimc.velocity.event.bot.MiraiBotReloginEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.velocity.event.bot.MiraiBotReloginEvent}
 */
@Deprecated
public class MiraiBotReloginEvent extends me.dreamvoid.miraimc.velocity.event.bot.MiraiBotReloginEvent {
    public MiraiBotReloginEvent(BotReloginEvent event) {
        super(event);
    }
}
