package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.BotNickChangedEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * Bot 昵称改变
 * @see me.dreamvoid.miraimc.sponge.event.bot.MiraiBotNickChangedEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.sponge.event.bot.MiraiBotNickChangedEvent}
 */
@Deprecated
public class MiraiBotNickChangedEvent extends me.dreamvoid.miraimc.sponge.event.bot.MiraiBotNickChangedEvent {
    public MiraiBotNickChangedEvent(BotNickChangedEvent event, Cause cause) {
        super(event, cause);
    }
}
