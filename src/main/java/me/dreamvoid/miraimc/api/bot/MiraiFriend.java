package me.dreamvoid.miraimc.api.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

/**
 * MiraiMC 好友
 * @author DreamVoid
 */
public class MiraiFriend {
    private final Friend friend;

    /**
     * 获取指定好友的实例
     * @param bot 机器人实例
     * @param friendAccount 好友账号
     * @throws NullPointerException 不存在指定好友时抛出
     */
    public MiraiFriend(Bot bot, long friendAccount) throws NullPointerException{
        friend = bot.getFriend(friendAccount);
    }

    /**
     * 向好友发送消息
     * @param message 消息文本
     */
    public void sendMessage(String message){
        friend.sendMessage(message);
    }
    /**
     * 向好友发送消息
     * @param messageChain 消息链
     * @deprecated
     */
    @Deprecated
    public void sendMessage(MessageChain messageChain){
        friend.sendMessage(messageChain);
    }

    /**
     * 向好友发送消息<br>
     * 此方法将自动转换为Mirai Code，可用于发送图片等特殊消息
     * @param message Mirai Code格式的消息文本
     */
    public void sendMessageMirai(String message){
        friend.sendMessage(MiraiCode.deserializeMiraiCode(message));
    }

    /**
     * 向好友发送戳一戳
     */
    public void sendNudge(){
        friend.nudge();
    }

    /**
     * 获取好友昵称
     * @return 昵称
     */
    public String getNick(){
        return friend.getNick();
    }

    /**
     * 获取好友备注
     * @return 备注
     */
    public String getRemark(){
        return friend.getRemark();
    }

    /**
     * 删除好友
     */
    public void doDelete(){
        friend.delete();
    }

    /**
     * 获取好友QQ号
     * @return QQ号
     */
    public long getID(){
        return friend.getId();
    }

    /**
     * 获取好友头像链接
     * @return Url
     */
    public String getAvatarUrl(){
        return friend.getAvatarUrl();
    }
}
