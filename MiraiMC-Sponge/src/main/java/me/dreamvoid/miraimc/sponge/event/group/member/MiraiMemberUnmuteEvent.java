package me.dreamvoid.miraimc.sponge.event.group.member;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.MemberUnmuteEvent;

/**
 * (Sponge) Mirai 核心事件 - 群 - 群成员 - 动作 - 群成员被取消禁言
 */
public class MiraiMemberUnmuteEvent extends AbstractGroupMemberEvent {
    public MiraiMemberUnmuteEvent(MemberUnmuteEvent event, Cause cause) {
        super(event, cause);
        this.event = event;

        Sponge.getEventManager().post(new me.dreamvoid.miraimc.sponge.event.MiraiGroupMemberUnmuteEvent(event, cause));
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
