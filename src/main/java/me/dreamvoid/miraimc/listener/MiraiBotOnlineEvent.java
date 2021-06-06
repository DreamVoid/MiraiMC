package me.dreamvoid.miraimc.listener;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.BotOnlineEvent;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MiraiBotOnlineEvent extends Event {

    public MiraiBotOnlineEvent(BotOnlineEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final BotOnlineEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人实例
     * @return 机器人
     */
    public Bot getBot() { return event.getBot(); }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public Long getID() { return event.getBot().getId(); }

    /**
     * 获取机器人昵称
     * @return 机器人昵称
     */
    public String getNick() { return event.getBot().getNick(); }

    /**
     * 获取机器人的好友列表
     * 此方法只返回QQ号，如需原始数组，请调用 getBot() 方法
     * @return 好友列表数组
     */
    public List<Long> getFriendList() {
        ContactList<Friend> FriendListOrigin = event.getBot().getFriends();
        List<Long> FriendList = new ArrayList<>();
        for(Friend Friends : FriendListOrigin){ FriendList.add(Friends.getId()); }
        return FriendList;
    }

    /**
     * 获取机器人的群列表
     * 此方法只返回群号，如需原始数组，请调用 getBot() 方法
     * @return 群列表数组
     */
    public List<Long> getGroupList() {
        ContactList<Group> GroupListOrigin = event.getBot().getGroups();
        List<Long> GroupList = new ArrayList<>();
        for(Group Groups : GroupListOrigin){ GroupList.add(Groups.getId()); }
        return GroupList;
    }
    
}
