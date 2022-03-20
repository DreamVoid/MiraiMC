package me.dreamvoid.miraimc.waterdogpe.event;

import dev.waterdog.waterdogpe.event.Event;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import net.mamoe.mirai.event.events.GroupAllowMemberInviteEvent;

/**
 * 群设置 - 允许群员邀请好友加群状态改变
 */
public class MiraiGroupAllowMemberInviteEvent extends Event {
    public MiraiGroupAllowMemberInviteEvent(GroupAllowMemberInviteEvent event) {
        this.event = event;
    }

    private final GroupAllowMemberInviteEvent event;

    /*private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlers() { return handlers; }*/
    //public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     *
     * @return 机器人账号
     */
    public long getBotID() {
        return event.getBot().getId();
    }

    /**
     * 获取哈希值
     *
     * @return 哈希值
     */
    public int getHashCode() {
        return event.hashCode();
    }

    /**
     * 获取群号
     *
     * @return 群号
     */
    public long getGroupID() {
        return event.getGroupId();
    }

    /**
     * 获取操作管理员QQ号
     * 如果不存在，返回0
     *
     * @return QQ号
     */
    public long getOperatorID() {
        if (event.getOperator() != null) {
            return event.getOperator().getId();
        } else return 0L;
    }

    /**
     * 获取群当前是否允许普通成员邀请
     *
     * @return 是否允许邀请
     */
    public boolean isAllowInvite() {
        return event.getNew();
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     *
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    /**
     * 获取群实例
     *
     * @return MiraiGroup 实例
     */
    public MiraiGroup getGroup() {
        return new MiraiGroup(event.getBot(), event.getGroup().getId());
    }
}
