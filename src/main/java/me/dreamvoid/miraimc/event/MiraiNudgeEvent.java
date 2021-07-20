package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.NudgeEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MiraiNudgeEvent extends Event {

    public MiraiNudgeEvent(NudgeEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final NudgeEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getID() { return event.getBot().getId(); }

    /**
     * 获取发送者ID
     * @return 发送者ID
     */
    public long getFromID() { return event.getFrom().getId(); }

    /**
     * 获取发送者昵称
     * @return 发送者昵称
     */
    public String getFromNick() { return event.getFrom().getNick(); }

    /**
     * 获取接收者ID
     * @return 接收者ID
     */
    public long getTargetID() { return event.getTarget().getId(); }

    /**
     * 获取接收者昵称
     * @return 接收者昵称
     */
    public String getTargetNick() { return event.getTarget().getNick(); }

    /**
     * (?)获取操作
     * @return 操作内容
     */
    public String getAction(){return event.getAction();}

    /**
     * (?)获取后缀
     * @return 后缀内容
     */
    public String getSuffix(){return event.getSuffix();}

}
