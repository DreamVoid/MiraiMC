package me.dreamvoid.miraimc.velocity.event;

/**
 * Bot 离线
 * @see me.dreamvoid.miraimc.velocity.event.bot.MiraiBotOfflineEvent
 * @deprecated 请使用 {@link me.dreamvoid.miraimc.velocity.event.bot.MiraiBotOfflineEvent}
 */
@Deprecated
public class MiraiBotOfflineEvent extends me.dreamvoid.miraimc.velocity.event.bot.MiraiBotOfflineEvent {
    public MiraiBotOfflineEvent(net.mamoe.mirai.event.events.BotOfflineEvent event, me.dreamvoid.miraimc.velocity.event.bot.MiraiBotOfflineEvent.Type type) {
        super(event, type);
    }
}
