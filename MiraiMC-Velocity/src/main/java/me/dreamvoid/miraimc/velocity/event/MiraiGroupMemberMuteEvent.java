package me.dreamvoid.miraimc.velocity.event;

import me.dreamvoid.miraimc.velocity.event.group.member.MiraiMemberMuteEvent;
import net.mamoe.mirai.event.events.MemberMuteEvent;

/**
 * 群成员 - 动作 - 群成员被禁言
 * @deprecated
 * @see MiraiMemberMuteEvent
 */
@Deprecated
public class MiraiGroupMemberMuteEvent extends MiraiMemberMuteEvent {
    public MiraiGroupMemberMuteEvent(MemberMuteEvent event) {
        super(event);
    }
}
