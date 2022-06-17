package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.nukkit.event.group.MiraiBotJoinGroupEvent;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;

/**
 * 机器人成功加入了一个新群
 * @deprecated
 * @see MiraiBotJoinGroupEvent
 */
@Deprecated
public class MiraiGroupBotJoinGroupEvent extends MiraiBotJoinGroupEvent {
    public MiraiGroupBotJoinGroupEvent(BotJoinGroupEvent event) {
        super(event);
    }
}
