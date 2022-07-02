package me.dreamvoid.miraimc.bukkit.event.message.passive;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.event.EventType;
import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.httpapi.exception.AbnormalStatusException;
import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * (Bukkit) Mirai 核心事件 - 消息 - 被动收到消息 - 群消息
 */
public class MiraiGroupMessageEvent extends AbstractMessageEvent {
    public MiraiGroupMessageEvent(GroupMessageEvent event) {
        super(event);
        this.event = event;

        BotID = event.getBot().getId();

        MemberName = event.getSender().getNameCard();
        Permission = event.getSender().getPermission().getLevel();

        GroupID = event.getGroup().getId();
        GroupName = event.getGroup().getName();
        GroupPermission = event.getGroup().getBotPermission().getLevel();
    }

    public MiraiGroupMessageEvent(long BotID, FetchMessage.Data data) {
        super(BotID, data);

        this.BotID = BotID;

        MemberName = data.sender.memberName;
        switch(data.sender.permission){
            case "OWNER":
                Permission=2;
                break;
            case "ADMINISTRATOR":
                Permission=1;
                break;
            case "MEMBER":
            default:
                Permission=0;
                break;
        }

        GroupID = data.sender.group.id;
        GroupName = data.sender.group.name;
        switch(data.sender.group.permission){
            case "OWNER":
                GroupPermission=2;
                break;
            case "ADMINISTRATOR":
                GroupPermission=1;
                break;
            case "MEMBER":
            default:
                GroupPermission=0;
                break;
        }
    }

    private GroupMessageEvent event;

    private final long BotID;

    private final String MemberName;
    private final int Permission;

    private final long GroupID;
    private final String GroupName;
    private final int GroupPermission;

    /**
     * 返回接收到这条信息的群号
     * @return 群号
     */
    public long getGroupID(){
        return GroupID;
    }

    /**
     * 返回接收到这条信息的群名称
     * @return 群名称
     */
    public String getGroupName(){
        return GroupName;
    }

    /**
     * 返回发送这条信息的发送者群名片
     * @return 发送者群名片
     */
    public String getSenderNameCard(){
        return MemberName;
    }

    /**
     * 获取发送者在目标群的管理权限
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getSenderPermission(){
        return Permission;
    }

    /**
     * 返回机器人解除禁言的剩余时间(如果已被禁言)
     * 此方法会同时判断目标群是否开启全员禁言，如果开启，则返回 -1
     * @return 禁言时间(秒) - 全员禁言返回 -1
     */
    public int getBotMuteRemainTime() {
        if(isMuteAll() && getBotPermission() == 0) {
            return -1;
        } else return event.getGroup().getBotMuteRemaining();
    }

    /**
     * 获取目标群的群成员列表
     * 此方法只返回QQ号
     * @return 群成员列表
     */
    public List<Long> getGroupMemberList(){
        ContactList<NormalMember> GroupMemberListOrigin = event.getGroup().getMembers(); // 原始数组
        List<Long> GroupMemberList = new ArrayList<>(); // 创建用于返回数据的数组
        for(NormalMember Member : GroupMemberListOrigin){ GroupMemberList.add(Member.getId()); } // 为返回的数组加入成员
        return GroupMemberList;
    }

    /**
     * 获取机器人在目标群的管理权限
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getBotPermission(){
        return GroupPermission;
    }

    /**
     * 判断目标群是否允许普通成员邀请新成员
     * @return 允许返回true，不允许返回false
     */
    public boolean isAllowMemberInvite(){
        return event.getGroup().getSettings().isAllowMemberInvite();
    }

    /**
     * 判断目标群是否允许匿名聊天
     * @return 允许返回true，不允许返回false
     */
    public boolean isAnonymousChatEnabled(){
        return event.getGroup().getSettings().isAnonymousChatEnabled();
    }

    /**
     * 判断目标群是否全员禁言
     * @return 全员禁言返回true，否则返回false
     */
    public boolean isMuteAll(){
        return event.getGroup().getSettings().isMuteAll();
    }

    /**
     * 判断目标群是否启用自动加群审批
     * @return 启用返回true，禁用返回false
     */
    public boolean isAutoApproveEnabled(){
        return event.getGroup().getSettings().isAutoApproveEnabled();
    }

    /**
     * 撤回这条消息（要求机器人具有管理员或群主权限）
     */
    public void recall() {
        MessageSource.recall(event.getMessage());
    }

    /**
     * 等待指定时间后撤回这条消息（要求机器人具有管理员或群主权限）<br>
     * 此方法执行异步(Async)任务
     * @param delayTime 延迟时间（毫秒）
     */
    public void recall(long delayTime){
        MessageSource.recallIn(event.getMessage(), delayTime);
    }

    /**
     * 向发送来源发送消息（HTTPAPI下支持 Mirai Code）
     * @param message 消息内容
     */
    @Override
    public void sendMessage(String message) {
        if(getType() == EventType.CORE){
            super.sendMessage(message);
        } else if(getType() == EventType.HTTPAPI){
            try {
                MiraiHttpAPI.INSTANCE.sendGroupMessage(MiraiHttpAPI.Bots.get(BotID), GroupID, message);
            } catch (IOException | AbnormalStatusException e) {
                Utils.logger.warning("发送消息时出现异常，原因: " + e);
            }
        }
    }

    /**
     * 向发送来源发送消息（支持 Mirai Code）
     * @param message 消息内容
     */
    @Override
    public void sendMessageMirai(String message) {
        if(getType() == EventType.CORE){
            super.sendMessageMirai(message);
        } else if(getType() == EventType.HTTPAPI){
            try {
                MiraiHttpAPI.INSTANCE.sendGroupMessage(MiraiHttpAPI.Bots.get(BotID), GroupID, message);
            } catch (IOException | AbnormalStatusException e) {
                Utils.logger.warning("发送消息时出现异常，原因: " + e);
            }
        }
    }

    /**
     * 获取群实例
     * @return MiraiGroup 实例
     */
    public MiraiGroup getGroup(){
        return new MiraiGroup(event.getBot(), event.getGroup().getId());
    }
}
