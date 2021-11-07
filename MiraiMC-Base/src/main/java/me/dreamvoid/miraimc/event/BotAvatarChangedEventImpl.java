package me.dreamvoid.miraimc.event;

/**
 * Bot 头像改变
 */
public interface BotAvatarChangedEventImpl {
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
     * 获取机器人头像Url
     * @return 机器人头像Url
     */
    String getAvatarUrl();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
