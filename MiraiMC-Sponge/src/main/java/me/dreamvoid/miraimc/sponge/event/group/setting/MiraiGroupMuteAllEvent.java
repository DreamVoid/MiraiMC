package me.dreamvoid.miraimc.sponge.event.group.setting;

import org.spongepowered.api.event.Cause;
import net.mamoe.mirai.event.events.GroupMuteAllEvent;

/**
 * (Sponge) 群 - 群设置 - 群设置改变 - 全员禁言状态改变
 */
@SuppressWarnings("unused")
public class MiraiGroupMuteAllEvent extends AbstractGroupSettingChangeEvent {
    private final GroupMuteAllEvent event;

    public MiraiGroupMuteAllEvent(GroupMuteAllEvent event, Cause cause) {
        super(event, cause);
        this.event = event;
    }

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
     * 获取群当前是否允许聊天
     * @return 是否允许聊天
     */
    public boolean isAllowChat(){
        return event.getNew();
    }

    /**
     * 获取群当前是否允许聊天
     * @return 是否允许聊天
     */
    public boolean isAllowChatBefore(){
        return event.getOrigin();
    }
}
