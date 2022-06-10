package me.dreamvoid.miraimc.bukkit.event;

import me.dreamvoid.miraimc.bukkit.event.group.MiraiBotLeaveEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;

/**
 * 机器人被踢出群或在其他客户端主动退出一个群
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.group.MiraiBotLeaveEvent
 */
@Deprecated
public class MiraiGroupBotLeaveEvent extends MiraiBotLeaveEvent {
    public MiraiGroupBotLeaveEvent(BotLeaveEvent event) {
        super(event);
    }
}
