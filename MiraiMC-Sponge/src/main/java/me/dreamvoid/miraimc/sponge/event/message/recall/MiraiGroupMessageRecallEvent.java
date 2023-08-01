package me.dreamvoid.miraimc.sponge.event.message.recall;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import org.spongepowered.api.event.cause.Cause;
import net.mamoe.mirai.event.events.MessageRecallEvent;

/**
 * (Sponge) 消息 - 消息撤回 - 群撤回
 */
@SuppressWarnings("unused")
public class MiraiGroupMessageRecallEvent extends AbstractMessageRecallEvent {
    public MiraiGroupMessageRecallEvent(MessageRecallEvent.GroupRecall event, Cause cause) {
        super(event, cause);
        this.event = event;
    }

    private final MessageRecallEvent.GroupRecall event;

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
     * @see #getAuthorNick()
     */
    @Deprecated
    public long getSenderID() { return event.getAuthorId(); }

    /**
     * 获取撤回信息的操作者昵称
     * 如果操作者不存在，则返回null
     * @return 操作者昵称
     */
    public String getOperatorNick() {
        if(event.getOperator() != null){
            return event.getOperator().getNick();
        } else return null;
    }

    /**
     * 获取撤回信息的操作者ID
     * 如果操作者不存在，则返回0
     * @return 操作者ID
     */
    public long getOperatorID() {
        if(event.getOperator() != null){
            return event.getOperator().getId();
        } else return 0;
    }

    /**
     * 获取群号
     * @return 群号
     */
    public long getGroupID(){
        return event.getGroup().getId();
    }

    /**
     * 获取群实例
     * @return MiraiGroup 实例
     */
    public MiraiGroup getGroup(){
        return new MiraiGroup(event.getBot(), event.getGroup().getId());
    }
}
