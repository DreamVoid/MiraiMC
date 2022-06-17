package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.GroupNameChangeEvent;

/**
 * 群设置 - 群名改变
 * @deprecated
 * @see me.dreamvoid.miraimc.velocity.event.group.setting.MiraiGroupNameChangeEvent
 */
@Deprecated
public class MiraiGroupNameChangeEvent extends me.dreamvoid.miraimc.velocity.event.group.setting.MiraiGroupNameChangeEvent {
    public MiraiGroupNameChangeEvent(GroupNameChangeEvent event) {
        super(event);
    }
}
