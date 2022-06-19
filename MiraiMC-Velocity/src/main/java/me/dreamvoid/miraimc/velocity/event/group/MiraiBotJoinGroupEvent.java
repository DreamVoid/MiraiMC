package me.dreamvoid.miraimc.velocity.event.group;

import me.dreamvoid.miraimc.velocity.VelocityPlugin;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;

/**
 * (Velocity) Mirai 核心事件 - 群 - 机器人成功加入了一个新群
 */
public class MiraiBotJoinGroupEvent extends AbstractGroupEvent {
    public MiraiBotJoinGroupEvent(BotJoinGroupEvent event) {
        super(event);
        this.event = event;

        VelocityPlugin.INSTANCE.getServer().getEventManager().fire(new me.dreamvoid.miraimc.velocity.event.MiraiGroupBotJoinGroupEvent(event));
    }

    private final BotJoinGroupEvent event;

    /**
     * 返回加入群的群号
     * @return 群号
     */
    @Override
    public long getGroupID() { return event.getGroupId(); }
}
