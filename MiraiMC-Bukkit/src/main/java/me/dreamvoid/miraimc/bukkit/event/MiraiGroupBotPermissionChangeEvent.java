package me.dreamvoid.miraimc.bukkit.event;

import me.dreamvoid.miraimc.bukkit.event.group.MiraiBotGroupPermissionChangeEvent;
import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;

/**
 * 机器人在群里的权限被改变
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.group.MiraiBotGroupPermissionChangeEvent
 */
@Deprecated
public class MiraiGroupBotPermissionChangeEvent extends MiraiBotGroupPermissionChangeEvent {
    public MiraiGroupBotPermissionChangeEvent(BotGroupPermissionChangeEvent event) {
        super(event);
    }
}
