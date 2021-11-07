package me.dreamvoid.miraimc.event;

/**
 * Bot 重新登录
 */
public interface BotReloginEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getID();

    /**
     * 获取机器人昵称
     * @return 机器人昵称
     */
    String getNick();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
