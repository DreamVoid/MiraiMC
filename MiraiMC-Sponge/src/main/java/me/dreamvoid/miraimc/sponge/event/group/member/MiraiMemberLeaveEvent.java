package me.dreamvoid.miraimc.sponge.event.group.member;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.MemberLeaveEvent;

/**
 * (Sponge) Mirai 核心事件 - 群 - 群成员 - 成员列表变更 - 成员已经离开群
 */
public class MiraiMemberLeaveEvent extends AbstractGroupMemberEvent {
    public MiraiMemberLeaveEvent(MemberLeaveEvent event, Cause cause) {
        super(event, cause);
        this.event = event;

        if(event instanceof MemberLeaveEvent.Kick){
            Sponge.getEventManager().post(new me.dreamvoid.miraimc.sponge.event.MiraiGroupMemberLeaveEvent((MemberLeaveEvent.Kick) event,(MemberLeaveEvent.Kick) event, cause));
        } else Sponge.getEventManager().post(new me.dreamvoid.miraimc.sponge.event.MiraiGroupMemberLeaveEvent(event,(MemberLeaveEvent.Quit) event, cause));
    }

    private final MemberLeaveEvent event;

    /**
     * 获取退出群的成员QQ
     * @return 成员QQ
     */
    public long getTargetID(){
        return event.getUser().getId();
    }

    /**
     * 成员被踢出群
     */
    public static class Kick extends MiraiMemberLeaveEvent{
        private MemberLeaveEvent.Kick event;

        public Kick(MemberLeaveEvent.Kick event, Cause cause) {
            super(event, cause);
            this.event = event;
        }

        /**
         * 返回操作管理员的QQ。
         * 如果成员为主动退群，则返回 0
         * @return 操作者ID
         */
        public long getOperator() {
            return event.getOperator().getId();
        }
    }

    /**
     * 成员主动离开群
     */
    public static class Quit extends MiraiMemberLeaveEvent{
        public Quit(MemberLeaveEvent event, Cause cause) {
            super(event, cause);
        }
    }
}
