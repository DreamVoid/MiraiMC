package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.group.member.MiraiMemberJoinEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 群成员 - 成员列表变更 - 成员已经加入群
 * @deprecated
 * @see MiraiMemberJoinEvent
 */
@Deprecated
public class MiraiGroupMemberJoinEvent extends MiraiMemberJoinEvent {

    public MiraiGroupMemberJoinEvent(MemberJoinEvent event, MemberJoinEvent.Active eventActive, Cause cause) {
        super(event, cause);
        this.event = event;
        this.eventActive = eventActive;
        this.eventInvite = null;
    }
    public MiraiGroupMemberJoinEvent(MemberJoinEvent event, MemberJoinEvent.Invite eventInvite, Cause cause) {
        super(event, cause);
        this.event = event;
        this.eventInvite = eventInvite;
        this.eventActive = null;
    }

    private final MemberJoinEvent event;
    private final MemberJoinEvent.Active eventActive;
    private final MemberJoinEvent.Invite eventInvite;

    /**
     * 返回邀请者的QQ号
     * 如果成员为主动加群，则返回 0
     * @return 邀请者QQ号
     */
    public long getInviterID(){
        if(eventInvite != null){
            return eventInvite.getInvitor().getId();
        } else return 0;
    }
}
