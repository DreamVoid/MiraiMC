package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.BotReloginEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * Bot 重新登录
 */
public class MiraiBotReloginEvent extends Event {

    public MiraiBotReloginEvent(BotReloginEvent event) {
        this.event = event;
    }

    private final BotReloginEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getID() { return event.getBot().getId(); }

    /**
     * 获取机器人昵称
     * @return 机器人昵称
     */
    public String getNick() { return event.getBot().getNick(); }

}
