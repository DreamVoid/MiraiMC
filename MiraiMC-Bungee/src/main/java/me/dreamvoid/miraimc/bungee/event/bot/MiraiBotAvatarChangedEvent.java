package me.dreamvoid.miraimc.bungee.event.bot;

import net.mamoe.mirai.event.events.BotAvatarChangedEvent;
import net.md_5.bungee.api.ProxyServer;

/**
 * (bungee) Mirai 核心事件 - Bot - Bot 头像改变
 */
public class MiraiBotAvatarChangedEvent extends AbstractBotEvent {
    public MiraiBotAvatarChangedEvent(BotAvatarChangedEvent event) {
        super(event);
        this.event = event;

        ProxyServer.getInstance().getPluginManager().callEvent(new me.dreamvoid.miraimc.bungee.event.MiraiBotAvatarChangedEvent(event));
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
