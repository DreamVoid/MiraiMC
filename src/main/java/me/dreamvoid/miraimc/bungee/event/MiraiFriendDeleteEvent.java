package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.FriendDeleteEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * 好友已被删除
 */
public class MiraiFriendDeleteEvent extends Event {
    public MiraiFriendDeleteEvent(FriendDeleteEvent event) {
        this.event = event;
    }

    private final FriendDeleteEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

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
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }
}
