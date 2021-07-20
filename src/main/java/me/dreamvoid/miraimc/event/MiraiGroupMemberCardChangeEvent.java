package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.MemberCardChangeEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 快速创建Mirai事件
 * 基于已经完成的BotOnlineEvent制作
 * 所有Mirai事件均有方法getBot，因此使用子方法getId作为基础方法
 * @author DreamVoid
 */
public class MiraiGroupMemberCardChangeEvent extends Event {
    public MiraiGroupMemberCardChangeEvent(MemberCardChangeEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final MemberCardChangeEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

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
}
