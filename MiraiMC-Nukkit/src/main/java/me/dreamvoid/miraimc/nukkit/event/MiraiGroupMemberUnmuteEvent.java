package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.nukkit.event.group.member.MiraiMemberUnmuteEvent;
import net.mamoe.mirai.event.events.MemberUnmuteEvent;

/**
 * 群成员 - 动作 - 群成员被取消禁言
 * @deprecated
 * @see MiraiMemberUnmuteEvent
 */
@Deprecated
public class MiraiGroupMemberUnmuteEvent extends MiraiMemberUnmuteEvent {
    public MiraiGroupMemberUnmuteEvent(MemberUnmuteEvent event) {
        super(event);
    }
}
