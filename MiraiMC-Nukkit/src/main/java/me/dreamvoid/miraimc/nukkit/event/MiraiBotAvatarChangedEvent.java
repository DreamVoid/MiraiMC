package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.BotAvatarChangedEvent;

/**
 * Bot 头像改变
 * @see me.dreamvoid.miraimc.nukkit.event.bot.MiraiBotAvatarChangedEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.nukkit.event.bot.MiraiBotAvatarChangedEvent }
 */
@Deprecated
public class MiraiBotAvatarChangedEvent extends me.dreamvoid.miraimc.nukkit.event.bot.MiraiBotAvatarChangedEvent {
    public MiraiBotAvatarChangedEvent(BotAvatarChangedEvent event) {
        super(event);
    }
}
