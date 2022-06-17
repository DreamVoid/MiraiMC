package me.dreamvoid.miraimc.nukkit.event.message.passive;

import net.mamoe.mirai.event.events.OtherClientMessageEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (bungee) Mirai 核心事件 - 消息 - 被动收到消息 - 其他客户端消息
 */
public class MiraiOtherClientMessageEvent extends AbstractMessageEvent {
    private final OtherClientMessageEvent event;

    public MiraiOtherClientMessageEvent(OtherClientMessageEvent event) {
        super(event);
        this.event = event;

        NukkitPlugin.getInstance().getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.nukkit.event.MiraiOtherClientMessageEvent(event));
    }

    /**
     * (?)获取发送设备的种类
     * @return 客户端种类
     */
    public String getDeviceKind(){
        return event.getClient().getInfo().getDeviceKind();
    }

    /**
     * (?)获取发送设备的名称
     * @return 设备名称
     */
    public String getDeviceName(){
        return event.getClient().getInfo().getDeviceName();
    }
}
