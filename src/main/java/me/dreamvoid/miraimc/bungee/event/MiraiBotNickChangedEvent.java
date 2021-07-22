package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.BotNickChangedEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * Bot 昵称改变
 */
public class MiraiBotNickChangedEvent extends Event {

    public MiraiBotNickChangedEvent(BotNickChangedEvent event) {
        this.event = event;
    }

    private final BotNickChangedEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getID() { return event.getBot().getId(); }

    /**
     * 获取机器人更换前的昵称
     * @return 机器人更换前的昵称
     */
    public String getOldNick() { return event.getFrom(); }

    /**
     * 获取机器人更换后的昵称
     * @return 机器人更换后的昵称
     */
    public String getNewNick() { return event.getTo(); }
}
