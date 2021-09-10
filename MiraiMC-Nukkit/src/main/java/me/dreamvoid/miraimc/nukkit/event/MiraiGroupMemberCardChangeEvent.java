package me.dreamvoid.miraimc.nukkit.event;

import cn.nukkit.event.HandlerList;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;
import cn.nukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * 群成员 - 名片和头衔 - 成员群名片改动
 */
public class MiraiGroupMemberCardChangeEvent extends Event {
    public MiraiGroupMemberCardChangeEvent(MemberCardChangeEvent event) {
        this.event = event;
    }

    private final MemberCardChangeEvent event;

    private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlers() { return handlers; }
    //public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 获取群成员更改之前的名片
     * @return 群名片
     */
    public String getOldNick() {
        return event.getOrigin();
    }

    /**
     * 获取群成员更改之后的名片
     * @return 群名片
     */
    public String getNewNick() {
        return event.getNew();
    }

    /**
     * 获取哈希值
     * @return 哈希值
     */
    public int getHashCode() {
        return event.hashCode();
    }

    /**
     * 获取群成员QQ号
     * @return QQ号
     */
    public long getMemberID(){
        return event.getMember().getId();
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }
}
