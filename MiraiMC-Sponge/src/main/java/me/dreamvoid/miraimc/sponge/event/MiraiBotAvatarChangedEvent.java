package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.BotAvatarChangedEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Bot 头像改变
 */
public class MiraiBotAvatarChangedEvent extends AbstractEvent {

    private final Cause cause;

    public MiraiBotAvatarChangedEvent(BotAvatarChangedEvent event, Cause cause) {
        this.event = event;
        this.cause = cause;
    }

    private final BotAvatarChangedEvent event;

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
     * 获取机器人头像Url
     * @return 机器人头像Url
     */
    public String getAvatarUrl(){
        return event.getBot().getAvatarUrl();
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
