package me.dreamvoid.miraimc.nukkit.event;

import cn.nukkit.event.HandlerList;
import net.mamoe.mirai.event.events.BotMuteEvent;
import cn.nukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * 机器人被禁言
 */
public class MiraiGroupBotMuteEvent extends Event{

    // 主动退群
    public MiraiGroupBotMuteEvent(BotMuteEvent event) {
        this.event = event;
    }

    private final BotMuteEvent event;

    private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlers() { return handlers; }
    //public static HandlerList getHandlerList() { return handlers; }

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
     * 返回机器人被禁言的时间
     * @return 时间(秒)
     */
    public int getTime() {
        return event.getDurationSeconds();
    }

    /**
     * 返回执行禁言操作的管理员。
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
}
