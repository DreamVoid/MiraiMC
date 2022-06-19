package me.dreamvoid.miraimc.nukkit.event.group.setting;

import net.mamoe.mirai.event.events.GroupAllowAnonymousChatEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (Nukkit) Mirai 核心事件 - 群 - 群设置 - 群设置改变 - 匿名聊天状态改变
 */
public class MiraiGroupAllowAnonymousChatEvent extends AbstractGroupSettingChangeEvent {
    private final GroupAllowAnonymousChatEvent event;

    public MiraiGroupAllowAnonymousChatEvent(GroupAllowAnonymousChatEvent event) {
        super(event);
        this.event = event;

        NukkitPlugin.getInstance().getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.nukkit.event.MiraiGroupAllowAnonymousChatEvent(event));
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
     * 获取群当前是否允许匿名聊天
     * @return 是否允许匿名聊天
     */
    public boolean isAllowAnonymousChat(){
        return event.getNew();
    }

    /**
     * 获取群之前是否允许匿名聊天
     * @return 是否允许匿名聊天
     */
    public boolean isAllowAnonymousChatBefore(){
        return event.getOrigin();
    }
}
