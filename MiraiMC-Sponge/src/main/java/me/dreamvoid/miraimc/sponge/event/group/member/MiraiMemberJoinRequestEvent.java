package me.dreamvoid.miraimc.sponge.event.group.member;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import org.spongepowered.api.event.Cause;
import me.dreamvoid.miraimc.sponge.event.bot.AbstractBotEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;

/**
 * (Sponge) 群 - 群成员 - 成员列表变更 - 一个账号请求加入群
 */
@SuppressWarnings("unused")
public class MiraiMemberJoinRequestEvent extends AbstractBotEvent {
    private final MemberJoinRequestEvent event;

    public MiraiMemberJoinRequestEvent(MemberJoinRequestEvent event, Cause cause) {
        super(event, cause);
        this.event = event;
    }

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
     * 返回请求加群的成员的QQ号
     * @return 成员QQ号
     */
    public long getFromID() { return event.getFromId(); }

    /**
     * 返回邀请者的QQ号
     * 如果没有邀请者，则返回 0
     * @return 邀请者QQ号
     */
    public long getInvitorID(){
        return event.getInvitorId() != null ? event.getInvitorId() : 0;
    }

    /**
     * 获取事件ID用于处理加群事件
     * @return 事件ID
     */
    public long getEventID(){ return event.getEventId(); }

    /**
     * 获取加群时填写的附言
     * @return 附言
     */
    public String getMessage(){ return event.getMessage(); }

    /**
     * 同意请求
     */
    public void accept(){
        event.accept();
        event.getBot().getLogger().info("[EventInvite/"+ getBotID()+"] "+ getGroupID()+"("+ getFromID() +"|"+getInvitorID()+") <- Accept");
    }

    /**
     * 忽略请求
     * @param Blacklist 是否拒绝目标再次申请加群
     */
    public void ignore(boolean Blacklist){
        event.ignore(Blacklist);
        event.getBot().getLogger().info("[EventInvite/"+ getBotID()+"] "+ getGroupID()+"("+getFromID() +"|"+getInvitorID()+") <- Deny");
    }

    /**
     * 拒绝请求
     * @param Blacklist 是否拒绝目标再次申请加群
     */
    public void reject(boolean Blacklist){
        reject(Blacklist, "");
    }

    /**
     * 拒绝请求
     */
    public void reject() {
        reject(false, "");
    }

    /**
     * 拒绝请求
     * @param Blacklist 是否拒绝目标再次申请加群
     * @param reason 拒绝原因
     */
    public void reject(boolean Blacklist, String reason){
        event.reject(Blacklist, reason);
        event.getBot().getLogger().info("[EventInvite/"+ getBotID()+"] "+ getGroupID()+"("+getFromID() +"|"+getInvitorID()+") <- Deny");
    }

    /**
     * 拒绝请求
     * @param reason 拒绝原因
     */
    public void reject(String reason){
        reject(false, reason);
    }

    /**
     * 获取群实例
     * @return MiraiGroup 实例
     */
    public MiraiGroup getGroup(){
        return event.getGroup() != null ? new MiraiGroup(event.getBot(), event.getGroup().getId()) : null;

    }
}
