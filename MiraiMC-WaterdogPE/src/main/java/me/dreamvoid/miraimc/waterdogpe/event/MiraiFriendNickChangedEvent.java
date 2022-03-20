package me.dreamvoid.miraimc.waterdogpe.event;


import dev.waterdog.waterdogpe.event.Event;
import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import net.mamoe.mirai.event.events.FriendNickChangedEvent;

/**
 * 好友昵称改变
 */
public class MiraiFriendNickChangedEvent extends Event {
    public MiraiFriendNickChangedEvent(FriendNickChangedEvent event) {
        this.event = event;
    }

    private final FriendNickChangedEvent event;

   /* private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlers() { return handlers; }*/
    //public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     *
     * @return 机器人账号
     */
    public long getBotID() {
        return event.getBot().getId();
    }

    /**
     * 获取好友更名之前的昵称
     *
     * @return 昵称
     */
    public String getOldNick() {
        return event.getFrom();
    }

    /**
     * 获取好友更名之后的昵称
     *
     * @return 昵称
     */
    public String getNewNick() {
        return event.getTo();
    }

    /**
     * 获取哈希值
     *
     * @return 哈希值
     */
    public int getHashCode() {
        return event.hashCode();
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     *
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    /**
     * 获取好友实例
     *
     * @return MiraiFriend 实例
     */
    public MiraiFriend getFriend() {
        return new MiraiFriend(event.getBot(), event.getFriend().getId());
    }
}
