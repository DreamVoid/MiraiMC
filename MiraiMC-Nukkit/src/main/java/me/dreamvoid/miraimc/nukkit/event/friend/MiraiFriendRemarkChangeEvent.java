package me.dreamvoid.miraimc.nukkit.event.friend;

import net.mamoe.mirai.event.events.FriendRemarkChangeEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (bungee) Mirai 核心事件 - 好友 - 好友昵称改变
 */
public class MiraiFriendRemarkChangeEvent extends AbstractFriendEvent {
    public MiraiFriendRemarkChangeEvent(FriendRemarkChangeEvent event) {
        super(event);
        this.event = event;

        NukkitPlugin.getInstance().getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.nukkit.event.MiraiFriendRemarkChangeEvent(event));
    }

    private final FriendRemarkChangeEvent event;

    /**
     * 获取好友更名之前的昵称
     * @return 昵称
     */
    public String getOldRemark() {
        return event.getOldRemark();
    }

    /**
     * 获取好友更名之后的昵称
     * @return 昵称
     */
    public String getNewRemark() {
        return event.getNewRemark();
    }
}
