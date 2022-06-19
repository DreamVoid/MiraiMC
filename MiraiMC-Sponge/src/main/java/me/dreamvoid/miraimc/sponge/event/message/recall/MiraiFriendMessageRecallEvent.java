package me.dreamvoid.miraimc.sponge.event.message.recall;

import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.MessageRecallEvent;

/**
 * (Sponge) Mirai 核心事件 - 消息 - 消息撤回 - 好友撤回
 */
public class MiraiFriendMessageRecallEvent extends AbstractMessageRecallEvent {
    public MiraiFriendMessageRecallEvent(MessageRecallEvent.FriendRecall event, Cause cause) {
        super(event, cause);
        this.event = event;


    }

    private final MessageRecallEvent.FriendRecall event;

    /**
     * 获取被撤回信息的发送者昵称
     * @return 发送者昵称
     * @deprecated
     * @see #getAuthorNick()
     */
    @Deprecated
    public String getSenderNick() { return event.getAuthor().getNick(); }

    /**
     * 获取被撤回信息的发送者ID
     * @return 发送者ID
     * @deprecated
     * @see #getAuthorID()
     */
    public long getSenderID() { return event.getAuthor().getId(); }

    /**
     * 获取撤回信息的操作者昵称
     * @return 操作者昵称
     */
    public String getOperatorNick() { return event.getOperator().getNick(); }

    /**
     * 获取撤回信息的操作者ID
     * @return 操作者ID
     */
    public long getOperatorID() { return event.getOperatorId(); }

    /**
     * 获取好友实例
     * @return MiraiFriend 实例
     */
    public MiraiFriend getFriend(){
        return new MiraiFriend(event.getBot(), event.getOperator().getId());
    }
}
