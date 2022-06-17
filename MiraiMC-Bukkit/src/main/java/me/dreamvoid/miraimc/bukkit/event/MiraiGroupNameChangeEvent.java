package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.GroupNameChangeEvent;

/**
 * 群设置 - 群名改变
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.group.setting.MiraiGroupNameChangeEvent
 */
@Deprecated
public class MiraiGroupNameChangeEvent extends me.dreamvoid.miraimc.bukkit.event.group.setting.MiraiGroupNameChangeEvent {
    public MiraiGroupNameChangeEvent(GroupNameChangeEvent event) {
        super(event);
    }
}
