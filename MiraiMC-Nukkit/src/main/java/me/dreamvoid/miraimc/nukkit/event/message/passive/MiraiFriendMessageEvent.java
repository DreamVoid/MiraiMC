package me.dreamvoid.miraimc.nukkit.event.message.passive;

import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import me.dreamvoid.miraimc.event.EventType;
import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.httpapi.exception.AbnormalStatusException;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;

import java.io.IOException;

/**
 * (Nukkit) Mirai 核心事件 - 消息 - 被动收到消息 - 好友消息
 */
public class MiraiFriendMessageEvent extends AbstractMessageEvent {
    public MiraiFriendMessageEvent(FriendMessageEvent event) {
        super(event);
        this.event = event;

        type = EventType.CORE;
        BotID = event.getBot().getId();

        SenderID = event.getSender().getId();
        NickName = event.getSender().getNick();

        MessageContent = event.getMessage().contentToString();
        MessageMiraiCode = event.getMessage().serializeToMiraiCode();
        Time = event.getTime();
    }
    public MiraiFriendMessageEvent(long BotAccount, FetchMessage.Data data) {
        type = EventType.HTTP_API;
        BotID = BotAccount;

        SenderID = data.sender.id;
        NickName = data.sender.nickname;

        for(FetchMessage.Data.MessageChain chain : data.messageChain){
            switch (chain.type){
                case "Source":
                    Time = chain.time;
                    break;
                case "Code":
                    MessageMiraiCode = chain.code;
                    break;
                case "Plain":
                    MessageContent = String.format("%s%s", MessageContent, chain.text);
                    break;
                default:
                    MessageContent = String.format("%s[%s]", MessageContent, chain.type);
                    break;
            }
        }
    }

    private FriendMessageEvent event;

    private final EventType type;
    private final long BotID;
    private final long SenderID;
    private final String NickName;
    private String MessageContent;
    private String MessageMiraiCode;
    private int Time;

    /**
     * 返回接收到这条信息的机器人ID
     * @return 机器人ID
     */
    @Override
    public long getBotID(){
        return BotID;
    }

    /**
     * 返回发送这条信息的发送者ID
     * @return 发送者ID
     */
    @Override
    public long getSenderID(){
        return SenderID;
    }

    /**
     * 返回发送这条信息的发送者昵称
     * @return 发送者昵称
     */
    @Override
    public String getSenderName(){
        return NickName;
    }

    /**
     * 返回接收到的消息内容转换到字符串的结果<br>
     * 此方法使用 contentToString()<br>
     * QQ 对话框中以纯文本方式会显示的消息内容，这适用于MC与QQ的消息互通等不方便展示原始内容的场景。<br>
     * 无法用纯文字表示的消息会丢失信息，如任何图片都是 [图片]
     * @return 转换字符串后的消息内容
     */
    @Override
    public String getMessage(){
        return MessageContent;
    }

    /**
     * 返回接收到的消息内容转换到Mirai Code的结果<br>
     * 此方法使用 serializeToMiraiCode()<br>
     * 转换为对应的 Mirai 码，消息的一种序列化方式
     * @return 带Mirai Code的消息内容
     */
    @Override
    public String getMessageToMiraiCode(){
        return MessageMiraiCode;
    }

    /**
     * 返回接收到这条信息的时间
     * @return 发送时间
     */
    @Override
    public int getTime() {
        return Time;
    }

    /**
     * 向发送来源发送消息（支持 Mirai Code）
     * @param message 消息内容
     */
    @Override
    public void sendMessage(String message) {
        if(type == EventType.CORE){
            event.getSender().sendMessage(MiraiCode.deserializeMiraiCode(message));
        } else if(type == EventType.HTTP_API){
            try {
                MiraiHttpAPI.INSTANCE.sendFriendMessage(MiraiHttpAPI.Bots.get(BotID), SenderID, message);
            } catch (IOException | AbnormalStatusException e) {
                Utils.logger.warning("发送消息时出现异常，原因: " + e);
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

    /**
     * 获取事件类型（用于判断机器人工作模式）
     * @return 0 = Core | 1 = HTTP API
     */
    public EventType getType() {
        return type;
    }
}
