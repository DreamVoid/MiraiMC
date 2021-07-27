package me.dreamvoid.miraimc.api.bot;

import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChain;

/**
 * MiraiMC 群
 * @author DreamVoid
 */
public class MiraiGroup {
    private final Group group;

    public MiraiGroup(Bot bot, long groupID) throws NullPointerException {
        group = bot.getGroup(groupID);
    }

    /**
     * 获取指定群成员的实例
     * @param memberAccount 群成员QQ号
     * @return MiraiMC 群成员实例
     */
    public MiraiNormalMember getMember(long memberAccount){
        return new MiraiNormalMember(group, memberAccount);
    }

    /**
     * 向群发送消息
     * @param message 消息内容
     */
    public void sendMessage(String message){
        group.sendMessage(message);
    }

    /**
     * 向群发送消息
     * @param messageChain 消息内容
     */
    public void sendMessage(MessageChain messageChain){
        group.sendMessage(messageChain);
    }

    /**
     * 判断指定成员是否在群内
     * @param memberAccount 成员QQ号
     * @return 存在返回true
     */
    public boolean contains(long memberAccount){
        return group.contains(memberAccount);
    }

    /**
     * 获取群名称
     * @return 群名
     */
    public String getName(){
        return group.getName();
    }

    /**
     * 设置群名称
     * @param name 群名称
     */
    public void setName(String name){
        group.setName(name);
    }

    /**
     * 退出群
     * @return 执行结果
     */
    public boolean doQuit(){
        return group.quit();
    }

    /**
     * 获取机器人在群内的管理权限
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getBotPermission(){
        return group.getBotPermission().getLevel();
    }
}
