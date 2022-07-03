package me.dreamvoid.miraimc.sponge.event.group.member;

import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.MemberSpecialTitleChangeEvent;

/**
 * (Sponge) 群 - 群成员 - 名片和头衔 - 成员群特殊头衔改动
 */
public class MiraiMemberSpecialTitleChangeEvent extends AbstractGroupMemberEvent {
    public MiraiMemberSpecialTitleChangeEvent(MemberSpecialTitleChangeEvent event, Cause cause) {
        super(event, cause);
        this.event = event;
    }

    private final MemberSpecialTitleChangeEvent event;

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
}
