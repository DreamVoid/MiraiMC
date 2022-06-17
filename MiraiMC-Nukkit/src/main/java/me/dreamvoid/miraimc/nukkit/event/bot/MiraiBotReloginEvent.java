package me.dreamvoid.miraimc.nukkit.event.bot;

import net.mamoe.mirai.event.events.BotReloginEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (bungee) Mirai 核心事件 - Bot - Bot 重新登录
 */
public class MiraiBotReloginEvent extends AbstractBotEvent {
    public MiraiBotReloginEvent(BotReloginEvent event) {
        super(event);

        NukkitPlugin.getInstance().getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.nukkit.event.MiraiBotReloginEvent(event));
    }
}
