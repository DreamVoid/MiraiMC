package me.dreamvoid.miraimc.velocity.event.friend;

import me.dreamvoid.miraimc.velocity.VelocityPlugin;
import net.mamoe.mirai.event.events.FriendNickChangedEvent;

/**
 * (Velocity) Mirai 核心事件 - 好友 - 好友昵称改变
 */
public class MiraiFriendNickChangedEvent extends AbstractFriendEvent {
    public MiraiFriendNickChangedEvent(FriendNickChangedEvent event) {
        super(event);
        this.event = event;

        VelocityPlugin.INSTANCE.getServer().getEventManager().fire(new me.dreamvoid.miraimc.velocity.event.MiraiFriendNickChangedEvent(event));
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
