package me.dreamvoid.miraimc.bungee.event.bot;

import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.md_5.bungee.api.ProxyServer;

/**
 * (BungeeCord) Mirai 核心事件 - Bot - Bot 离线
 */
public class MiraiBotOfflineEvent extends AbstractBotEvent {
    public MiraiBotOfflineEvent(BotOfflineEvent event, Type type) {
        super(event);
        this.event = event;
        this.Type = type;

        ProxyServer.getInstance().getPluginManager().callEvent(new me.dreamvoid.miraimc.bungee.event.MiraiBotOfflineEvent(event,type));
    }

    private final BotOfflineEvent event;
    private final Type Type;

    /**
     * 获取机器人下线原因
     * @return Active - 主动下线 | Force - 被挤下线 | Dropped - 被服务器断开或因网络问题而掉线 | RequireReconnect - 服务器主动要求更换另一个服务器
     */
    public Type getType() { return Type; }

    /**
     * 重新建立连接
     * @return 成功返回true，失败返回false
     */
    public boolean reconnect() { return event.getReconnect(); }

    /**
     * 关闭机器人线程[!]
     */
    public void close() { event.getBot().close(); }

    /**
     * 下线原因类型
     */
    public enum Type{
        Active,Force,Dropped,RequireReconnect
    }
}
