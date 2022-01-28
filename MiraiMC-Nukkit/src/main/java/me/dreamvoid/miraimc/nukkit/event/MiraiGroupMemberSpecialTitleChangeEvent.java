package me.dreamvoid.miraimc.nukkit.event;

import cn.nukkit.event.HandlerList;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import net.mamoe.mirai.event.events.MemberSpecialTitleChangeEvent;
import cn.nukkit.event.Event;

/**
 * 群成员 - 名片和头衔 - 成员群头衔改动
 */
public class MiraiGroupMemberSpecialTitleChangeEvent extends Event {
    public MiraiGroupMemberSpecialTitleChangeEvent(MemberSpecialTitleChangeEvent event) {
        this.event = event;
    }

    private final MemberSpecialTitleChangeEvent event;

    private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlers() { return handlers; }
    //public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 获取群成员更改之前的特殊头衔
     * @return 群头衔
     */
    public String getOldNick() {
        return event.getOrigin();
    }

    /**
     * 获取群成员更改之后的特殊头衔
     * @return 群头衔
     */
    public String getNewNick() {
        return event.getNew();
    }

    /**
     * 获取哈希值
     * @return 哈希值
     */
    public int getHashCode() {
        return event.hashCode();
    }

    /**
     * 获取群成员QQ号
     * @return QQ号
     */
    public long getMemberID(){
        return event.getMember().getId();
    }

    /**
     * 获取操作管理员QQ号
     * 如果不存在，返回0
     * @return QQ号
     */
    public long getOperatorID(){
        if(event.getOperator()!=null){
            return event.getOperator().getId();
        } else return 0L;
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    /**
     * 获取群实例
     * @return MiraiGroup 实例
     */
    public MiraiGroup getGroup(){
        return new MiraiGroup(event.getBot(), event.getGroup().getId());
    }

    /**
     * 获取群员实例
     * @return MiraiNormalMember 实例
     */
    public MiraiNormalMember getMember(){
        return new MiraiNormalMember(event.getGroup(), event.getMember().getId());
    }
}
