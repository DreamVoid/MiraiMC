package me.dreamvoid.miraimc.velocity.event;

import me.dreamvoid.miraimc.velocity.event.group.MiraiBotMuteEvent;
import net.mamoe.mirai.event.events.BotMuteEvent;

/**
 * 机器人被禁言
 * @deprecated
 * @see MiraiBotMuteEvent
 */
@Deprecated
public class MiraiGroupBotMuteEvent extends MiraiBotMuteEvent {
    public MiraiGroupBotMuteEvent(BotMuteEvent event) {
        super(event);
    }
}
