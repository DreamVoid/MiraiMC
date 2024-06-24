package me.dreamvoid.miraimc.sponge.event.bot;

import net.mamoe.mirai.event.events.BotAvatarChangedEvent;
import org.spongepowered.api.event.Cause;

/**
 * (Sponge) Bot - Bot 头像改变
 */
@SuppressWarnings("unused")
public class MiraiBotAvatarChangedEvent extends AbstractBotEvent {
    public MiraiBotAvatarChangedEvent(BotAvatarChangedEvent event, Cause cause) {
        super(event, cause);
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
