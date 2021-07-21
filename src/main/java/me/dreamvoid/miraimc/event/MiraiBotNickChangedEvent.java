package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.BotNickChangedEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Bot 昵称改变
 */
public class MiraiBotNickChangedEvent extends Event {

    public MiraiBotNickChangedEvent(BotNickChangedEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final BotNickChangedEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getID() { return event.getBot().getId(); }

    /**
     * 获取机器人更换前的昵称
     * @return 机器人更换前的昵称
     */
    public String getOldNick() { return event.getFrom(); }

    /**
     * 获取机器人更换后的昵称
     * @return 机器人更换后的昵称
     */
    public String getNewNick() { return event.getTo(); }
}
