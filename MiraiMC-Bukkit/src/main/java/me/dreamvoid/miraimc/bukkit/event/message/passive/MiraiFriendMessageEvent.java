package me.dreamvoid.miraimc.bukkit.event.message.passive;

import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import net.mamoe.mirai.event.events.FriendMessageEvent;

/**
 * (Bukkit) 消息 - 被动收到消息 - 好友消息
 */
@SuppressWarnings("unused")
public class MiraiFriendMessageEvent extends AbstractMessageEvent {
    public MiraiFriendMessageEvent(FriendMessageEvent event) {
        super(event);
        this.event = event;
    }

    private final FriendMessageEvent event;

    /**
     * 返回发送这条信息的发送者昵称
     * @return 发送者昵称
     */
    @Override
    public String getSenderName(){
        return event.getSender().getNick();
    }

    /**
     * 获取好友实例
     * @return MiraiFriend 实例
     */
    public MiraiFriend getFriend(){
        return new MiraiFriend(event.getBot(), getSenderID());
    }
}
