package me.dreamvoid.miraimc.bungee.event;

import me.dreamvoid.miraimc.bungee.event.group.member.MiraiMemberCardChangeEvent;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;

/**
 * 群成员 - 名片和头衔 - 成员群名片改动
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.group.member.MiraiMemberCardChangeEvent
 */
@Deprecated
public class MiraiGroupMemberCardChangeEvent extends MiraiMemberCardChangeEvent {
    public MiraiGroupMemberCardChangeEvent(MemberCardChangeEvent event) {
        super(event);
    }
}
