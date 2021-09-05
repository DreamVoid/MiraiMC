package me.dreamvoid.miraimc.bungee.event;

import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * 消息撤回 - 群消息
 */
public class MiraiGroupMessageRecallEvent extends Event {

    public MiraiGroupMessageRecallEvent(MessageRecallEvent.GroupRecall event) {
        this.event = event;
    }

    private final MessageRecallEvent.GroupRecall event;

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
     * 获取信息发送时间
     * @return 发送时间
     */
    public long getMessageTime() { return event.getMessageTime(); }

    /**
     * 获取信息编号
     * @return 信息编号
     */
    public int[] getMessageIds() { return event.getMessageIds(); }

    /**
     * 获取群号
     * @return 群号
     */
    public long getGroupID(){
        return event.getGroup().getId();
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
