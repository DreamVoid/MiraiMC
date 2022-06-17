package me.dreamvoid.miraimc.bukkit.event;

import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberMuteEvent;
import net.mamoe.mirai.event.events.MemberMuteEvent;

/**
 * 群成员 - 动作 - 群成员被禁言
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberMuteEvent
 */
@Deprecated
public class MiraiGroupMemberMuteEvent extends MiraiMemberMuteEvent {
    public MiraiGroupMemberMuteEvent(MemberMuteEvent event) {
        super(event);
    }
}
