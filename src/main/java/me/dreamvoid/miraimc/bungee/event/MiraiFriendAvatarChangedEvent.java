package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * 好友头像改变
 */
public class MiraiFriendAvatarChangedEvent extends Event {
    public MiraiFriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
        this.event = event;
    }

    private final FriendAvatarChangedEvent event;

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
