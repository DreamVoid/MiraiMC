package me.dreamvoid.miraimc.bungee.event;

import me.dreamvoid.miraimc.bungee.event.group.MiraiBotLeaveEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;

/**
 * 机器人被踢出群或在其他客户端主动退出一个群
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.group.MiraiBotLeaveEvent
 */
@Deprecated
public class MiraiGroupBotLeaveEvent extends MiraiBotLeaveEvent {
    public MiraiGroupBotLeaveEvent(BotLeaveEvent event) {
        super(event);
    }
}
