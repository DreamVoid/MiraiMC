package me.dreamvoid.miraimc.nukkit.event.message.passive;

import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import net.mamoe.mirai.event.events.OtherClientMessageEvent;

/**
 * (Nukkit) 消息 - 被动收到消息 - 其他客户端消息
 */
public class MiraiOtherClientMessageEvent extends AbstractMessageEvent {
    public MiraiOtherClientMessageEvent(OtherClientMessageEvent event) {
        super(event);
        this.event = event;

        deviceKind = event.getClient().getInfo().getDeviceKind();
    }

    public MiraiOtherClientMessageEvent(long BotID, FetchMessage.Data data){
        super(BotID, data);

        deviceKind = data.sender.platform;
    }

    private OtherClientMessageEvent event;
    private final String deviceKind;

    /**
     * (?)获取发送设备的种类
     * @return 客户端种类
     */
    public String getDeviceKind(){
        return deviceKind;
    }

    /**
     * (?)获取发送设备的名称
     * @return 设备名称
     */
    public String getDeviceName(){
        return event.getClient().getInfo().getDeviceName();
    }
}
