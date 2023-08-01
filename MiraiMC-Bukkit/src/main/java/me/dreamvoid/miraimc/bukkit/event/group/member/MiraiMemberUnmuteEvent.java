package me.dreamvoid.miraimc.bukkit.event.group.member;

import net.mamoe.mirai.event.events.MemberUnmuteEvent;

/**
 * (Bukkit) 群 - 群成员 - 动作 - 群成员被取消禁言
 */
@SuppressWarnings("unused")
public class MiraiMemberUnmuteEvent extends AbstractGroupMemberEvent {
    public MiraiMemberUnmuteEvent(MemberUnmuteEvent event) {
        super(event);
        this.event = event;
    }

    private final MemberUnmuteEvent event;

    /**
     * 获取操作管理员QQ号
     * 如果不存在，返回0
     * @return QQ号
     */
    public long getOperatorID(){
        return event.getOperator()!=null ? event.getOperator().getId() : 0L;
    }

    /**
     * 获取被操作群成员QQ号
     * @return QQ号
     */
    public long getMemberID(){
        return event.getMember().getId();
    }
}
