package me.dreamvoid.miraimc.waterdogpe.event;


import dev.waterdog.waterdogpe.event.Event;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import net.mamoe.mirai.event.events.MemberMuteEvent;

/**
 * 群成员 - 动作 - 群成员被禁言
 */
public class MiraiGroupMemberMuteEvent extends Event {
    public MiraiGroupMemberMuteEvent(MemberMuteEvent event) {
        this.event = event;
    }

    private final MemberMuteEvent event;

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
     * 获取被操作群成员QQ号
     *
     * @return QQ号
     */
    public long getMemberID() {
        return event.getMember().getId();
    }

    /**
     * 获取禁言事件
     *
     * @return 持续时间（秒）
     */
    public int getDurationTime() {
        return event.getDurationSeconds();
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

    /**
     * 获取群员实例
     *
     * @return MiraiNormalMember 实例
     */
    public MiraiNormalMember getMember() {
        return new MiraiNormalMember(event.getGroup(), event.getMember().getId());
    }
}
