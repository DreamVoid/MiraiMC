package me.dreamvoid.miraimc.nukkit.event.group;

import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (bungee) Mirai 核心事件 - 群 - 机器人成功加入了一个新群
 */
public class MiraiBotJoinGroupEvent extends AbstractGroupEvent {
    private final BotJoinGroupEvent event;

    public MiraiBotJoinGroupEvent(BotJoinGroupEvent event) {
        super(event);
        this.event = event;

        NukkitPlugin.getInstance().getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.nukkit.event.MiraiGroupBotJoinGroupEvent(event));
    }

    /**
     * 返回加入群的群号
     * @return 群号
     */
    @Override
    public long getGroupID() { return event.getGroupId(); }
}
