package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.BotReloginEvent;

/**
 * Bot 重新登录
 * @see me.dreamvoid.miraimc.bukkit.event.bot.MiraiBotReloginEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.bukkit.event.bot.MiraiBotReloginEvent}
 */
@Deprecated
public class MiraiBotReloginEvent extends me.dreamvoid.miraimc.bukkit.event.bot.MiraiBotReloginEvent {
    public MiraiBotReloginEvent(BotReloginEvent event) {
        super(event);
    }
}
