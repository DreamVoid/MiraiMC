package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.group.MiraiBotJoinGroupEvent;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 机器人成功加入了一个新群
 * @deprecated
 * @see MiraiBotJoinGroupEvent
 */
@Deprecated
public class MiraiGroupBotJoinGroupEvent extends MiraiBotJoinGroupEvent {
    public MiraiGroupBotJoinGroupEvent(BotJoinGroupEvent event, Cause cause) {
        super(event, cause);
    }
}
