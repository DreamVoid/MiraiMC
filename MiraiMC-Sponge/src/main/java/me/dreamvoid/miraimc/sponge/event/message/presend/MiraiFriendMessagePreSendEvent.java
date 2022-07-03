package me.dreamvoid.miraimc.sponge.event.message.presend;

import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.FriendMessagePreSendEvent;

/**
 * (Sponge) 消息 - 主动发送消息前 - 好友消息
 */
public class MiraiFriendMessagePreSendEvent extends AbstractMessagePreSendEvent {
    public MiraiFriendMessagePreSendEvent(FriendMessagePreSendEvent event, Cause cause) {
        super(event, cause);
        this.event = event;
    }

    private final FriendMessagePreSendEvent event;

    /**
     * 返回发送这条信息的机器人ID
     * @return 机器人ID
     */
    public long getBotID(){
        return event.getBot().getId();
    }

    /**
     * 返回目标好友的QQ号
     * @return 好友QQ号
     */
    public long getFriendID(){
        return event.getTarget().getId();
    }

    /**
     * 返回目标好友的昵称
     * @return 昵称
     */
    public String getFriendNickName(){ return event.getTarget().getNick(); }

    /**
     * 返回目标好友的备注名
     * @return 备注名
     */
    public String getFriendRemark(){ return event.getTarget().getRemark(); }

    /**
     * 获取好友实例
     * @return MiraiFriend 实例
     */
    public MiraiFriend getFriend(){
        return new MiraiFriend(event.getBot(), event.getTarget().getId());
    }
}
