package me.dreamvoid.miraimc.sponge.event.group.setting;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.GroupNameChangeEvent;

/**
 * (Sponge) Mirai 核心事件 - 群 - 群设置 - 群设置改变 - 群名改变
 */
public class MiraiGroupNameChangeEvent extends AbstractGroupSettingChangeEvent {
    public MiraiGroupNameChangeEvent(GroupNameChangeEvent event, Cause cause) {
        super(event, cause);
        this.event = event;

        Sponge.getEventManager().post(new me.dreamvoid.miraimc.sponge.event.MiraiGroupNameChangeEvent(event, cause));
    }

    private final GroupNameChangeEvent event;

    /**
     * 获取更换前的名称
     * @return 群名称
     */
    public String getGroupOldName(){
        return event.getOrigin();
    }

    /**
     * 获取更换后的名称
     * @return 群名称
     */
    public String getGroupNewName(){
        return event.getNew();
    }

    /**
     * 获取群号
     * @return 群号
     */
    @Override
    public long getGroupID(){
        return event.getGroupId();
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
