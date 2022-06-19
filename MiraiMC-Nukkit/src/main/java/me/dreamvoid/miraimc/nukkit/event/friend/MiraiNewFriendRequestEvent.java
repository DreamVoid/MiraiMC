package me.dreamvoid.miraimc.nukkit.event.friend;

import me.dreamvoid.miraimc.nukkit.event.bot.AbstractBotEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (Nukkit) Mirai 核心事件 - 好友 - 一个账号请求添加机器人为好友
 */
public class MiraiNewFriendRequestEvent extends AbstractBotEvent {
    public MiraiNewFriendRequestEvent(NewFriendRequestEvent event) {
        super(event);
        this.event = event;

        NukkitPlugin.getInstance().getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.nukkit.event.MiraiNewFriendRequestEvent(event));
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
     * 拒绝请求
     * @param Blacklist 是否加入黑名单
     */
    public void reject(boolean Blacklist){
        event.reject(Blacklist);
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
     * 获取来源群号<br>
     * 其他途径时为 0
     * @return 群号
     */
    public long getFromGroupID(){
        return event.getFromGroupId();
    }
}
