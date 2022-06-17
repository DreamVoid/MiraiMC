package me.dreamvoid.miraimc.bungee.event;

/**
 * Bot 离线
 * @see me.dreamvoid.miraimc.bungee.event.bot.MiraiBotOfflineEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.bungee.event.bot.MiraiBotOfflineEvent}
 */
@Deprecated
public class MiraiBotOfflineEvent extends me.dreamvoid.miraimc.bungee.event.bot.MiraiBotOfflineEvent {
    public MiraiBotOfflineEvent(net.mamoe.mirai.event.events.BotOfflineEvent event, me.dreamvoid.miraimc.bungee.event.bot.MiraiBotOfflineEvent.Type type) {
        super(event, type);
    }
}
