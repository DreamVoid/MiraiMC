package me.dreamvoid.miraimc.velocity.event.friend;

import net.mamoe.mirai.event.events.FriendNickChangedEvent;

/**
 * (Velocity) 好友 - 好友昵称改变
 */
@SuppressWarnings("unused")
public class MiraiFriendNickChangedEvent extends AbstractFriendEvent {
    public MiraiFriendNickChangedEvent(FriendNickChangedEvent event) {
        super(event);
        this.event = event;
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
