package me.dreamvoid.miraimc.sponge.event.bot;

import net.mamoe.mirai.event.events.BotNickChangedEvent;
import org.spongepowered.api.event.Cause;

/**
 * (Sponge) Bot - Bot 昵称改变
 */
@SuppressWarnings("unused")
public class MiraiBotNickChangedEvent extends AbstractBotEvent {

    public MiraiBotNickChangedEvent(BotNickChangedEvent event, Cause cause) {
        super(event, cause);
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
