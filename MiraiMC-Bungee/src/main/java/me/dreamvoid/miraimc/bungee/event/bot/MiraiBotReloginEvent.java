package me.dreamvoid.miraimc.bungee.event.bot;

import net.mamoe.mirai.event.events.BotReloginEvent;

/**
 * (BungeeCord) Bot - Bot 重新登录
 */
public class MiraiBotReloginEvent extends AbstractBotEvent {
    public MiraiBotReloginEvent(BotReloginEvent event) {
        super(event);
    }
}
