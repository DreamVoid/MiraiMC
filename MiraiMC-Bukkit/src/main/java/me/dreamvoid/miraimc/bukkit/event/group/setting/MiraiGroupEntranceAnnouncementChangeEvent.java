package me.dreamvoid.miraimc.bukkit.event.group.setting;

import net.mamoe.mirai.event.events.GroupEntranceAnnouncementChangeEvent;
import org.bukkit.Bukkit;

/**
 * (Bukkit) Mirai 核心事件 - 群 - 群设置 - 群设置改变 - 入群公告改变
 */
public class MiraiGroupEntranceAnnouncementChangeEvent extends AbstractGroupSettingChangeEvent {
    public MiraiGroupEntranceAnnouncementChangeEvent(GroupEntranceAnnouncementChangeEvent event) {
        super(event);
        this.event = event;


    }

    private final GroupEntranceAnnouncementChangeEvent event;

    /**
     * 获取群号
     * @return 群号
     */
    @Override
    public long getGroupID() {
        return event.getGroupId();
    }

    /**
     * 获取操作管理员QQ号
     * @return QQ号
     */
    public long getOperatorID(){
        if(event.getOperator()!=null){
            return event.getOperator().getId();
        } else return 0L;
    }

    /**
     * 获取更换前的群公告内容
     * @return 群公告内容
     */
    public String getOrigin(){
        return event.getOrigin();
    }

    /**
     * 获取更换后的群公告内容
     * @return 群公告内容
     */
    public String getNew(){
        return event.getNew();
    }
}
