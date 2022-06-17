package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.NudgeEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 戳一戳
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.MiraiNudgeEvent
 */
@Deprecated
public class MiraiNudgeEvent extends me.dreamvoid.miraimc.sponge.event.message.MiraiNudgeEvent {
    public MiraiNudgeEvent(NudgeEvent event, Cause cause) {
        super(event, cause);
    }
}
