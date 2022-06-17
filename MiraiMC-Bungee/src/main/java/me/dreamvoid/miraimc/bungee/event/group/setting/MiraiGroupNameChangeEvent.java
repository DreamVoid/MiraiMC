package me.dreamvoid.miraimc.bungee.event.group.setting;

import net.mamoe.mirai.event.events.GroupNameChangeEvent;
import net.md_5.bungee.api.ProxyServer;

/**
 * (bungee) Mirai 核心事件 - 群 - 群设置 - 群设置改变 - 群名改变
 */
public class MiraiGroupNameChangeEvent extends AbstractGroupSettingChangeEvent {
    public MiraiGroupNameChangeEvent(GroupNameChangeEvent event) {
        super(event);
        this.event = event;

        ProxyServer.getInstance().getPluginManager().callEvent(new me.dreamvoid.miraimc.bungee.event.MiraiGroupNameChangeEvent(event));
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
