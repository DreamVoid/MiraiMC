package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.GroupAllowAnonymousChatEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 群设置 - 匿名聊天状态改变
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.group.setting.MiraiGroupAllowAnonymousChatEvent
 */
@Deprecated
public class MiraiGroupAllowAnonymousChatEvent extends me.dreamvoid.miraimc.sponge.event.group.setting.MiraiGroupAllowAnonymousChatEvent {
    public MiraiGroupAllowAnonymousChatEvent(GroupAllowAnonymousChatEvent event, Cause cause) {
        super(event, cause);
    }
}
