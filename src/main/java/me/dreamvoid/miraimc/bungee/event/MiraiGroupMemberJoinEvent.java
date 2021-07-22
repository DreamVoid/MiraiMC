package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * 群成员 - 成员列表变更 - 成员已经加入群
 */
public class MiraiGroupMemberJoinEvent extends Event{

    public MiraiGroupMemberJoinEvent(MemberJoinEvent event, MemberJoinEvent.Active eventActive) {
        this.event = event;
        this.eventActive = eventActive;
        this.eventInvite = null;
    }
    public MiraiGroupMemberJoinEvent(MemberJoinEvent event, MemberJoinEvent.Invite eventInvite) {
        this.event = event;
        this.eventInvite = eventInvite;
        this.eventActive = null;
    }

    private final MemberJoinEvent event;
    private final MemberJoinEvent.Active eventActive;
    private final MemberJoinEvent.Invite eventInvite;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 返回目标群的群号
     * @return 群号
     */
    public long getGroupID() { return event.getGroupId(); }

    /**
     * 返回新成员的QQ号
     * @return 成员QQ号
     */
    public long getNewMemberID() { return event.getMember().getId(); }

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
