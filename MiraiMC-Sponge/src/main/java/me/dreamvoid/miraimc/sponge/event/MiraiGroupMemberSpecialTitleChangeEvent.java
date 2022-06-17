package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.group.member.MiraiMemberSpecialTitleChangeEvent;
import net.mamoe.mirai.event.events.MemberSpecialTitleChangeEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 群成员 - 名片和头衔 - 成员群头衔改动
 * @deprecated
 * @see MiraiMemberSpecialTitleChangeEvent
 */
@Deprecated
public class MiraiGroupMemberSpecialTitleChangeEvent extends MiraiMemberSpecialTitleChangeEvent {
    public MiraiGroupMemberSpecialTitleChangeEvent(MemberSpecialTitleChangeEvent event, Cause cause) {
        super(event, cause);
    }
}
