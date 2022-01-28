package me.dreamvoid.miraimc.bungee.event;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * 机器人成功加入了一个新群
 */
public class MiraiGroupBotJoinGroupEvent extends Event{

    public MiraiGroupBotJoinGroupEvent(BotJoinGroupEvent event) {
        this.event = event;
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

    /**
     * 获取群实例
     * @return MiraiGroup 实例
     */
    public MiraiGroup getGroup(){
        return new MiraiGroup(event.getBot(), event.getGroup().getId());
    }
}
