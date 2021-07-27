package me.dreamvoid.miraimc.api;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;

public class MiraiNormalMember{
    private final NormalMember member;

    public MiraiNormalMember(Group group,long account) throws NullPointerException{
        member = group.get(account);
    }

    /**
     * 获取成员QQ号
     * @return QQ号
     */
    public long getId(){
        return member.getId();
    }

    /**
     * 踢出成员(要求机器人为管理员或群主)
     * @param reason 理由
     */
    public void doKick(String reason){
        member.kick(reason);
    }

    /**
     * 禁言成员(要求机器人为管理员或群主)
     * @param time 时间(秒)
     */
    public void setMute(int time){
        member.mute(time);
    }

    /**
     * 解除禁言成员(要求机器人为管理员或群主)
     */
    public void setUnmute(){
        member.unmute();
    }

    /**
     * 判断是否被禁言
     * @return 被禁言返回true，未被禁言false
     */
    public boolean isMuted(){
        return member.isMuted();
    }

    /**
     * 获取禁言剩余时间
     * @return 剩余时间(秒)
     */
    public int getMuteTimeRemaining(){
        return member.getMuteTimeRemaining();
    }

    /**
     * 发送消息
     * @param message 消息内容
     */
    public void sendMessage(String message){
        member.sendMessage(message);
    }
}
