package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.BotReloginEvent;

/**
 * Bot 重新登录
 * @see me.dreamvoid.miraimc.bungee.event.bot.MiraiBotReloginEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.bungee.event.bot.MiraiBotReloginEvent}
 */
@Deprecated
public class MiraiBotReloginEvent extends me.dreamvoid.miraimc.bungee.event.bot.MiraiBotReloginEvent {
    public MiraiBotReloginEvent(BotReloginEvent event) {
        super(event);
    }
}
