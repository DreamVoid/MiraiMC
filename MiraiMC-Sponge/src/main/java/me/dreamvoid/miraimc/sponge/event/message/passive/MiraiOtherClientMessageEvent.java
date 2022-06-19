package me.dreamvoid.miraimc.sponge.event.message.passive;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.OtherClientMessageEvent;

/**
 * (Sponge) Mirai 核心事件 - 消息 - 被动收到消息 - 其他客户端消息
 */
public class MiraiOtherClientMessageEvent extends AbstractMessageEvent {
    public MiraiOtherClientMessageEvent(OtherClientMessageEvent event, Cause cause) {
        super(event, cause);
        this.event = event;

        Sponge.getEventManager().post(new me.dreamvoid.miraimc.sponge.event.MiraiOtherClientMessageEvent(event, cause));
    }

    private final OtherClientMessageEvent event;

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
