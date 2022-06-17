package me.dreamvoid.miraimc.bungee.event;

import me.dreamvoid.miraimc.bungee.event.group.MiraiBotGroupPermissionChangeEvent;
import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;

/**
 * 机器人在群里的权限被改变
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.group.MiraiBotGroupPermissionChangeEvent
 */
@Deprecated
public class MiraiGroupBotPermissionChangeEvent extends MiraiBotGroupPermissionChangeEvent {
    public MiraiGroupBotPermissionChangeEvent(BotGroupPermissionChangeEvent event) {
        super(event);
    }
}
