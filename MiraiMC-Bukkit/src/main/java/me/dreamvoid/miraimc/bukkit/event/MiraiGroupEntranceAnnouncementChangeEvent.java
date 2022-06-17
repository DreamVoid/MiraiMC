package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.GroupEntranceAnnouncementChangeEvent;

/**
 * 群设置 - 入群公告改变
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.group.setting.MiraiGroupEntranceAnnouncementChangeEvent
 */
@Deprecated
public class MiraiGroupEntranceAnnouncementChangeEvent extends me.dreamvoid.miraimc.bukkit.event.group.setting.MiraiGroupEntranceAnnouncementChangeEvent {
    public MiraiGroupEntranceAnnouncementChangeEvent(GroupEntranceAnnouncementChangeEvent event) {
        super(event);
    }
}
