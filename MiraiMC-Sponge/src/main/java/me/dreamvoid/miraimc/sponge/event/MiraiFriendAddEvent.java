package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.FriendAddEvent;
import org.jetbrains.annotations.NotNull;

/**
 * 成功添加了一个新好友
 */
public class MiraiFriendAddEvent extends AbstractEvent {
    private final Cause cause;

    public MiraiFriendAddEvent(FriendAddEvent event, Cause cause) {
        this.event = event;
        this.cause = cause;
    }

    private final FriendAddEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 获取好友QQ号
     * @return QQ号
     */
    public long getFriendID(){
        return event.getFriend().getId();
    }

    /**
     * 获取好友昵称
     * @return 昵称
     */
    public String getFriendNick() {
        return event.getFriend().getNick();
    }

    /**
     * (?) 获取好友昵称
     * @return 昵称
     */
    public String getFriendRemark(){
        return event.getFriend().getRemark();
    }

    /**
     * 获取哈希值
     * @return 哈希值
     */
    public int getHashCode() {
        return event.hashCode();
    }

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    @Override
    public @NotNull Cause getCause() {
        return cause;
    }
}
