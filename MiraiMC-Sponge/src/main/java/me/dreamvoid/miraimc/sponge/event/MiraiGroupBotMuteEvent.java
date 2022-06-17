package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.group.MiraiBotMuteEvent;
import net.mamoe.mirai.event.events.BotMuteEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 机器人被禁言
 * @deprecated
 * @see MiraiBotMuteEvent
 */
@Deprecated
public class MiraiGroupBotMuteEvent extends MiraiBotMuteEvent {
    public MiraiGroupBotMuteEvent(BotMuteEvent event, Cause cause) {
        super(event, cause);
    }
}
