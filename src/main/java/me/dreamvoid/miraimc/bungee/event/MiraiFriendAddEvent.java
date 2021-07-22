package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.FriendAddEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * 成功添加了一个新好友
 */
public class MiraiFriendAddEvent extends Event {
    public MiraiFriendAddEvent(FriendAddEvent event) {
        this.event = event;
    }

    private final FriendAddEvent event;

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
}
