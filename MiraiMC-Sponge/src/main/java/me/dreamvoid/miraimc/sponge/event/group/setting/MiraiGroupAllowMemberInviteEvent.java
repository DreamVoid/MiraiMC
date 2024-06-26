package me.dreamvoid.miraimc.sponge.event.group.setting;

import org.spongepowered.api.event.Cause;
import net.mamoe.mirai.event.events.GroupAllowMemberInviteEvent;

/**
 * (Sponge) 群 - 群设置 - 群设置改变 - 允许群员邀请好友加群状态改变
 */
@SuppressWarnings("unused")
public class MiraiGroupAllowMemberInviteEvent extends AbstractGroupSettingChangeEvent {
    public MiraiGroupAllowMemberInviteEvent(GroupAllowMemberInviteEvent event, Cause cause) {
        super(event, cause);
        this.event = event;
    }

    private final GroupAllowMemberInviteEvent event;

    /**
     * 获取群号
     * @return 群号
     */
    @Override
    public long getGroupID(){
        return event.getGroupId();
    }

    /**
     * 获取操作管理员QQ号
     * 如果不存在，返回0
     * @return QQ号
     */
    public long getOperatorID(){
        if(event.getOperator()!=null){
            return event.getOperator().getId();
        } else return 0L;
    }

    /**
     * 获取群当前是否允许普通成员邀请
     * @return 是否允许邀请
     */
    public boolean isAllowInvite(){
        return event.getNew();
    }

    /**
     * 获取群之前是否允许普通成员邀请
     * @return 是否允许邀请
     */
    public boolean isAllowInviteBefore(){
        return event.getOrigin();
    }
}
