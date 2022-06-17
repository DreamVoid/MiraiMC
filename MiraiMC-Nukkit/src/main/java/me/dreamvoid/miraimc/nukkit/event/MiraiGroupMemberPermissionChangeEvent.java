package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.nukkit.event.group.member.MiraiMemberPermissionChangeEvent;
import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;

/**
 * 群成员 - 成员权限 - 成员权限改变
 * @deprecated
 * @see MiraiMemberPermissionChangeEvent
 */
@Deprecated
public class MiraiGroupMemberPermissionChangeEvent extends MiraiMemberPermissionChangeEvent {
    public MiraiGroupMemberPermissionChangeEvent(MemberPermissionChangeEvent event) {
        super(event);
    }
}
