package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.BotUnmuteEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * 机器人被取消禁言
 */
public class MiraiGroupBotUnmuteEvent extends AbstractEvent {
    private final Cause cause;

    // 主动退群
    public MiraiGroupBotUnmuteEvent(BotUnmuteEvent event, Cause cause) {
        this.event = event;
        this.cause = cause;
    }


    private final BotUnmuteEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 返回目标群的群号
     * @return 群号
     */
    public long getGroupID() { return event.getGroupId(); }

    /**
     * 返回执行解除禁言操作的管理员。
     * @return 管理员QQ
     */
    public long getNewPermission() {
        return event.getOperator().getId();
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    @Override
    public @NotNull Cause getCause() {
        return cause;
    }
}
