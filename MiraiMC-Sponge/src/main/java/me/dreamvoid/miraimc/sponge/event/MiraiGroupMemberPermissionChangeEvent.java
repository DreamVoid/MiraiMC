package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.group.member.MiraiMemberPermissionChangeEvent;
import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 群成员 - 成员权限 - 成员权限改变
 * @deprecated
 * @see MiraiMemberPermissionChangeEvent
 */
@Deprecated
public class MiraiGroupMemberPermissionChangeEvent extends MiraiMemberPermissionChangeEvent {
    public MiraiGroupMemberPermissionChangeEvent(MemberPermissionChangeEvent event, Cause cause) {
        super(event, cause);
    }
}
