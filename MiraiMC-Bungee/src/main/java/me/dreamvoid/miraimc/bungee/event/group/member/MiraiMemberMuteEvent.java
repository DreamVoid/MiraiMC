package me.dreamvoid.miraimc.bungee.event.group.member;

import net.mamoe.mirai.event.events.MemberMuteEvent;

/**
 * (BungeeCord) 群 - 群成员 - 动作 - 群成员被禁言
 */
@SuppressWarnings("unused")
public class MiraiMemberMuteEvent extends AbstractGroupMemberEvent {
    public MiraiMemberMuteEvent(MemberMuteEvent event) {
        super(event);
        this.event = event;
    }

    private final MemberMuteEvent event;

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

    /**
     * 获取禁言事件
     * @return 持续时间（秒）
     */
    public int getDurationTime(){
        return event.getDurationSeconds();
    }
}
