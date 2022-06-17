package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.BotOnlineEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * Bot 登录完成
 * @see me.dreamvoid.miraimc.sponge.event.bot.MiraiBotOnlineEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.sponge.event.bot.MiraiBotOnlineEvent}
 */
@Deprecated
public class MiraiBotOnlineEvent extends me.dreamvoid.miraimc.sponge.event.bot.MiraiBotOnlineEvent {
    public MiraiBotOnlineEvent(BotOnlineEvent event, Cause cause) {
        super(event, cause);
    }
}
