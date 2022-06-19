package me.dreamvoid.miraimc.sponge.event.group.member;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.MemberJoinEvent;

/**
 * (Sponge) Mirai 核心事件 - 群 - 群成员 - 成员列表变更 - 成员已经加入群
 */
public class MiraiMemberJoinEvent extends AbstractGroupMemberEvent {
    public MiraiMemberJoinEvent(MemberJoinEvent event, Cause cause){
        super(event, cause);
        this.event = event;
    }

    private final MemberJoinEvent event;

    /**
     * 返回目标群的群号
     * @return 群号
     */
    @Override
    public long getGroupID() { return event.getGroupId(); }

    /**
     * 返回新成员的QQ号
     * @return 成员QQ号
     */
    public long getNewMemberID() { return event.getMember().getId(); }

    /**
     * 成员主动加入群
     */
    public static class Active extends MiraiMemberJoinEvent {
        public Active(MemberJoinEvent.Active event, Cause cause) {
            super(event, cause);
        }
    }

    /**
     * 成员被邀请加入群
     */
    public static class Invite extends MiraiMemberJoinEvent {
        public Invite(MemberJoinEvent.Invite event, Cause cause) {
            super(event, cause);
            this.event = event;
        }

        MemberJoinEvent.Invite event;

        /**
         * 返回邀请者的QQ号
         * 如果成员为主动加群，则返回 0
         * @return 邀请者QQ号
         */
        public long getInvitorID(){
            return event.getInvitor().getId();
        }
    }
}
