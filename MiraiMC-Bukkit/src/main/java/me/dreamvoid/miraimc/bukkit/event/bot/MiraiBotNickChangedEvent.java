package me.dreamvoid.miraimc.bukkit.event.bot;

import net.mamoe.mirai.event.events.BotNickChangedEvent;
import org.bukkit.Bukkit;

/**
 * (Bukkit) Mirai 核心事件 - Bot - Bot 昵称改变
 */
public class MiraiBotNickChangedEvent extends AbstractBotEvent {

    public MiraiBotNickChangedEvent(BotNickChangedEvent event) {
        super(event);
        this.event = event;

        Bukkit.getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiBotNickChangedEvent(event));
    }

    private final BotNickChangedEvent event;

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
