package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 一个账号请求添加机器人为好友
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.friend.MiraiNewFriendRequestEvent
 */
@Deprecated
public class MiraiNewFriendRequestEvent extends me.dreamvoid.miraimc.sponge.event.friend.MiraiNewFriendRequestEvent {
    public MiraiNewFriendRequestEvent(NewFriendRequestEvent event, Cause cause) {
        super(event, cause);
    }
}
