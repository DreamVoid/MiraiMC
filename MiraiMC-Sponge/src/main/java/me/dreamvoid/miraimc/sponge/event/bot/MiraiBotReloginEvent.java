package me.dreamvoid.miraimc.sponge.event.bot;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.BotReloginEvent;

/**
 * (Sponge) Mirai 核心事件 - Bot - Bot 重新登录
 */
public class MiraiBotReloginEvent extends AbstractBotEvent {
    public MiraiBotReloginEvent(BotReloginEvent event, Cause cause) {
        super(event, cause);


    }
}
