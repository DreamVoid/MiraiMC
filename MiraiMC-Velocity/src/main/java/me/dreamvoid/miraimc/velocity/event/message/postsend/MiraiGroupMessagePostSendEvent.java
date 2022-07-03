package me.dreamvoid.miraimc.velocity.event.message.postsend;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessagePostSendEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * (Velocity) 消息 - 主动发送消息后 - 群消息
 */
public class MiraiGroupMessagePostSendEvent extends AbstractMessagePostSendEvent {
    public MiraiGroupMessagePostSendEvent(GroupMessagePostSendEvent event) {
        super(event);
        this.event = event;
    }

    private final GroupMessagePostSendEvent event;

    /**
     * 返回目标群的群号
     * @return 群号
     */
    public long getGroupID(){
        return event.getTarget().getId();
    }

    /**
     * 返回目标群的群名称
     * @return 群名称
     */
    public String getGroupName(){ return event.getTarget().getName(); }

    /**
     * 返回机器人解除禁言的剩余时间(如果已被禁言)
     * 此方法会同时判断目标群是否开启全员禁言，如果开启，则返回 -1
     * @return 禁言时间(秒) - 全员禁言返回 -1
     */
    public int getBotMuteRemainTime() {
        if(isMuteAll() && getBotPermission() == 0) {
            return -1;
        } else return event.getTarget().getBotMuteRemaining();
    }

    /**
     * 获取目标群的群成员列表
     * 此方法只返回QQ号
     * @return 群成员列表
     * @deprecated
     * @see MiraiGroup#getMembers()
     */
    @Deprecated
    public List<Long> getGroupMemberList(){
        ContactList<NormalMember> GroupMemberListOrigin = event.getTarget().getMembers(); // 原始数组
        List<Long> GroupMemberList = new ArrayList<>(); // 创建用于返回数据的数组
        for(NormalMember Member : GroupMemberListOrigin){ GroupMemberList.add(Member.getId()); } // 为返回的数组加入成员
        return GroupMemberList;
    }

    /**
     * 获取机器人在目标群的管理权限
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getBotPermission(){
        return event.getTarget().getBotPermission().getLevel();
    }

    /**
     * 判断目标群是否允许普通成员邀请新成员
     * @return 允许返回true，不允许返回false
     */
    public boolean isAllowMemberInvite(){
        return event.getTarget().getSettings().isAllowMemberInvite();
    }

    /**
     * 判断目标群是否允许匿名聊天
     * @return 允许返回true，不允许返回false
     */
    public boolean isAnonymousChatEnabled(){
        return event.getTarget().getSettings().isAnonymousChatEnabled();
    }

    /**
     * 判断目标群是否全员禁言
     * @return 全员禁言返回true，否则返回false
     */
    public boolean isMuteAll(){
        return event.getTarget().getSettings().isMuteAll();
    }

    /**
     * 判断目标群是否启用自动加群审批
     * @return 启用返回true，禁用返回false
     */
    public boolean isAutoApproveEnabled(){
        return event.getTarget().getSettings().isAutoApproveEnabled();
    }

    /**
     * 获取群实例
     * @return MiraiGroup 实例
     */
    public MiraiGroup getGroup(){
        return new MiraiGroup(event.getBot(), event.getTarget().getId());
    }
}
