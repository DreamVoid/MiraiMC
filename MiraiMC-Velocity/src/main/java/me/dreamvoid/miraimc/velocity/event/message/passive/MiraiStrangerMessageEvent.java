package me.dreamvoid.miraimc.velocity.event.message.passive;

import me.dreamvoid.miraimc.velocity.VelocityPlugin;
import net.mamoe.mirai.event.events.StrangerMessageEvent;

/**
 * (Velocity) Mirai 核心事件 - 消息 - 被动收到消息 - 陌生人消息
 */
public class MiraiStrangerMessageEvent extends AbstractMessageEvent {

    public MiraiStrangerMessageEvent(StrangerMessageEvent event) {
        super(event);

        VelocityPlugin.INSTANCE.getServer().getEventManager().fire(new me.dreamvoid.miraimc.velocity.event.MiraiStrangerMessageEvent(event));
    }
}
