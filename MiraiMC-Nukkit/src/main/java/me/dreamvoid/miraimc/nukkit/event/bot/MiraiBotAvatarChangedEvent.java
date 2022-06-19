package me.dreamvoid.miraimc.nukkit.event.bot;

import net.mamoe.mirai.event.events.BotAvatarChangedEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (Nukkit) Mirai 核心事件 - Bot - Bot 头像改变
 */
public class MiraiBotAvatarChangedEvent extends AbstractBotEvent {
    public MiraiBotAvatarChangedEvent(BotAvatarChangedEvent event) {
        super(event);
        this.event = event;

        NukkitPlugin.getInstance().getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.nukkit.event.MiraiBotAvatarChangedEvent(event));
    }

    private final BotAvatarChangedEvent event;

    /**
     * 获取机器人头像Url
     * @return 机器人头像Url
     */
    public String getAvatarUrl(){
        return event.getBot().getAvatarUrl();
    }
}
