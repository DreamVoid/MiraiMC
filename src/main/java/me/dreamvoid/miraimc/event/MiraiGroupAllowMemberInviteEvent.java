package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.GroupAllowMemberInviteEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 群设置 - 允许群员邀请好友加群状态改变
 */
public class MiraiGroupAllowMemberInviteEvent extends Event {
    public MiraiGroupAllowMemberInviteEvent(GroupAllowMemberInviteEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final GroupAllowMemberInviteEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

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
     * 获取群当前是否允许普通成员邀请
     * @return 是否允许邀请
     */
    public boolean isAllowInvite(){
        return event.getNew();
    }
}
