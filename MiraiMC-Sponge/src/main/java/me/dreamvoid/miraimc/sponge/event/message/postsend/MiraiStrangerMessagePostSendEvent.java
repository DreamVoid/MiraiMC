package me.dreamvoid.miraimc.sponge.event.message.postsend;

import org.spongepowered.api.event.Cause;
import net.mamoe.mirai.event.events.StrangerMessagePostSendEvent;

/**
 * (Sponge) 消息 - 主动发送消息后 - 陌生人消息
 */
@SuppressWarnings("unused")
public class MiraiStrangerMessagePostSendEvent extends AbstractMessagePostSendEvent {
    public MiraiStrangerMessagePostSendEvent(StrangerMessagePostSendEvent event, Cause cause) {
        super(event, cause);
        this.event = event;
    }

    private final StrangerMessagePostSendEvent event;

    /**
     * 返回接收这条信息的目标昵称
     * @return 目标昵称
     */
    public String getTargetNick(){
        return event.getTarget().getNick();
    }

    /**
     * 返回接收者的备注名
     * @return 备注名
     */
    public String getFriendRemark(){ return event.getTarget().getRemark(); }
}
