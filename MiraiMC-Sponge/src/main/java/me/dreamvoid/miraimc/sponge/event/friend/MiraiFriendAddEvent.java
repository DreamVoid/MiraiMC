package me.dreamvoid.miraimc.sponge.event.friend;

import org.spongepowered.api.event.Cause;
import net.mamoe.mirai.event.events.FriendAddEvent;

/**
 * (Sponge) 好友 - 成功添加了一个新好友
 */
@SuppressWarnings("unused")
public class MiraiFriendAddEvent extends AbstractFriendEvent {
    public MiraiFriendAddEvent(FriendAddEvent event, Cause cause) {
        super(event, cause);
    }
}
