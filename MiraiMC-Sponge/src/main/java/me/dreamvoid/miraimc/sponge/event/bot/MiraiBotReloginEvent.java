package me.dreamvoid.miraimc.sponge.event.bot;

import org.spongepowered.api.event.Cause;
import net.mamoe.mirai.event.events.BotReloginEvent;

/**
 * (Sponge) Bot - Bot 重新登录
 */
@SuppressWarnings("unused")
public class MiraiBotReloginEvent extends AbstractBotEvent {
    public MiraiBotReloginEvent(BotReloginEvent event, Cause cause) {
        super(event, cause);
    }
}
