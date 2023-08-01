package me.dreamvoid.miraimc.bukkit.event.bot;

import net.mamoe.mirai.event.events.BotReloginEvent;

/**
 * (Bukkit) Bot - Bot 重新登录
 */
@SuppressWarnings("unused")
public class MiraiBotReloginEvent extends AbstractBotEvent {
    public MiraiBotReloginEvent(BotReloginEvent event) {
        super(event);
    }
}
