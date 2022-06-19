package me.dreamvoid.miraimc.nukkit.event.message.passive;

import net.mamoe.mirai.event.events.StrangerMessageEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (Nukkit) Mirai 核心事件 - 消息 - 被动收到消息 - 陌生人消息
 */
public class MiraiStrangerMessageEvent extends AbstractMessageEvent {

    public MiraiStrangerMessageEvent(StrangerMessageEvent event) {
        super(event);


    }
}
