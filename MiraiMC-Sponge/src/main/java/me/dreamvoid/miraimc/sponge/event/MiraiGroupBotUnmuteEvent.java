package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.group.MiraiBotUnmuteEvent;
import net.mamoe.mirai.event.events.BotUnmuteEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 机器人被取消禁言
 * @deprecated
 * @see MiraiBotUnmuteEvent
 */
@Deprecated
public class MiraiGroupBotUnmuteEvent extends MiraiBotUnmuteEvent {
    public MiraiGroupBotUnmuteEvent(BotUnmuteEvent event, Cause cause) {
        super(event, cause);
    }
}
