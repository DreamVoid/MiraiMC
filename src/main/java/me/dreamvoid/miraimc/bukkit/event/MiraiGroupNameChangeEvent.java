package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.GroupNameChangeEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 群设置 - 群名改变
 */
public class MiraiGroupNameChangeEvent extends Event {
    public MiraiGroupNameChangeEvent(GroupNameChangeEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final GroupNameChangeEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 获取更换前的名称
     * @return 群名称
     */
    public String getGroupOldName(){
        return event.getOrigin();
    }

    /**
     * 获取更换后的名称
     * @return 群名称
     */
    public String getGroupNewName(){
        return event.getNew();
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
     * 获取哈希值
     * @return 哈希值
     */
    public int getHashCode(){
        return event.hashCode();
    }
}
