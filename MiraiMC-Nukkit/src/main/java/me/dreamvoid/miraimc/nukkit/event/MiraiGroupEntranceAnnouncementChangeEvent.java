package me.dreamvoid.miraimc.nukkit.event;

import net.mamoe.mirai.event.events.GroupEntranceAnnouncementChangeEvent;

/**
 * 群设置 - 入群公告改变
 * @deprecated
 * @see me.dreamvoid.miraimc.nukkit.event.group.setting.MiraiGroupEntranceAnnouncementChangeEvent
 */
@Deprecated
public class MiraiGroupEntranceAnnouncementChangeEvent extends me.dreamvoid.miraimc.nukkit.event.group.setting.MiraiGroupEntranceAnnouncementChangeEvent {
    public MiraiGroupEntranceAnnouncementChangeEvent(GroupEntranceAnnouncementChangeEvent event) {
        super(event);
    }
}
