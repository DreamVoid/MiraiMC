package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.group.member.MiraiMemberUnmuteEvent;
import net.mamoe.mirai.event.events.MemberUnmuteEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 群成员 - 动作 - 群成员被取消禁言
 * @deprecated
 * @see MiraiMemberUnmuteEvent
 */
@Deprecated
public class MiraiGroupMemberUnmuteEvent extends MiraiMemberUnmuteEvent {
    public MiraiGroupMemberUnmuteEvent(MemberUnmuteEvent event, Cause cause) {
        super(event, cause);
    }
}
