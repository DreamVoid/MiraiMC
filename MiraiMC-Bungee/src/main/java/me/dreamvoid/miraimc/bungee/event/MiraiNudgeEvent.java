package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.NudgeEvent;

/**
 * 戳一戳
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.message.MiraiNudgeEvent
 */
@Deprecated
public class MiraiNudgeEvent extends me.dreamvoid.miraimc.bungee.event.message.MiraiNudgeEvent {
    public MiraiNudgeEvent(NudgeEvent event) {
        super(event);
    }
}
