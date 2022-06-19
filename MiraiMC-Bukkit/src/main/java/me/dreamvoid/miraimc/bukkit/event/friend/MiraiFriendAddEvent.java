package me.dreamvoid.miraimc.bukkit.event.friend;

import net.mamoe.mirai.event.events.FriendAddEvent;
import org.bukkit.Bukkit;

/**
 * (Bukkit) Mirai 核心事件 - 好友 - 成功添加了一个新好友
 */
public class MiraiFriendAddEvent extends AbstractFriendEvent {
    public MiraiFriendAddEvent(FriendAddEvent event) {
        super(event);
    }
}
