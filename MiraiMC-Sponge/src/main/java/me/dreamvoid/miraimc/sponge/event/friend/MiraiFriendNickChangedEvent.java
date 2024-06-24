package me.dreamvoid.miraimc.sponge.event.friend;

import org.spongepowered.api.event.Cause;
import net.mamoe.mirai.event.events.FriendNickChangedEvent;

/**
 * (Sponge) 好友 - 好友昵称改变
 */
@SuppressWarnings("unused")
public class MiraiFriendNickChangedEvent extends AbstractFriendEvent {
    public MiraiFriendNickChangedEvent(FriendNickChangedEvent event, Cause cause) {
        super(event, cause);
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
