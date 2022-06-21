package me.dreamvoid.miraimc.nukkit.event.bot;

import net.mamoe.mirai.event.events.BotReloginEvent;

/**
 * (Nukkit) Mirai 核心事件 - Bot - Bot 重新登录
 */
public class MiraiBotReloginEvent extends AbstractBotEvent {
    public MiraiBotReloginEvent(BotReloginEvent event) {
        super(event);
    }
}
