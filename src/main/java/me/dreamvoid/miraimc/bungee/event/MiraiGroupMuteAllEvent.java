package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.GroupMuteAllEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * 群设置 - 全员禁言状态改变
 */
public class MiraiGroupMuteAllEvent extends Event {
    public MiraiGroupMuteAllEvent(GroupMuteAllEvent event) {
        this.event = event;
    }

    private final GroupMuteAllEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 获取哈希值
     * @return 哈希值
     */
    public int getHashCode() {
        return event.hashCode();
    }

    /**
     * 获取群号
     * @return 群号
     */
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
}
