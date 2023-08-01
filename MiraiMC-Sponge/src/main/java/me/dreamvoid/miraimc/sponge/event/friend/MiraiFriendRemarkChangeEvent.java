package me.dreamvoid.miraimc.sponge.event.friend;

import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.FriendRemarkChangeEvent;

/**
 * (Sponge) 好友 - 好友昵称改变
 */
@SuppressWarnings("unused")
public class MiraiFriendRemarkChangeEvent extends AbstractFriendEvent {
    public MiraiFriendRemarkChangeEvent(FriendRemarkChangeEvent event, Cause cause) {
        super(event, cause);
        this.event = event;
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
