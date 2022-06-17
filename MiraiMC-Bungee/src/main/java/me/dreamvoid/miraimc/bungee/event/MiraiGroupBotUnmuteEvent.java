package me.dreamvoid.miraimc.bungee.event;

import me.dreamvoid.miraimc.bungee.event.group.MiraiBotUnmuteEvent;
import net.mamoe.mirai.event.events.BotUnmuteEvent;

/**
 * 机器人被取消禁言
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.group.MiraiBotUnmuteEvent
 */
@Deprecated
public class MiraiGroupBotUnmuteEvent extends MiraiBotUnmuteEvent {
    public MiraiGroupBotUnmuteEvent(BotUnmuteEvent event) {
        super(event);
    }
}
