package me.dreamvoid.miraimc.velocity.event.friend;

import me.dreamvoid.miraimc.velocity.VelocityPlugin;
import net.mamoe.mirai.event.events.FriendAvatarChangedEvent;

/**
 * (bungee) Mirai 核心事件 - 好友 - 好友头像改变
 */
public class MiraiFriendAvatarChangedEvent extends AbstractFriendEvent {
    public MiraiFriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
        super(event);

        VelocityPlugin.INSTANCE.getServer().getEventManager().fire(new me.dreamvoid.miraimc.velocity.event.MiraiFriendAvatarChangedEvent(event));
    }
}
