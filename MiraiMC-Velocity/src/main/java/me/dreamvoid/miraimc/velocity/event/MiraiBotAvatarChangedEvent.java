package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.BotAvatarChangedEvent;

/**
 * Bot 头像改变
 * @see me.dreamvoid.miraimc.velocity.event.bot.MiraiBotAvatarChangedEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.velocity.event.bot.MiraiBotAvatarChangedEvent }
 */
@Deprecated
public class MiraiBotAvatarChangedEvent extends me.dreamvoid.miraimc.velocity.event.bot.MiraiBotAvatarChangedEvent {
    public MiraiBotAvatarChangedEvent(BotAvatarChangedEvent event) {
        super(event);
    }
}
