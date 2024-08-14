package me.dreamvoid.miraimc.velocity.event.message.passive;

import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import net.mamoe.mirai.event.events.GroupTempMessageEvent;

/**
 * (Velocity) 消息 - 被动收到消息 - 群临时会话消息
 */
@SuppressWarnings("unused")
public class MiraiGroupTempMessageEvent extends AbstractMessageEvent {
    public MiraiGroupTempMessageEvent(GroupTempMessageEvent event) {
        super(event);
        this.event = event;
    }

    private final GroupTempMessageEvent event;

    /**
     * 返回接收到这条信息的群号
     * @return 群号
     */
    public long getGroupID(){
        return event.getGroup().getId();
    }

    /**
     * 返回接收到这条信息的群名称
     * @return 群名称
     */
    public String getGroupName(){
        return event.getGroup().getName();
    }

    /**
     * 返回发送这条信息的发送者群名片
     * @return 发送者群名片
     */
    public String getSenderNameCard(){
        return event.getSender().getNameCard();
    }

    /**
     * 从临时会话获取群员实例
     * @return MiraiNormalMember 实例
     */
    public MiraiNormalMember getMember(){
        return new MiraiNormalMember(event.getGroup(), event.getSender().getId());
    }
}
