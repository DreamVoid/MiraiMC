package me.dreamvoid.miraimc.velocity.event.message.passive;

import net.mamoe.mirai.event.events.OtherClientMessageEvent;

/**
 * (Velocity) 消息 - 被动收到消息 - 其他客户端消息
 */
@SuppressWarnings("unused")
public class MiraiOtherClientMessageEvent extends AbstractMessageEvent {
    public MiraiOtherClientMessageEvent(OtherClientMessageEvent event) {
        super(event);
        this.event = event;
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
