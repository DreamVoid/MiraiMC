package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.GroupMuteAllEvent;

/**
 * 群设置 - 全员禁言状态改变
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.group.setting.MiraiGroupMuteAllEvent
 */
@Deprecated
public class MiraiGroupMuteAllEvent extends me.dreamvoid.miraimc.nukkit.event.group.setting.MiraiGroupMuteAllEvent {
    public MiraiGroupMuteAllEvent(GroupMuteAllEvent event) {
        super(event);
    }
}
