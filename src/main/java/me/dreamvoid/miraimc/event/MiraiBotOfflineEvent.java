package me.dreamvoid.miraimc.event;

import net.mamoe.mirai.event.events.BotOfflineEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MiraiBotOfflineEvent extends Event {

    public MiraiBotOfflineEvent(BotOfflineEvent event, String type) {
        super(true);
        this.event = event;
        this.Type = type;
    }

    private static final HandlerList handlers = new HandlerList();
    private final BotOfflineEvent event;
    private final String Type;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人下线原因
     * @return Active - 主动下线 | Force - 被挤下线 | Dropped - 被服务器断开或因网络问题而掉线 | RequireReconnect - 服务器主动要求更换另一个服务器
     */
    public String getType() { return Type; }

    /**
     * 重新建立连接
     * @return 成功返回true，失败返回false
     */
    public boolean reconnect() { return event.getReconnect(); }

    /**
     * 关闭机器人线程
     * [!]
     */
    public void close() { event.getBot().close(); }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getID() { return event.getBot().getId(); }

    /**
     * 获取机器人昵称
     * @return 机器人昵称
     */
    public String getNick() { return event.getBot().getNick(); }


}
