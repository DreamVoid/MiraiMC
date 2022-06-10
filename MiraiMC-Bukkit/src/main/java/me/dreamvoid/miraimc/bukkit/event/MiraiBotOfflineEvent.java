package me.dreamvoid.miraimc.bukkit.event;

/**
 * Bot 离线
 * @see me.dreamvoid.miraimc.bukkit.event.bot.MiraiBotOfflineEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.bukkit.event.bot.MiraiBotOfflineEvent}
 */
@Deprecated
public class MiraiBotOfflineEvent extends me.dreamvoid.miraimc.bukkit.event.bot.MiraiBotOfflineEvent {
    public MiraiBotOfflineEvent(net.mamoe.mirai.event.events.BotOfflineEvent event, me.dreamvoid.miraimc.bukkit.event.bot.MiraiBotOfflineEvent.Type type) {
        super(event, type);
    }
}
