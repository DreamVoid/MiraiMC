package me.dreamvoid.miraimc.event;

/**
 * Bot 昵称改变
 */
public interface BotNickChangedEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getID();

    /**
     * 获取机器人更换前的昵称
     * @return 机器人更换前的昵称
     */
    String getOldNick();

    /**
     * 获取机器人更换后的昵称
     * @return 机器人更换后的昵称
     */
    String getNewNick();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
