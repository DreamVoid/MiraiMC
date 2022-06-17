package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.nukkit.event.group.MiraiBotLeaveEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;

/**
 * 机器人被踢出群或在其他客户端主动退出一个群
 * @deprecated
 * @see MiraiBotLeaveEvent
 */
@Deprecated
public class MiraiGroupBotLeaveEvent extends MiraiBotLeaveEvent {
    public MiraiGroupBotLeaveEvent(BotLeaveEvent event) {
        super(event);
    }
}
