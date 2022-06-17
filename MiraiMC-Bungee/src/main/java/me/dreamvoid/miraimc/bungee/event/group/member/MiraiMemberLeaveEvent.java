package me.dreamvoid.miraimc.bungee.event.group.member;

import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.md_5.bungee.api.ProxyServer;

/**
 * (bungee) Mirai 核心事件 - 群 - 群成员 - 成员列表变更 - 成员已经离开群
 */
public class MiraiMemberLeaveEvent extends AbstractGroupMemberEvent {
    public MiraiMemberLeaveEvent(MemberLeaveEvent event) {
        super(event);
        this.event = event;

        if(event instanceof MemberLeaveEvent.Kick){
            ProxyServer.getInstance().getPluginManager().callEvent(new me.dreamvoid.miraimc.bungee.event.MiraiGroupMemberLeaveEvent((MemberLeaveEvent.Kick) event,(MemberLeaveEvent.Kick) event));
        } else ProxyServer.getInstance().getPluginManager().callEvent(new me.dreamvoid.miraimc.bungee.event.MiraiGroupMemberLeaveEvent(event,(MemberLeaveEvent.Quit) event));
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

        public Kick(MemberLeaveEvent.Kick event) {
            super(event);
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
        public Quit(MemberLeaveEvent event) {
            super(event);
        }
    }
}
