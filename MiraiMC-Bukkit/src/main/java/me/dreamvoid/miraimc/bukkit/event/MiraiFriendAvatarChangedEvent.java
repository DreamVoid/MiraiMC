package me.dreamvoid.miraimc.bukkit.event;

import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 好友头像改变
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

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    /**
     * 获取好友实例
     * @return MiraiFriend 实例
     */
    public MiraiFriend getFriend(){
        return new MiraiFriend(event.getBot(), event.getFriend().getId());
    }
}
