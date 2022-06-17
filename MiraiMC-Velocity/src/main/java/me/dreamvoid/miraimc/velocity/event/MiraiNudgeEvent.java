package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.NudgeEvent;

/**
 * 戳一戳
 * @deprecated
 * @see me.dreamvoid.miraimc.velocity.event.message.MiraiNudgeEvent
 */
@Deprecated
public class MiraiNudgeEvent extends me.dreamvoid.miraimc.velocity.event.message.MiraiNudgeEvent {
    public MiraiNudgeEvent(NudgeEvent event) {
        super(event);
    }
}
