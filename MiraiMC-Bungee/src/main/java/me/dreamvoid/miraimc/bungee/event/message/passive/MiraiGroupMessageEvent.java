package me.dreamvoid.miraimc.bungee.event.message.passive;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.internal.httpapi.exception.AbnormalStatusException;
import me.dreamvoid.miraimc.internal.httpapi.response.FetchMessage;
import me.dreamvoid.miraimc.internal.httpapi.type.Message;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageSource;
import net.md_5.bungee.api.ProxyServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * (bungee) Mirai 核心事件 - 消息 - 被动收到消息 - 群消息
 */
public class MiraiGroupMessageEvent extends AbstractMessageEvent {
    public MiraiGroupMessageEvent(GroupMessageEvent event) {
        super(event);
        this.event = event;

        type = 0;
        botID = event.getBot().getId();
        senderID = event.getSender().getId();
        memberName = event.getSender().getNameCard();
        messageContent = event.getMessage().contentToString();
        messageMiraiCode = event.getMessage().serializeToMiraiCode();
        time = event.getTime();
        GroupID = event.getGroup().getId();
        GroupName = event.getGroup().getName();

        ProxyServer.getInstance().getPluginManager().callEvent(new me.dreamvoid.miraimc.bungee.event.MiraiGroupMessageEvent(event));
    }

    public MiraiGroupMessageEvent(long BotAccount, FetchMessage.Sender sender, Message message) {
        type = 1;
        botID = BotAccount;
        senderID = sender.id;
        memberName = sender.memberName;
        messageContent = message.text;
        messageMiraiCode = message.text;
        time = message.time;
        GroupID = sender.group.id;
        GroupName = sender.group.name;

        ProxyServer.getInstance().getPluginManager().callEvent(new me.dreamvoid.miraimc.bungee.event.MiraiGroupMessageEvent(BotAccount, sender, message));
    }

    private GroupMessageEvent event;

    private final int type;
    private final long botID;
    private final long senderID;
    private final String memberName;
    private final String messageContent;
    private final String messageMiraiCode;
    private final int time;
    private final long GroupID;
    private final String GroupName;

    /**
     * 返回接收到这条信息的机器人ID
     * @return 机器人ID
     */
    @Override
    public long getBotID(){
        return botID;
    }

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
     * 返回发送这条信息的发送者ID
     * @return 发送者ID
     */
    @Override
    public long getSenderID(){
        return senderID;
    }

    /**
     * 返回发送这条信息的发送者群名片
     * @return 发送者群名片
     */
    public String getSenderNameCard(){
        return memberName;
    }

    /**
     * 返回接收到的消息内容转换到字符串的结果<br>
     * 此方法使用 contentToString()<br>
     * QQ 对话框中以纯文本方式会显示的消息内容，这适用于MC与QQ的消息互通等不方便展示原始内容的场景。<br>
     * 无法用纯文字表示的消息会丢失信息，如任何图片都是 [图片]
     * @return 转换字符串后的消息内容
     */
    @Override
    public String getMessage(){
        return messageContent;
    }

    /**
     * 返回接收到的消息内容转换到Mirai Code的结果<br>
     * 此方法使用 serializeToMiraiCode()<br>
     * 转换为对应的 Mirai 码，消息的一种序列化方式
     * @return 带Mirai Code的消息内容
     */
    @Override
    public String getMessageToMiraiCode(){
        return messageMiraiCode;
    }

    /**
     * 返回接收到这条信息的时间
     * @return 发送时间
     */
    @Override
    public int getTime(){
        return time;
    }

    /**
     * 获取发送者在目标群的管理权限
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getSenderPermission(){
        return event.getSender().getPermission().getLevel();
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
        return event.getGroup().getBotPermission().getLevel();
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
     * 向发送来源发送消息（支持 Mirai Code）
     * @param message 消息内容
     */
    @Override
    public void sendMessage(String message) {
        if(type == 0){
            event.getGroup().sendMessage(MiraiCode.deserializeMiraiCode(message));
        } else if(type == 1){
            try {
                MiraiHttpAPI.INSTANCE.sendGroupMessage(MiraiHttpAPI.Bots.get(botID), GroupID, message);
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

    /**
     * 获取事件类型（用于判断机器人工作模式）
     * @return 0 = Core | 1 = HTTP API
     */
    public int getType() {
        return type;
    }
}
