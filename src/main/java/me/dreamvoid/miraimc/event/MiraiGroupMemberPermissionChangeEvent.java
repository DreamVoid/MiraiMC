package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 群成员 - 成员权限 - 成员权限改变
 */
public class MiraiGroupMemberPermissionChangeEvent extends Event{

    // 主动退群
    public MiraiGroupMemberPermissionChangeEvent(MemberPermissionChangeEvent event) {
        super(true);
        this.event = event;
    }


    private static final HandlerList handlers = new HandlerList();
    private final MemberPermissionChangeEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

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
     * 返回成员的原有权限。
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getOriginPermission() {
        return event.getOrigin().getLevel();
    }

    /**
     * 返回成员的新权限。
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getNewPermssion() {
        return event.getNew().getLevel();
    }

    /**
     * 获取被操作成员QQ号
     * @return QQ号
     */
    public long getMemberID(){
        return event.getMember().getId();
    }

    /**
     * 获取哈希值
     * @return 哈希值
     */
    public int getHashCode(){
        return event.hashCode();
    }
}
