package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.GroupAllowAnonymousChatEvent;

/**
 * 群设置 - 匿名聊天状态改变
 * @deprecated
 * @see me.dreamvoid.miraimc.velocity.event.group.setting.MiraiGroupAllowAnonymousChatEvent
 */
@Deprecated
public class MiraiGroupAllowAnonymousChatEvent extends me.dreamvoid.miraimc.velocity.event.group.setting.MiraiGroupAllowAnonymousChatEvent {
    public MiraiGroupAllowAnonymousChatEvent(GroupAllowAnonymousChatEvent event) {
        super(event);
    }
}
