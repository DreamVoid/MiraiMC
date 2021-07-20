package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 快速创建Mirai事件
 * 基于已经完成的BotOnlineEvent制作
 * 所有Mirai事件均有方法getBot，因此使用子方法getId作为基础方法
 * @author DreamVoid
 */
public class MiraiFriendAvatarChangedEvent extends Event {
    public MiraiFriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final FriendAvatarChangedEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 获取好友QQ号
     * @return QQ号
     */
    public long getFriendID(){
        return event.getFriend().getId();
    }

    /**
     * 获取好友昵称
     * @return 昵称
     */
    public String getFriendNick() {
        return event.getFriend().getNick();
    }

    /**
     * (?) 获取好友昵称
     * @return 昵称
     */
    public String getFriendRemark(){
        return event.getFriend().getRemark();
    }

    /**
     * 获取哈希值
     * @return 哈希值
     */
    public int getHashCode() {
        return event.hashCode();
    }

    /**
     * 获取好友头像链接
     * @return Url链接
     */
    public String getAvatarUrl(){
        return event.getFriend().getAvatarUrl();
    }
}
