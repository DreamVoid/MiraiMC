package me.dreamvoid.miraimc.api.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.friendgroup.FriendGroup;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MiraiMC 好友分组
 * @author DreamVoid
 * @since mirai 2.13
 */
public class MiraiFriendGroup {
    private final Bot bot;
    private final FriendGroup friendGroup;

    /**
     * 初始化好友分组实例
     * @param bot 机器人实例
     * @param friendGroup 好友分组
     */
    public MiraiFriendGroup(Bot bot, FriendGroup friendGroup){
        this.bot = bot;
        this.friendGroup = friendGroup;
    }

    /**
     * 重命名好友分组
     * @param newName 新名称
     * @return 是否重命名成功
     */
    public boolean rename(String newName){
        return friendGroup.renameTo(newName);
    }

    /**
     * 获取好友分组名称
     * @return 好友分组名称
     */
    public String getName(){
        return friendGroup.getName();
    }

    /**
     * 获取分组内所有好友
     * @return 好友列表
     */
    public List<MiraiFriend> getFriends(){
        return friendGroup.getFriends().stream().map(f -> new MiraiFriend(bot, f)).collect(Collectors.toList());
    }

    /**
     * 获取好友分组ID
     * @return 好友分组ID
     */
    public int getId(){
        return friendGroup.getId();
    }

    /**
     * 获取分组内好友的数量
     * @return 好友数量
     */
    public int getCount(){
        return friendGroup.getCount();
    }

    /**
     * 删除好友分组
     * @return 是否删除成功
     */
    public boolean delete(){
        return friendGroup.delete();
    }

    /**
     * 移动好友至好友分组
     * @param friend 好友
     * @return 是否移动成功
     */
    public boolean moveIn(MiraiFriend friend){
        return friendGroup.moveIn(bot.getFriendOrFail(friend.getID()));
    }

    /**
     * 移动好友至好友分组
     * @param friendID QQ号
     * @return 是否移动成功
     */
    public boolean moveIn(long friendID){
        return friendGroup.moveIn(bot.getFriendOrFail(friendID));
    }
}
