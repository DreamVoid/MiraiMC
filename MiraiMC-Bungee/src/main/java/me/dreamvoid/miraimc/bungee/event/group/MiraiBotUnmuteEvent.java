package me.dreamvoid.miraimc.bungee.event.group;

import net.mamoe.mirai.event.events.BotUnmuteEvent;
import net.md_5.bungee.api.ProxyServer;

/**
 * (BungeeCord) Mirai 核心事件 - 群 - 机器人被取消禁言
 */
public class MiraiBotUnmuteEvent extends AbstractGroupEvent{
    public MiraiBotUnmuteEvent(BotUnmuteEvent event) {
        super(event);
        this.event = event;

        ProxyServer.getInstance().getPluginManager().callEvent(new me.dreamvoid.miraimc.bungee.event.MiraiGroupBotUnmuteEvent(event));
    }

    private final BotUnmuteEvent event;

    /**
     * 返回执行解除禁言操作的管理员。
     * @return 管理员QQ
     */
    public long getOperatorID() {
        return event.getOperator().getId();
    }
}
