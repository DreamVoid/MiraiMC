package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 机器人成功加入了一个新群
 */
public class MiraiGroupBotJoinGroupEvent extends Event{

    public MiraiGroupBotJoinGroupEvent(BotJoinGroupEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final BotJoinGroupEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

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

}