package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.GroupAllowMemberInviteEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 群设置 - 允许群员邀请好友加群状态改变
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.group.setting.MiraiGroupAllowMemberInviteEvent
 */
@Deprecated
public class MiraiGroupAllowMemberInviteEvent extends me.dreamvoid.miraimc.sponge.event.group.setting.MiraiGroupAllowMemberInviteEvent {
    public MiraiGroupAllowMemberInviteEvent(GroupAllowMemberInviteEvent event, Cause cause) {
        super(event, cause);
    }
}
