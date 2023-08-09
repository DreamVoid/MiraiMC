package me.dreamvoid.miraimc.velocity.event.message.passive;

import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import me.dreamvoid.miraimc.event.EventType;
import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.httpapi.exception.AbnormalStatusException;
import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.event.events.FriendMessageEvent;

import java.io.IOException;

/**
 * (Velocity) 消息 - 被动收到消息 - 好友消息
 */
@SuppressWarnings("unused")
public class MiraiFriendMessageEvent extends AbstractMessageEvent {
    public MiraiFriendMessageEvent(FriendMessageEvent event) {
        super(event);
        this.event = event;

        BotID = event.getBot().getId();

        SenderID = event.getSender().getId();
        NickName = event.getSender().getNick();
    }

    public MiraiFriendMessageEvent(long BotAccount, FetchMessage.Data data) {
        super(BotAccount, data);

        BotID = BotAccount;

        SenderID = data.sender.id;
        NickName = data.sender.nickname;
    }

    private FriendMessageEvent event;

    private final long BotID;
    private final long SenderID;
    private final String NickName;

    /**
     * 返回发送这条信息的发送者昵称
     * @return 发送者昵称
     */
    @Override
    public String getSenderName(){
        return NickName;
    }

    /**
     * 向发送来源发送消息（HTTPAPI下支持 Mirai Code）
     * @param message 消息内容
     */
    @Override
    public void sendMessage(String message) {
        if(getType() == EventType.CORE){
            super.sendMessage(message);
        } else if(getType() == EventType.HTTPAPI){
            try {
                MiraiHttpAPI.INSTANCE.sendFriendMessage(MiraiHttpAPI.Bots.get(BotID), SenderID, message);
            } catch (IOException | AbnormalStatusException e) {
                Utils.getLogger().warning("发送消息时出现异常，原因: " + e);
            }
        }
    }

    /**
     * 向发送来源发送消息（支持 Mirai Code）
     * @param message 消息内容
     */
    @Override
    public void sendMessageMirai(String message) {
        if(getType() == EventType.CORE){
            super.sendMessageMirai(message);
        } else if(getType() == EventType.HTTPAPI){
            try {
                MiraiHttpAPI.INSTANCE.sendFriendMessage(MiraiHttpAPI.Bots.get(BotID), SenderID, message);
            } catch (IOException | AbnormalStatusException e) {
                Utils.getLogger().warning("发送消息时出现异常，原因: " + e);
            }
        }
    }

    /**
     * 获取好友实例
     * @return MiraiFriend 实例
     */
    public MiraiFriend getFriend(){
        return new MiraiFriend(event.getBot(), getSenderID());
    }
}
