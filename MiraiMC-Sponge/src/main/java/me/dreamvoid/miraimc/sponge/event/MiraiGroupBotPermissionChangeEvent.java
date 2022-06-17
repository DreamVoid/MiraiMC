package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.group.MiraiBotGroupPermissionChangeEvent;
import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 机器人在群里的权限被改变
 * @deprecated
 * @see MiraiBotGroupPermissionChangeEvent
 */
@Deprecated
public class MiraiGroupBotPermissionChangeEvent extends MiraiBotGroupPermissionChangeEvent {
    public MiraiGroupBotPermissionChangeEvent(BotGroupPermissionChangeEvent event, Cause cause) {
        super(event, cause);
    }
}
