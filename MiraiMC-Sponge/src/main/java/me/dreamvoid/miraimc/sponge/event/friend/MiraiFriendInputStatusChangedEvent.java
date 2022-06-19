package me.dreamvoid.miraimc.sponge.event.friend;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.FriendInputStatusChangedEvent;

/**
 * (Sponge) Mirai 核心事件 - 好友 - 好友输入状态改变
 */
public class MiraiFriendInputStatusChangedEvent extends AbstractFriendEvent {
    public MiraiFriendInputStatusChangedEvent(FriendInputStatusChangedEvent event, Cause cause) {
        super(event, cause);
        this.event = event;


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
