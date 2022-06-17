package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.BotAvatarChangedEvent;

/**
 * Bot 头像改变
 * @see me.dreamvoid.miraimc.bungee.event.bot.MiraiBotAvatarChangedEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.bungee.event.bot.MiraiBotAvatarChangedEvent }
 */
@Deprecated
public class MiraiBotAvatarChangedEvent extends me.dreamvoid.miraimc.bungee.event.bot.MiraiBotAvatarChangedEvent {
    public MiraiBotAvatarChangedEvent(BotAvatarChangedEvent event) {
        super(event);
    }
}
