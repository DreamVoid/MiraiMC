package me.dreamvoid.miraimc.nukkit.event.group;

import net.mamoe.mirai.event.events.BotJoinGroupEvent;

/**
 * (Nukkit) 群 - 机器人成功加入了一个新群
 */
@SuppressWarnings("unused")
public class MiraiBotJoinGroupEvent extends AbstractGroupEvent {
    private final BotJoinGroupEvent event;

    public MiraiBotJoinGroupEvent(BotJoinGroupEvent event) {
        super(event);
        this.event = event;
    }

    /**
     * 返回加入群的群号
     * @return 群号
     */
    @Override
    public long getGroupID() { return event.getGroupId(); }
}
