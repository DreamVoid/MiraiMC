package me.dreamvoid.miraimc.sponge.event.bot;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.BotOnlineEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * (Sponge) Bot - Bot 登录完成
 */
public class MiraiBotOnlineEvent extends AbstractBotEvent {
    public MiraiBotOnlineEvent(BotOnlineEvent event, Cause cause) {
        super(event, cause);
        this.event = event;
    }

    private final BotOnlineEvent event;

    /**
     * 获取机器人的好友列表
     * 此方法只返回QQ号
     * @return 好友列表数组
     * @see MiraiBot#getFriendList()
     * @deprecated 请使用 {@link MiraiBot#getFriendList()}
     */
    @Deprecated
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
     * @see MiraiBot#getGroupList()
     * @deprecated 请使用 {@link MiraiBot#getGroupList()}
     */
    @Deprecated
    public List<Long> getGroupList() {
        ContactList<Group> GroupListOrigin = event.getBot().getGroups(); // 原始数组
        List<Long> GroupList = new ArrayList<>(); // 创建用于返回数据的数组
        for(Group Groups : GroupListOrigin){ GroupList.add(Groups.getId()); } // 为返回的数组加入成员
        return GroupList;
    }
}
