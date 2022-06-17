package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.NudgeEvent;

/**
 * 戳一戳
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.message.MiraiNudgeEvent
 */
@Deprecated
public class MiraiNudgeEvent extends me.dreamvoid.miraimc.bukkit.event.message.MiraiNudgeEvent {
    public MiraiNudgeEvent(NudgeEvent event) {
        super(event);
    }
}
