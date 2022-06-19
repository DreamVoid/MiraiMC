package me.dreamvoid.miraimc.nukkit.event.friend;

import net.mamoe.mirai.event.events.FriendNickChangedEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (Nukkit) Mirai 核心事件 - 好友 - 好友昵称改变
 */
public class MiraiFriendNickChangedEvent extends AbstractFriendEvent {
    public MiraiFriendNickChangedEvent(FriendNickChangedEvent event) {
        super(event);
        this.event = event;

        NukkitPlugin.getInstance().getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.nukkit.event.MiraiFriendNickChangedEvent(event));
    }

    private final FriendNickChangedEvent event;

    /**
     * 获取好友更名之前的昵称
     * @return 昵称
     */
    public String getOldNick() {
        return event.getFrom();
    }

    /**
     * 获取好友更名之后的昵称
     * @return 昵称
     */
    public String getNewNick() {
        return event.getTo();
    }
}
