package me.dreamvoid.miraimc.sponge.event.group;

import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;

/**
 * (Sponge) 群 - 机器人成功加入了一个新群
 */
@SuppressWarnings("unused")
public class MiraiBotJoinGroupEvent extends AbstractGroupEvent {
    public MiraiBotJoinGroupEvent(BotJoinGroupEvent event, Cause cause) {
        super(event, cause);
        this.event = event;
    }

    private final BotJoinGroupEvent event;

    /**
     * 返回加入群的群号
     * @return 群号
     */
    @Override
    public long getGroupID() { return event.getGroupId(); }
}
