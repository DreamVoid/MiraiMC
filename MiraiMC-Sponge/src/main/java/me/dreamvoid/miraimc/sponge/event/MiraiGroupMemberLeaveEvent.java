package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * 群成员 - 成员列表变更 - 成员已经离开群
 */
public class MiraiGroupMemberLeaveEvent extends AbstractEvent {
    private final Cause cause;

    // 主动退群
    public MiraiGroupMemberLeaveEvent(MemberLeaveEvent event, MemberLeaveEvent.Quit eventQuit, Cause cause) {
        this.event = event;
        this.cause = cause;
        this.eventQuit = eventQuit;
        this.eventKick = null;
    }

    // 被踢出群
    public MiraiGroupMemberLeaveEvent(MemberLeaveEvent.Kick event, MemberLeaveEvent.Kick eventKick, Cause cause) {
        this.event = event;
        this.cause = cause;
        this.eventKick = eventKick;
        this.eventQuit = null;
    }

    private final MemberLeaveEvent event;
    private final MemberLeaveEvent.Quit eventQuit;
    private final MemberLeaveEvent.Kick eventKick;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 返回退出群的群号
     * @return 群号
     */
    public long getGroupID() { return event.getGroupId(); }

    /**
     * 返回退群类型
     * @return Active - 主动退群 | Kick - 被踢出群
     */
    public String getType() {
        if(eventKick != null){
            return "Kick";
        } else return "Quit";
    }

    /**
     * 获取退出群的成员QQ
     * @return 成员QQ
     */
    public long getTargetID(){
        return event.getUser().getId();
    }
    /**
     * 返回操作管理员的QQ。
     * 如果成员为主动退群，则返回 0
     * @return 操作者ID
     */
    public long getOperator() {
        if(eventKick != null && eventKick.getOperator() != null){
            return eventKick.getOperator().getId();
        } else return 0;
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    @Override
    public @NotNull Cause getCause() {
        return cause;
    }

    /**
     * 获取群实例
     * @return MiraiGroup 实例
     */
    public MiraiGroup getGroup(){
        return new MiraiGroup(event.getBot(), event.getGroup().getId());
    }
}
