package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.group.member.MiraiMemberMuteEvent;
import net.mamoe.mirai.event.events.MemberMuteEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 群成员 - 动作 - 群成员被禁言
 * @deprecated
 * @see MiraiMemberMuteEvent
 */
@Deprecated
public class MiraiGroupMemberMuteEvent extends MiraiMemberMuteEvent {
    public MiraiGroupMemberMuteEvent(MemberMuteEvent event, Cause cause) {
        super(event, cause);
    }
}
