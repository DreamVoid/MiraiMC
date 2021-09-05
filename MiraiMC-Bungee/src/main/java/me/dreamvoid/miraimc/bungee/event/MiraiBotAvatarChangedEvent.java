package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.BotAvatarChangedEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * Bot 头像改变
 */
public class MiraiBotAvatarChangedEvent extends Event {

    public MiraiBotAvatarChangedEvent(BotAvatarChangedEvent event) {
        this.event = event;
    }

    private final BotAvatarChangedEvent event;

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

    /**
     * 获取机器人头像Url
     * @return 机器人头像Url
     */
    public String getAvatarUrl(){
        return event.getBot().getAvatarUrl();
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }
}
