package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.group.member.MiraiMemberCardChangeEvent;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 群成员 - 名片和头衔 - 成员群名片改动
 * @deprecated
 * @see MiraiMemberCardChangeEvent
 */
@Deprecated
public class MiraiGroupMemberCardChangeEvent extends MiraiMemberCardChangeEvent {
    public MiraiGroupMemberCardChangeEvent(MemberCardChangeEvent event, Cause cause) {
        super(event, cause);
    }
}
