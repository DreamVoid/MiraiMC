package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.BotNickChangedEvent;

/**
 * Bot 昵称改变
 * @see me.dreamvoid.miraimc.bungee.event.bot.MiraiBotNickChangedEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.bungee.event.bot.MiraiBotNickChangedEvent}
 */
@Deprecated
public class MiraiBotNickChangedEvent extends me.dreamvoid.miraimc.bungee.event.bot.MiraiBotNickChangedEvent {
    public MiraiBotNickChangedEvent(BotNickChangedEvent event) {
        super(event);
    }
}
