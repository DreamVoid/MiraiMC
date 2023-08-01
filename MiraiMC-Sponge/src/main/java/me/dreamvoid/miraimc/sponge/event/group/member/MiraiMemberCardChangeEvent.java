package me.dreamvoid.miraimc.sponge.event.group.member;

import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;

/**
 * (Sponge) 群 - 群成员 - 名片和头衔 - 成员群名片改动
 */
@SuppressWarnings("unused")
public class MiraiMemberCardChangeEvent extends AbstractGroupMemberEvent {
    public MiraiMemberCardChangeEvent(MemberCardChangeEvent event, Cause cause) {
        super(event, cause);
        this.event = event;
    }
    private final MemberCardChangeEvent event;

    /**
     * 获取群成员更改之前的名片
     * @return 群名片
     */
    public String getOldNick() {
        return event.getOrigin();
    }

    /**
     * 获取群成员更改之后的名片
     * @return 群名片
     */
    public String getNewNick() {
        return event.getNew();
    }

    /**
     * 获取群成员QQ号
     * @return QQ号
     */
    public long getMemberID(){
        return event.getMember().getId();
    }
}
