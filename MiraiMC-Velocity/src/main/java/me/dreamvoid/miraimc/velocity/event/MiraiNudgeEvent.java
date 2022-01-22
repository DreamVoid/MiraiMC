package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.NudgeEvent;

/**
 * 戳一戳
 */
public class MiraiNudgeEvent {
    public MiraiNudgeEvent(NudgeEvent event) {
        this.event = event;
    }

    private final NudgeEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 获取发送者ID
     * @return 发送者ID
     */
    public long getFromID() { return event.getFrom().getId(); }

    /**
     * 获取发送者昵称
     * @return 发送者昵称
     */
    public String getFromNick() { return event.getFrom().getNick(); }

    /**
     * 获取接收者ID
     * @return 接收者ID
     */
    public long getTargetID() { return event.getTarget().getId(); }

    /**
     * 获取接收者昵称
     * @return 接收者昵称
     */
    public String getTargetNick() { return event.getTarget().getNick(); }

    /**
     * (?)获取操作
     * @return 操作内容
     */
    public String getAction(){return event.getAction();}

    /**
     * (?)获取后缀
     * @return 后缀内容
     */
    public String getSuffix(){return event.getSuffix();}

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }
}
