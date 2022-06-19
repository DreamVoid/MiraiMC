package me.dreamvoid.miraimc.sponge.event.friend;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.FriendAddEvent;

/**
 * (Sponge) Mirai 核心事件 - 好友 - 成功添加了一个新好友
 */
public class MiraiFriendAddEvent extends AbstractFriendEvent {
    public MiraiFriendAddEvent(FriendAddEvent event, Cause cause) {
        super(event, cause);
    }
}
