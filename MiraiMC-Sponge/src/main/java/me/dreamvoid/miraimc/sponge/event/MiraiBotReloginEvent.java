package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.BotReloginEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * Bot 重新登录
 * @see me.dreamvoid.miraimc.sponge.event.bot.MiraiBotReloginEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.sponge.event.bot.MiraiBotReloginEvent}
 */
@Deprecated
public class MiraiBotReloginEvent extends me.dreamvoid.miraimc.sponge.event.bot.MiraiBotReloginEvent {
    public MiraiBotReloginEvent(BotReloginEvent event, Cause cause) {
        super(event, cause);
    }
}
