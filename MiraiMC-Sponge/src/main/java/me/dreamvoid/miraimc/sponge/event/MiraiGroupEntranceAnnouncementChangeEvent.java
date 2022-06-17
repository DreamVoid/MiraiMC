package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.GroupEntranceAnnouncementChangeEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 群设置 - 入群公告改变
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.group.setting.MiraiGroupEntranceAnnouncementChangeEvent
 */
@Deprecated
public class MiraiGroupEntranceAnnouncementChangeEvent extends me.dreamvoid.miraimc.sponge.event.group.setting.MiraiGroupEntranceAnnouncementChangeEvent {
    public MiraiGroupEntranceAnnouncementChangeEvent(GroupEntranceAnnouncementChangeEvent event, Cause cause) {
        super(event, cause);
    }
}
