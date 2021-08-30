package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.FriendMessagePostSendEvent;
import net.mamoe.mirai.message.data.MessageSource;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 主动发送消息后 - 好友消息
 */
public class MiraiFriendMessagePostSendEvent extends Event {

    public MiraiFriendMessagePostSendEvent(FriendMessagePostSendEvent event) {
        super(true);
        this.event = event;
    }

    private static final HandlerList handlers = new HandlerList();
    private final FriendMessagePostSendEvent event;

    public @NotNull HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

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
     * 返回接收到的消息内容<br>
     * 此方法使用 toString()
     * @return 原始消息内容
     */
    public String getMessage(){
        return event.getMessage().toString();
    }

    /**
     * 返回接收到的消息内容转换到字符串的结果<br>
     * 此方法使用 contentToString()，这一般和 toString() 的工作方式相同
     * @return 转换字符串后的消息内容
     */
    public String getMessageContent(){
        return event.getMessage().contentToString();
    }

    /**
     * 返回接收到的消息内容转换到Mirai Code的结果<br>
     * 此方法使用 serializeToMiraiCode()
     * @return 带Mirai Code的消息内容
     */
    public String getMessageToMiraiCode(){
        return event.getMessage().serializeToMiraiCode();
    }

    /**
     * 撤回这条消息
     */
    public void recall() {
        MessageSource.recall(event.getMessage());
    }

    /**
     * 等待指定时间后撤回这条消息<br>
     * 此方法执行异步(Async)任务
     * @param delayTime 延迟时间（毫秒）
     */
    public void recall(long delayTime){
        MessageSource.recallIn(event.getMessage(), delayTime);
    }

}
