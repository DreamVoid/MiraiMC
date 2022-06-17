package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.BotAvatarChangedEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * Bot 头像改变
 * @see me.dreamvoid.miraimc.sponge.event.bot.MiraiBotAvatarChangedEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.sponge.event.bot.MiraiBotAvatarChangedEvent }
 */
@Deprecated
public class MiraiBotAvatarChangedEvent extends me.dreamvoid.miraimc.sponge.event.bot.MiraiBotAvatarChangedEvent {
    public MiraiBotAvatarChangedEvent(BotAvatarChangedEvent event, Cause cause) {
        super(event, cause);
    }
}
