package me.dreamvoid.miraimc.listener;

import net.mamoe.mirai.event.events.BotLeaveEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MiraiGroupBotLeaveEvent extends Event{

    public MiraiGroupBotLeaveEvent(BotLeaveEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final BotLeaveEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public Long getBotID() { return event.getBot().getId(); }

    /**
     * 返回退出群的群号
     * @return 群号
     */
    public Long getGroupID() { return event.getGroupId(); }

}
