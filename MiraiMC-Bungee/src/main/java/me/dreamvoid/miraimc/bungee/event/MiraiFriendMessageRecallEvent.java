package me.dreamvoid.miraimc.bungee.event;

import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * 消息撤回 - 好友撤回
 */
public class MiraiFriendMessageRecallEvent extends Event {

    public MiraiFriendMessageRecallEvent(MessageRecallEvent.FriendRecall event) {
        this.event = event;
    }

    private final MessageRecallEvent.FriendRecall event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 获取被撤回信息的发送者昵称
     * @return 发送者昵称
     */
    public String getSenderNick() { return event.getAuthor().getNick(); }

    /**
     * 获取被撤回信息的发送者ID
     * @return 发送者ID
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
     * 获取信息发送时间
     * @return 发送时间
     */
    public long getMessageTime() { return event.getMessageTime(); }

    /**
     * (?)获取信息编号
     * @return 信息编号
     */
    public int[] getMessageIds() { return event.getMessageIds(); }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    /**
     * 获取好友实例
     * @return MiraiFriend 实例
     */
    public MiraiFriend getFriend(){
        return new MiraiFriend(event.getBot(), event.getOperator().getId());
    }
}
