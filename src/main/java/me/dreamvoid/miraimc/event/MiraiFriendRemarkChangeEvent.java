package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.FriendRemarkChangeEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 好友昵称改变
 */
public class MiraiFriendRemarkChangeEvent extends Event {
    public MiraiFriendRemarkChangeEvent(FriendRemarkChangeEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final FriendRemarkChangeEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 获取好友更名之前的昵称
     * @return 昵称
     */
    public String getOldRemark() {
        return event.getOldRemark();
    }

    /**
     * 获取好友更名之后的昵称
     * @return 昵称
     */
    public String getNewRemark() {
        return event.getNewRemark();
    }

    /**
     * 获取哈希值
     * @return 哈希值
     */
    public int getHashCode() {
        return event.hashCode();
    }
}
