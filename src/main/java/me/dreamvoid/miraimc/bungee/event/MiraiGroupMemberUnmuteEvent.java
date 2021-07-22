package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.MemberUnmuteEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * 群成员 - 动作 - 群成员被取消禁言
 */
public class MiraiGroupMemberUnmuteEvent extends Event {
    public MiraiGroupMemberUnmuteEvent(MemberUnmuteEvent event) {
        this.event = event;
    }

    private final MemberUnmuteEvent event;

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
     * 获取被操作群成员QQ号
     * @return QQ号
     */
    public long getMemberID(){
        return event.getMember().getId();
    }
}
