package me.dreamvoid.miraimc.sponge.event.group.member;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import me.dreamvoid.miraimc.sponge.event.bot.AbstractBotEvent;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;

/**
 * (bungee) Mirai 核心事件 - 群 - 群成员 - 成员列表变更 - 机器人被邀请加入群
 */
public class MiraiBotInvitedJoinGroupRequestEvent extends AbstractBotEvent {

    public MiraiBotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent event, Cause cause) {
        super(event, cause);
        this.event = event;

        Sponge.getEventManager().post(new me.dreamvoid.miraimc.sponge.event.MiraiGroupBotInvitedJoinGroupRequestEvent(event, cause));
    }

    private final BotInvitedJoinGroupRequestEvent event;

    /**
     * 返回目标群的群号
     * @return 群号
     */
    public long getGroupID() { return event.getGroupId(); }

    /**
     * 返回目标群的群名称
     * @return 群名称
     */
    public String getGroupName() { return event.getGroupName(); }

    /**
     * 返回邀请者的昵称
     * @return 邀请者昵称
     */
    public String getInvitorNick() { return event.getInvitorNick(); }

    /**
     * 返回邀请者的QQ号
     * @return 邀请者QQ号
     */
    public long getInvitorID(){ return event.getInvitorId(); }

    /**
     * 获取事件ID用于处理加群事件
     * @return 事件ID
     */
    public long getEventID(){ return event.getEventId(); }

    /**
     * 同意请求
     */
    public void accept(){
        event.accept();
        event.getBot().getLogger().info("[EventInvite/"+ getBotID()+"] "+ getGroupID()+"("+ getBotID() +"|"+getInvitorID()+") <- Accept");
    }

    /**
     * 忽略请求
     */
    public void ignore(){
        event.ignore();
        event.getBot().getLogger().info("[EventInvite/"+ getBotID()+"] "+ getGroupID()+"("+ getBotID() +"|"+getInvitorID()+") <- Ignore");
    }
}
