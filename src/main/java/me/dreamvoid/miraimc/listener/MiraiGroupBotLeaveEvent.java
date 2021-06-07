package me.dreamvoid.miraimc.listener;

import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.BotLeaveEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MiraiGroupBotLeaveEvent extends Event{

    public MiraiGroupBotLeaveEvent(BotLeaveEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final BotLeaveEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public Long getBotID() { return event.getBot().getId(); }

    /**
     * 获取机器人昵称
     * @return 机器人昵称
     */
    public String getBotNick() { return event.getBot().getNick(); }

    /**
     * 获取机器人的好友列表
     * 此方法只返回QQ号
     * @return 好友列表数组
     */
    public List<Long> getBotFriendList() {
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
    public List<Long> getBotGroupList() {
        ContactList<Group> GroupListOrigin = event.getBot().getGroups(); // 原始数组
        List<Long> GroupList = new ArrayList<>(); // 创建用于返回数据的数组
        for(Group Groups : GroupListOrigin){ GroupList.add(Groups.getId()); } // 为返回的数组加入成员
        return GroupList;
    }


}
