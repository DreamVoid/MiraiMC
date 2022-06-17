package me.dreamvoid.miraimc.bungee.event;

import me.dreamvoid.miraimc.bungee.event.group.MiraiBotJoinGroupEvent;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;

/**
 * 机器人成功加入了一个新群
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.group.MiraiBotJoinGroupEvent
 */
@Deprecated
public class MiraiGroupBotJoinGroupEvent extends MiraiBotJoinGroupEvent {
    public MiraiGroupBotJoinGroupEvent(BotJoinGroupEvent event) {
        super(event);
    }
}
