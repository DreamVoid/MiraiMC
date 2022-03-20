package me.dreamvoid.miraimc.waterdogpe.event;


import dev.waterdog.waterdogpe.event.Event;
import net.mamoe.mirai.event.events.BotReloginEvent;

/**
 * Bot 重新登录
 */
public class MiraiBotReloginEvent extends Event {

    public MiraiBotReloginEvent(BotReloginEvent event) {
        this.event = event;
    }

    private final BotReloginEvent event;

   /* private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlers() { return handlers; }*/
    //public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     *
     * @return 机器人账号
     */
    public long getID() {
        return event.getBot().getId();
    }

    /**
     * 获取机器人昵称
     *
     * @return 机器人昵称
     */
    public String getNick() {
        return event.getBot().getNick();
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     *
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }
}
