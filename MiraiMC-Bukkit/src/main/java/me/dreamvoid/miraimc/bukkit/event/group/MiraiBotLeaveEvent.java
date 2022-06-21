package me.dreamvoid.miraimc.bukkit.event.group;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import net.mamoe.mirai.event.events.BotLeaveEvent;

/**
 * (Bukkit) Mirai 核心事件 - 群 - 机器人被踢出群或在其他客户端主动退出一个群
 */
public class MiraiBotLeaveEvent extends AbstractGroupEvent{
    public MiraiBotLeaveEvent(BotLeaveEvent event) {
        super(event);
        this.event = event;
    }

    private final BotLeaveEvent event;

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
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    @Override
    public String toString() {
        return event.toString();
    }

    /**
     * 获取群实例
     * @return MiraiGroup 实例
     */
    public MiraiGroup getGroup(){
        return new MiraiGroup(event.getBot(), event.getGroup().getId());
    }

    /**
     * 机器人主动退出一个群
     */
    public static class Active extends MiraiBotLeaveEvent {
        public Active(BotLeaveEvent.Active event){
            super(event);
        }
    }

    /**
     * 机器人被管理员或群主踢出群
     */
    public static class Kick extends MiraiBotLeaveEvent {
        public Kick(BotLeaveEvent.Kick event){
            super(event);
            this.event = event;
        }

        final BotLeaveEvent.Kick event;

        /**
         * 返回操作管理员的QQ。
         * @return 操作者ID
         */
        public long getOperator() {
            return event.getOperator().getId();
        }
    }
}
