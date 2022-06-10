package me.dreamvoid.miraimc.bukkit.event.bot;

import net.mamoe.mirai.event.events.NewFriendRequestEvent;

/**
 * 一个账号请求添加机器人为好友
 */
public class MiraiNewFriendRequestEvent extends AbstractBotEvent {
    public MiraiNewFriendRequestEvent(NewFriendRequestEvent event) {
        super(event);
        this.event = event;
    }

    private final NewFriendRequestEvent event;

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
    public void accept(){
        event.accept();
    }

    /**
     * 接受请求
     * @deprecated
     * @see #accept()
     */
    @Deprecated
    public void setAcceptRequest(){
        event.accept();
    }

    /**
     * 拒绝请求
     * @param Blacklist 是否加入黑名单
     */
    public void reject(boolean Blacklist){
        event.reject(Blacklist);
    }

    /**
     * 拒绝请求
     * @param setBlacklist 是否加入黑名单
     * @deprecated
     * @see #reject(boolean)
     */
    @Deprecated
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
     * @return 群号
     */
    public long getFromGroupID(){
        return event.getFromGroupId();
    }
}
