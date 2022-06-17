package me.dreamvoid.miraimc.velocity.event;

import me.dreamvoid.miraimc.velocity.event.group.member.MiraiMemberLeaveEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;

/**
 * 群成员 - 成员列表变更 - 成员已经离开群
 * @deprecated
 * @see MiraiMemberLeaveEvent
 */
@Deprecated
public class MiraiGroupMemberLeaveEvent extends MiraiMemberLeaveEvent {

    // 主动退群
    public MiraiGroupMemberLeaveEvent(MemberLeaveEvent event, MemberLeaveEvent.Quit eventQuit) {
        super(event);
        this.event = event;
        this.eventQuit = eventQuit;
        this.eventKick = null;
    }

    // 被踢出群
    public MiraiGroupMemberLeaveEvent(MemberLeaveEvent.Kick event, MemberLeaveEvent.Kick eventKick) {
        super(event);
        this.event = event;
        this.eventKick = eventKick;
        this.eventQuit = null;
    }

    private final MemberLeaveEvent event;
    private final MemberLeaveEvent.Quit eventQuit;
    private final MemberLeaveEvent.Kick eventKick;

    /**
     * 返回退群类型
     * @return Active - 主动退群 | Kick - 被踢出群
     */
    public String getType() {
        if(eventKick != null){
            return "Kick";
        } else return "Quit";
    }

    /**
     * 返回操作管理员的QQ。
     * 如果成员为主动退群，则返回 0
     * @return 操作者ID
     */
    public long getOperator() {
        if(eventKick != null && eventKick.getOperator() != null){
            return eventKick.getOperator().getId();
        } else return 0;
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }
}
