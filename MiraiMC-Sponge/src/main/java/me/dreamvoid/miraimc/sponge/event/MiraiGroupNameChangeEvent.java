package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.GroupNameChangeEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 群设置 - 群名改变
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.group.setting.MiraiGroupNameChangeEvent
 */
@Deprecated
public class MiraiGroupNameChangeEvent extends me.dreamvoid.miraimc.sponge.event.group.setting.MiraiGroupNameChangeEvent {
    public MiraiGroupNameChangeEvent(GroupNameChangeEvent event, Cause cause) {
        super(event, cause);
    }
}
