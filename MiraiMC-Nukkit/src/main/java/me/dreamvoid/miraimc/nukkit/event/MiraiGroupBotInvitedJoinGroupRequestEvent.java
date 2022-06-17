package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.nukkit.event.group.member.MiraiBotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;

/**
 * 群成员 - 成员列表变更 - 机器人被邀请加入群
 * @deprecated
 * @see MiraiBotInvitedJoinGroupRequestEvent
 */
@Deprecated
public class MiraiGroupBotInvitedJoinGroupRequestEvent extends MiraiBotInvitedJoinGroupRequestEvent {

    public MiraiGroupBotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent event) {
        super(event);
        this.event = event;
    }

    private final BotInvitedJoinGroupRequestEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 返回邀请者的昵称
     * @return 邀请者昵称
     */
    public String getInviterNick() { return getInvitorNick(); }

    /**
     * 返回邀请者的QQ号
     * @return 邀请者QQ号
     */
    public long getInviterID(){ return getInvitorID(); }

    /**
     * 同意请求
     */
    public void setAccept(){
        accept();
    }

    /**
     * 忽略请求
     */
    public void setIgnore(){
        ignore();
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
