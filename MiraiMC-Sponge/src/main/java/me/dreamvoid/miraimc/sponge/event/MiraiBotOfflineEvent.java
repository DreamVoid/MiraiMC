package me.dreamvoid.miraimc.sponge.event;

import org.spongepowered.api.event.cause.Cause;

/**
 * Bot 离线
 * @see me.dreamvoid.miraimc.sponge.event.bot.MiraiBotOfflineEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.sponge.event.bot.MiraiBotOfflineEvent}
 */
@Deprecated
public class MiraiBotOfflineEvent extends me.dreamvoid.miraimc.sponge.event.bot.MiraiBotOfflineEvent {
    public MiraiBotOfflineEvent(net.mamoe.mirai.event.events.BotOfflineEvent event, me.dreamvoid.miraimc.sponge.event.bot.MiraiBotOfflineEvent.Type type, Cause cause) {
        super(event, type, cause);
    }
}
