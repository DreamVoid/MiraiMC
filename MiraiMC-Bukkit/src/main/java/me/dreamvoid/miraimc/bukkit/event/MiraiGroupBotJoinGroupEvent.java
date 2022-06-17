package me.dreamvoid.miraimc.bukkit.event;

import me.dreamvoid.miraimc.bukkit.event.group.MiraiBotJoinGroupEvent;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;

/**
 * 机器人成功加入了一个新群
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.group.MiraiBotJoinGroupEvent
 */
@Deprecated
public class MiraiGroupBotJoinGroupEvent extends MiraiBotJoinGroupEvent {
    public MiraiGroupBotJoinGroupEvent(BotJoinGroupEvent event) {
        super(event);
    }
}
