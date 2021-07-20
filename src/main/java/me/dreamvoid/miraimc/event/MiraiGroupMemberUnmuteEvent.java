package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.MemberUnmuteEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 快速创建Mirai事件
 * 基于已经完成的BotOnlineEvent制作
 * 所有Mirai事件均有方法getBot，因此使用子方法getId作为基础方法
 * @author DreamVoid
 */
public class MiraiGroupMemberUnmuteEvent extends Event {
    public MiraiGroupMemberUnmuteEvent(MemberUnmuteEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final MemberUnmuteEvent event;

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
     * 获取被操作群成员QQ号
     * @return QQ号
     */
    public long getMemberID(){
        return event.getMember().getId();
    }
}
