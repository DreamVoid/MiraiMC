package me.dreamvoid.miraimc.bukkit.event.group.member;

import net.mamoe.mirai.event.events.MemberCardChangeEvent;

/**
 * 群成员 - 名片和头衔 - 成员群名片改动
 */
public class MiraiMemberCardChangeEvent extends AbstractGroupMemberEvent {
    public MiraiMemberCardChangeEvent(MemberCardChangeEvent event) {
        super(event);
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
