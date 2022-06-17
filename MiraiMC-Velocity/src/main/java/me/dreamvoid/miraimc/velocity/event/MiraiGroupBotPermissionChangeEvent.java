package me.dreamvoid.miraimc.velocity.event;

import me.dreamvoid.miraimc.velocity.event.group.MiraiBotGroupPermissionChangeEvent;
import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;

/**
 * 机器人在群里的权限被改变
 * @deprecated
 * @see MiraiBotGroupPermissionChangeEvent
 */
@Deprecated
public class MiraiGroupBotPermissionChangeEvent extends MiraiBotGroupPermissionChangeEvent {
    public MiraiGroupBotPermissionChangeEvent(BotGroupPermissionChangeEvent event) {
        super(event);
    }
}
