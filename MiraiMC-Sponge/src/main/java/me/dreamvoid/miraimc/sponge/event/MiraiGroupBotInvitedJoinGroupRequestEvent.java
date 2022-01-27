package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * 群成员 - 成员列表变更 - 机器人被邀请加入群
 */
public class MiraiGroupBotInvitedJoinGroupRequestEvent extends AbstractEvent {
    private final Cause cause;

    public MiraiGroupBotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent event, Cause cause) {
        this.event = event;
        this.cause = cause;
    }

    private final BotInvitedJoinGroupRequestEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 返回目标群的群号
     * @return 群号
     */
    public long getGroupID() { return event.getGroupId(); }

    /**
     * 返回目标群的群名称
     * @return 群名称
     */
    public String getGroupName() { return event.getGroupName(); }

    /**
     * 返回邀请者的昵称
     * @return 邀请者昵称
     */
    public String getInviterNick() { return event.getInvitorNick(); }

    /**
     * 返回邀请者的QQ号
     * @return 邀请者QQ号
     */
    public long getInviterID(){ return event.getInvitorId(); }

    /**
     * 获取事件ID用于处理加群事件
     * @return 事件ID
     */
    public long getEventID(){ return event.getEventId(); }

    /**
     * 同意请求
     */
    public void setAccept(){
        event.accept();
        event.getBot().getLogger().info("[EventInvite/"+getBotID()+"] "+ getGroupID()+"("+getBotID() +"|"+getInviterID()+") <- Accept");
    }

    /**
     * 忽略请求
     */
    public void setIgnore(){
        event.ignore();
        event.getBot().getLogger().info("[EventInvite/"+getBotID()+"] "+ getGroupID()+"("+getBotID() +"|"+getInviterID()+") <- Ignore");
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
}
