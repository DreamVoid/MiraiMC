package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.BotReloginEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * Bot 重新登录
 */
public class MiraiBotReloginEvent extends AbstractEvent {

    private final Cause cause;

    public MiraiBotReloginEvent(BotReloginEvent event, Cause cause) {
        this.event = event;
        this.cause = cause;
    }

    private final BotReloginEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getID() { return event.getBot().getId(); }

    /**
     * 获取机器人昵称
     * @return 机器人昵称
     */
    public String getNick() { return event.getBot().getNick(); }

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
