package me.dreamvoid.miraimc.nukkit.event.bot;

import net.mamoe.mirai.event.events.BotNickChangedEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (Nukkit) Mirai 核心事件 - Bot - Bot 昵称改变
 */
public class MiraiBotNickChangedEvent extends AbstractBotEvent {

    public MiraiBotNickChangedEvent(BotNickChangedEvent event) {
        super(event);
        this.event = event;
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
