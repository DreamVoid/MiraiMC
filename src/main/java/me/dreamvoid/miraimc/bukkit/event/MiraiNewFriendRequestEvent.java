package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 一个账号请求添加机器人为好友
 */
public class MiraiNewFriendRequestEvent extends Event {
    public MiraiNewFriendRequestEvent(NewFriendRequestEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final NewFriendRequestEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 获取事件ID
     * @return 事件ID
     */
    public long getEventID() {
        return event.getEventId();
    }

    /**
     * 接受请求
     */
    public void setAcceptRequset(){
        event.accept();
    }

    /**
     * 拒绝请求
     * @param setBlacklist 是否加入黑名单
     */
    public void setDenyRequest(boolean setBlacklist){
        event.reject(setBlacklist);
    }

    /**
     * 获取请求者QQ号
     * @return QQ号
     */
    public long getFromID(){
        return event.getFromId();
    }

    /**
     * 获取请求者昵称
     * @return 昵称
     */
    public String getFromNick(){
        return event.getFromNick();
    }

    /**
     * 获取来源群名称
     * 如果不存在来源群，则返回空文本
     * @return 群名称
     */
    public String getFromGroupName(){
        if(event.getFromGroup()!=null){
            return event.getFromGroup().getName();
        } else return "";
    }

    /**
     * 获取来源群号
     * # TO DO: 如果群号不存在，返回什么？
     * @return 群号
     */
    public long getFromGroupID(){
        return event.getFromGroupId();
    }

    /**
     * 获取哈希值
     * @return 哈希值
     */
    public int getHashCode(){
        return event.hashCode();
    }
}