package me.dreamvoid.miraimc.bukkit.event;

import me.dreamvoid.miraimc.bukkit.event.group.MiraiBotUnmuteEvent;
import net.mamoe.mirai.event.events.BotUnmuteEvent;

/**
 * 机器人被取消禁言
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.group.MiraiBotUnmuteEvent
 */
@Deprecated
public class MiraiGroupBotUnmuteEvent extends MiraiBotUnmuteEvent {
    public MiraiGroupBotUnmuteEvent(BotUnmuteEvent event) {
        super(event);
    }
}
