package me.dreamvoid.miraimc.velocity.event.bot;

import me.dreamvoid.miraimc.velocity.VelocityPlugin;
import net.mamoe.mirai.event.events.BotNickChangedEvent;

/**
 * (bungee) Mirai 核心事件 - Bot - Bot 昵称改变
 */
public class MiraiBotNickChangedEvent extends AbstractBotEvent {

    public MiraiBotNickChangedEvent(BotNickChangedEvent event) {
        super(event);
        this.event = event;

        VelocityPlugin.INSTANCE.getServer().getEventManager().fire(new me.dreamvoid.miraimc.velocity.event.MiraiBotNickChangedEvent(event));
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
