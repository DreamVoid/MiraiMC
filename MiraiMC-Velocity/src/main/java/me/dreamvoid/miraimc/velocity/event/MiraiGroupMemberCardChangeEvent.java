package me.dreamvoid.miraimc.velocity.event;

import me.dreamvoid.miraimc.velocity.event.group.member.MiraiMemberCardChangeEvent;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;

/**
 * 群成员 - 名片和头衔 - 成员群名片改动
 * @deprecated
 * @see MiraiMemberCardChangeEvent
 */
@Deprecated
public class MiraiGroupMemberCardChangeEvent extends MiraiMemberCardChangeEvent {
    public MiraiGroupMemberCardChangeEvent(MemberCardChangeEvent event) {
        super(event);
    }
}
