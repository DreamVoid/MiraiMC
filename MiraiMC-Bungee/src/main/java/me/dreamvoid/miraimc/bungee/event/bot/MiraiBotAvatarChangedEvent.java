package me.dreamvoid.miraimc.bungee.event.bot;

import net.mamoe.mirai.event.events.BotAvatarChangedEvent;

/**
 * (BungeeCord) Bot - Bot 头像改变
 */
@SuppressWarnings("unused")
public class MiraiBotAvatarChangedEvent extends AbstractBotEvent {
    public MiraiBotAvatarChangedEvent(BotAvatarChangedEvent event) {
        super(event);
        this.event = event;
    }

    private final BotAvatarChangedEvent event;

    /**
     * 获取机器人头像Url
     * @return 机器人头像Url
     */
    public String getAvatarUrl(){
        return event.getBot().getAvatarUrl();
    }
}
