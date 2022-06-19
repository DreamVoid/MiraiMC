package me.dreamvoid.miraimc.velocity.event.group.setting;

import me.dreamvoid.miraimc.velocity.VelocityPlugin;
import net.mamoe.mirai.event.events.GroupMuteAllEvent;

/**
 * (Velocity) Mirai 核心事件 - 群 - 群设置 - 群设置改变 - 全员禁言状态改变
 */
public class MiraiGroupMuteAllEvent extends AbstractGroupSettingChangeEvent {
    public MiraiGroupMuteAllEvent(GroupMuteAllEvent event) {
        super(event);
        this.event = event;

        VelocityPlugin.INSTANCE.getServer().getEventManager().fire(new me.dreamvoid.miraimc.velocity.event.MiraiGroupMuteAllEvent(event));
    }

    private final GroupMuteAllEvent event;

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
