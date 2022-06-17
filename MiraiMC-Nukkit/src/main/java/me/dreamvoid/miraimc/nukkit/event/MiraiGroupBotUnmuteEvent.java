package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.nukkit.event.group.MiraiBotUnmuteEvent;
import net.mamoe.mirai.event.events.BotUnmuteEvent;

/**
 * 机器人被取消禁言
 * @deprecated
 * @see MiraiBotUnmuteEvent
 */
@Deprecated
public class MiraiGroupBotUnmuteEvent extends MiraiBotUnmuteEvent {
    public MiraiGroupBotUnmuteEvent(BotUnmuteEvent event) {
        super(event);
    }
}
