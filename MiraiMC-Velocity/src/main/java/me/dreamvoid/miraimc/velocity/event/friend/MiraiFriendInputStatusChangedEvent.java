package me.dreamvoid.miraimc.velocity.event.friend;

import me.dreamvoid.miraimc.velocity.VelocityPlugin;
import net.mamoe.mirai.event.events.FriendInputStatusChangedEvent;

/**
 * (bungee) Mirai 核心事件 - 好友 - 好友输入状态改变
 */
public class MiraiFriendInputStatusChangedEvent extends AbstractFriendEvent {
    public MiraiFriendInputStatusChangedEvent(FriendInputStatusChangedEvent event) {
        super(event);
        this.event = event;

        VelocityPlugin.INSTANCE.getServer().getEventManager().fire(new me.dreamvoid.miraimc.velocity.event.MiraiFriendInputStatusChangedEvent(event));
    }

    private final FriendInputStatusChangedEvent event;

    /**
     * 判断好友是否正在输入
     * @return 是否正在输入
     */
    public boolean isInputting(){
        return event.getInputting();
    }
}
