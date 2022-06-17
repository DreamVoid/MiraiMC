package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.nukkit.event.group.member.MiraiMemberSpecialTitleChangeEvent;
import net.mamoe.mirai.event.events.MemberSpecialTitleChangeEvent;

/**
 * 群成员 - 名片和头衔 - 成员群头衔改动
 * @deprecated
 * @see MiraiMemberSpecialTitleChangeEvent
 */
@Deprecated
public class MiraiGroupMemberSpecialTitleChangeEvent extends MiraiMemberSpecialTitleChangeEvent {
    public MiraiGroupMemberSpecialTitleChangeEvent(MemberSpecialTitleChangeEvent event) {
        super(event);
    }
}
