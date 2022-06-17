package me.dreamvoid.miraimc.bungee.event;

import me.dreamvoid.miraimc.bungee.event.group.member.MiraiMemberUnmuteEvent;
import net.mamoe.mirai.event.events.MemberUnmuteEvent;

/**
 * 群成员 - 动作 - 群成员被取消禁言
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.group.member.MiraiMemberUnmuteEvent
 */
@Deprecated
public class MiraiGroupMemberUnmuteEvent extends MiraiMemberUnmuteEvent {
    public MiraiGroupMemberUnmuteEvent(MemberUnmuteEvent event) {
        super(event);
    }
}
