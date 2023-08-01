package me.dreamvoid.miraimc.sponge.event.group;

import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.BotUnmuteEvent;

/**
 * (Sponge) 群 - 机器人被取消禁言
 */
@SuppressWarnings("unused")
public class MiraiBotUnmuteEvent extends AbstractGroupEvent {
    private final BotUnmuteEvent event;

    public MiraiBotUnmuteEvent(BotUnmuteEvent event, Cause cause) {
        super(event, cause);
        this.event = event;
    }

    /**
     * 返回执行解除禁言操作的管理员。
     * @return 管理员QQ
     */
    public long getOperatorID() {
        return event.getOperator().getId();
    }
}
