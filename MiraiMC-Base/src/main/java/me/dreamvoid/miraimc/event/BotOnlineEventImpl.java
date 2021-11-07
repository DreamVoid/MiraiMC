package me.dreamvoid.miraimc.event;

import java.util.List;

/**
 * Bot 登录完成
 */
public interface BotOnlineEventImpl {
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
     * 获取机器人的好友列表
     * 此方法只返回QQ号
     * @return 好友列表数组
     */
    List<Long> getFriendList();

    /**
     * 获取机器人的群列表
     * 此方法只返回群号
     * @return 群列表数组
     */
    List<Long> getGroupList();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
