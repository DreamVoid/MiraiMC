package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Bot 登录完成
 */
public class MiraiBotOnlineEvent {

    public MiraiBotOnlineEvent(BotOnlineEvent event) {
        this.event = event;
    }

    private final BotOnlineEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getID() { return event.getBot().getId(); }

    /**
     * 获取机器人昵称
     * @return 机器人昵称
     */
    public String getNick() { return event.getBot().getNick(); }

    /**
     * 获取机器人的好友列表
     * 此方法只返回QQ号
     * @return 好友列表数组
     */
    public List<Long> getFriendList() {
        ContactList<Friend> FriendListOrigin = event.getBot().getFriends(); // 原始数组
        List<Long> FriendList = new ArrayList<>(); // 创建用于返回数据的数组
        for(Friend Friends : FriendListOrigin){ FriendList.add(Friends.getId()); }
        return FriendList;
    }

    /**
     * 获取机器人的群列表
     * 此方法只返回群号
     * @return 群列表数组
     */
    public List<Long> getGroupList() {
        ContactList<Group> GroupListOrigin = event.getBot().getGroups(); // 原始数组
        List<Long> GroupList = new ArrayList<>(); // 创建用于返回数据的数组
        for(Group Groups : GroupListOrigin){ GroupList.add(Groups.getId()); } // 为返回的数组加入成员
        return GroupList;
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
