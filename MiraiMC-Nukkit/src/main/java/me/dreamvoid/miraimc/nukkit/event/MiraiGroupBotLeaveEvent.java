package me.dreamvoid.miraimc.nukkit.event;

import cn.nukkit.event.HandlerList;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import net.mamoe.mirai.event.events.BotLeaveEvent;
import cn.nukkit.event.Event;

/**
 * 机器人被踢出群或在其他客户端主动退出一个群
 */
public class MiraiGroupBotLeaveEvent extends Event{

    // 主动退群
    public MiraiGroupBotLeaveEvent(BotLeaveEvent event, BotLeaveEvent.Active eventActive) {
        this.event = event;
        this.eventActive = eventActive;
        this.eventKick = null;
    }

    // 被踢出群
    public MiraiGroupBotLeaveEvent(BotLeaveEvent.Kick event, BotLeaveEvent.Kick eventKick) {
        this.event = event;
        this.eventKick = eventKick;
        this.eventActive = null;
    }

    private final BotLeaveEvent event;
    private final BotLeaveEvent.Active eventActive;
    private final BotLeaveEvent.Kick eventKick;

    private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlers() { return handlers; }
    //public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 返回退出群的群号
     * @return 群号
     */
    public long getGroupID() { return event.getGroupId(); }

    /**
     * 返回退群类型
     * @return Active - 主动退群 | Kick - 被踢出群
     */
    public String getType() {
        if(eventKick != null){
            return "Kick";
        } else return "Active";
    }

    /**
     * 返回操作管理员的QQ。
     * 如果机器人为主动退群，则返回 0
     * @return 操作者ID
     */
    public long getOperator() {
        if(eventKick != null){
            return eventKick.getOperator().getId();
        } else return 0;
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    /**
     * 获取群实例
     * @return MiraiGroup 实例
     */
    public MiraiGroup getGroup(){
        return new MiraiGroup(event.getBot(), event.getGroup().getId());
    }
}
