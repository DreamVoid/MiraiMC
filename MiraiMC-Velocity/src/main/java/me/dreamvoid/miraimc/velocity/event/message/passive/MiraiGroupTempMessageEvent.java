package me.dreamvoid.miraimc.velocity.event.message.passive;

import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import net.mamoe.mirai.event.events.GroupTempMessageEvent;

/**
 * (Velocity) 消息 - 被动收到消息 - 群临时会话消息
 */
public class MiraiGroupTempMessageEvent extends AbstractMessageEvent {
    public MiraiGroupTempMessageEvent(GroupTempMessageEvent event) {
        super(event);
        this.event = event;

        GroupID = event.getGroup().getId();
        GroupName = event.getGroup().getName();
        MemberName = event.getSender().getNameCard();
    }

    public MiraiGroupTempMessageEvent(long BotID, FetchMessage.Data data){
        super(BotID, data);

        GroupID = data.sender.group.id;
        GroupName = data.sender.group.name;
        MemberName = data.sender.memberName;
    }

    private GroupTempMessageEvent event;

    private final long GroupID;
    private final String GroupName;
    private final String MemberName;

    /**
     * 返回接收到这条信息的群号
     * @return 群号
     */
    public long getGroupID(){
        return GroupID;
    }

    /**
     * 返回接收到这条信息的群名称
     * @return 群名称
     */
    public String getGroupName(){
        return GroupName;
    }

    /**
     * 返回发送这条信息的发送者群名片
     * @return 发送者群名片
     */
    public String getSenderNameCard(){
        return MemberName;
    }

    /**
     * 从临时会话获取群员实例
     * @return MiraiNormalMember 实例
     */
    public MiraiNormalMember getMember(){
        return new MiraiNormalMember(event.getGroup(), event.getSender().getId());
    }
}
