package me.dreamvoid.miraimc.bungee.event;

import me.dreamvoid.miraimc.bungee.event.group.MiraiBotMuteEvent;
import net.mamoe.mirai.event.events.BotMuteEvent;

/**
 * 机器人被禁言
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.group.MiraiBotMuteEvent
 */
@Deprecated
public class MiraiGroupBotMuteEvent extends MiraiBotMuteEvent {
    public MiraiGroupBotMuteEvent(BotMuteEvent event) {
        super(event);
    }
}
