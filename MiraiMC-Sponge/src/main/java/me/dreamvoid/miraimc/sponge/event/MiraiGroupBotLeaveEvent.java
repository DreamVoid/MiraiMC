package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.group.MiraiBotLeaveEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 机器人被踢出群或在其他客户端主动退出一个群
 * @deprecated
 * @see MiraiBotLeaveEvent
 */
@Deprecated
public class MiraiGroupBotLeaveEvent extends MiraiBotLeaveEvent {
    public MiraiGroupBotLeaveEvent(BotLeaveEvent event, Cause cause) {
        super(event, cause);
    }
}
