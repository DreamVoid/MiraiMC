package me.dreamvoid.miraimc.nukkit.event.group;

import net.mamoe.mirai.event.events.BotMuteEvent;

/**
 * (Nukkit) 群 - 机器人被禁言
 */
public class MiraiBotMuteEvent extends AbstractGroupEvent {
    private final BotMuteEvent event;

    public MiraiBotMuteEvent(BotMuteEvent event) {
        super(event);
        this.event = event;
    }

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
    public long getOperatorID() {
        return event.getOperator().getId();
    }
}
