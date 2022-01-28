package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * 机器人成功加入了一个新群
 */
public class MiraiGroupBotJoinGroupEvent extends AbstractEvent {
    private final Cause cause;

    public MiraiGroupBotJoinGroupEvent(BotJoinGroupEvent event, Cause cause) {
        this.event = event;
        this.cause = cause;
    }

    private final BotJoinGroupEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 返回加入群的群号
     * @return 群号
     */
    public long getGroupID() { return event.getGroupId(); }

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

    /**
     * 获取群实例
     * @return MiraiGroup 实例
     */
    public MiraiGroup getGroup(){
        return new MiraiGroup(event.getBot(), event.getGroup().getId());
    }
}
