package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.GroupMuteAllEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 群设置 - 全员禁言状态改变
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.group.setting.MiraiGroupMuteAllEvent
 */
@Deprecated
public class MiraiGroupMuteAllEvent extends me.dreamvoid.miraimc.sponge.event.group.setting.MiraiGroupMuteAllEvent {
    public MiraiGroupMuteAllEvent(GroupMuteAllEvent event, Cause cause) {
        super(event, cause);
    }
}
