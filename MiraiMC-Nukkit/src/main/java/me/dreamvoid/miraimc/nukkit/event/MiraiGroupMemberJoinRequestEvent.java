package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.nukkit.event.group.member.MiraiMemberJoinRequestEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;

/**
 * 群成员 - 成员列表变更 - 一个账号请求加入群
 * @deprecated
 * @see MiraiMemberJoinRequestEvent
 */
@Deprecated
public class MiraiGroupMemberJoinRequestEvent extends MiraiMemberJoinRequestEvent {
    public MiraiGroupMemberJoinRequestEvent(MemberJoinRequestEvent event) {
        super(event);
        this.event = event;
    }

    private final MemberJoinRequestEvent event;

    /**
     * 返回请求加群的成员的QQ号
     * @return 成员QQ号
     */
    public long getRequestMemberID() { return getFromID(); }

    /**
     * 返回邀请者的QQ号
     * 如果没有邀请者，则返回 0
     * @return 邀请者QQ号
     */
    public long getInviterID(){
        return getInvitorID();
    }

    /**
     * 同意请求
     */
    public void setAccept(){
        accept();
    }

    /**
     * 忽略请求
     * @param setBlacklist 是否拒绝目标再次申请加群
     */
    public void setIgnore(boolean setBlacklist){
        ignore(setBlacklist);
    }

    /**
     * 拒绝请求
     * @param setBlacklist 是否拒绝目标再次申请加群
     */
    public void setDeny(boolean setBlacklist){
        reject(setBlacklist);
    }

    /**
     * 拒绝请求
     */
    public void setDeny(){
        reject();
    }

    /**
     * 拒绝请求
     * @param setBlacklist 是否拒绝目标再次申请加群
     * @param reason 拒绝原因
     */
    public void setDeny(boolean setBlacklist, String reason){
        reject(setBlacklist,reason);
    }

    /**
     * 拒绝请求
     * @param reason 拒绝原因
     */
    public void setDeny(String reason){
        reject(reason);
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }
}
