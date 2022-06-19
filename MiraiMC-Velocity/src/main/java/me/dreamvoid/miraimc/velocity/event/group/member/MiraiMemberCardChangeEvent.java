package me.dreamvoid.miraimc.velocity.event.group.member;

import me.dreamvoid.miraimc.velocity.VelocityPlugin;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;

/**
 * (Velocity) Mirai 核心事件 - 群 - 群成员 - 名片和头衔 - 成员群名片改动
 */
public class MiraiMemberCardChangeEvent extends AbstractGroupMemberEvent {
    private final MemberCardChangeEvent event;

    public MiraiMemberCardChangeEvent(MemberCardChangeEvent event) {
        super(event);
        this.event = event;

        VelocityPlugin.INSTANCE.getServer().getEventManager().fire(new me.dreamvoid.miraimc.velocity.event.MiraiGroupMemberCardChangeEvent(event));
    }

    /**
     * 获取群成员更改之前的名片
     * @return 群名片
     */
    public String getOldNick() {
        return event.getOrigin();
    }

    /**
     * 获取群成员更改之后的名片
     * @return 群名片
     */
    public String getNewNick() {
        return event.getNew();
    }

    /**
     * 获取群成员QQ号
     * @return QQ号
     */
    public long getMemberID(){
        return event.getMember().getId();
    }
}
