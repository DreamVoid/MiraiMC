package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.NudgeEvent;

/**
 * 戳一戳
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.message.MiraiNudgeEvent
 */
@Deprecated
public class MiraiNudgeEvent extends me.dreamvoid.miraimc.nukkit.event.message.MiraiNudgeEvent {
    public MiraiNudgeEvent(NudgeEvent event) {
        super(event);
    }
}
