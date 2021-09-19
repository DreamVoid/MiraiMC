package me.dreamvoid.miraimc.nukkit.event;

import cn.nukkit.event.HandlerList;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupTempMessagePreSendEvent;
import cn.nukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * 主动发送消息前 - 群临时会话消息
 */
public class MiraiGroupTempMessagePreSendEvent extends Event {

    public MiraiGroupTempMessagePreSendEvent(GroupTempMessagePreSendEvent event) {
        this.event = event;
    }

    private final GroupTempMessagePreSendEvent event;

    private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlers() { return handlers; }
    //public static HandlerList getHandlerList() { return handlers; }

    // 所有事件公有方法
    /**
     * 返回接收这条信息的机器人ID
     * @return 机器人ID
     */
    public long getBotID(){
        return event.getBot().getId();
    }

    /**
     * 返回目标好友的QQ号
     * @return 好友QQ号
     */
    public long getSenderID(){
        return event.getTarget().getId();
    }

    /**
     * 返回目标群成员的昵称
     * @return 昵称
     */
    public String getSenderNickName(){ return event.getTarget().getNick(); }

    /**
     * 返回目标群成员的群名片
     * @return 群名片
     */
    public String getSenderNameCard(){ return event.getTarget().getNameCard(); }

    /**
     * 返回目标群成员的备注名
     * @return 备注名
     */
    public String getRemark(){ return event.getTarget().getRemark(); }

    /**
     * 返回接收到的消息内容<br>
     * 此方法使用 toString()<br>
     * Java 对象的 toString()，会尽可能包含多的信息用于调试作用，行为可能不确定
     * @return 原始消息内容
     */
    public String getMessage(){
        return event.getMessage().toString();
    }

    /**
     * 返回接收到的消息内容转换到字符串的结果<br>
     * 此方法使用 contentToString()<br>
     * QQ 对话框中以纯文本方式会显示的消息内容，这适用于MC与QQ的消息互通等不方便展示原始内容的场景。<br>
     * 无法用纯文字表示的消息会丢失信息，如任何图片都是 [图片]
     * @return 转换字符串后的消息内容
     */
    public String getMessageContent(){
        return event.getMessage().contentToString();
    }

    /**
     * 返回目标群成员解除禁言的剩余时间(如果已被禁言)
     * 此方法会同时判断目标群是否开启全员禁言，如果开启，则返回 -1
     * @return 时间(秒)
     */
    public int getMemberMuteRemainTime(){
        if(isMuteAll() && getMemberPermission() == 0) {
            return -1;
        } else return event.getTarget().getMuteTimeRemaining();
    }

    /**
     * 获取目标群成员在目标群的管理权限
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getMemberPermission(){
        return event.getTarget().getPermission().getLevel();
    }

    // 群事件公有方法
    /**
     * 返回机器人解除禁言的剩余时间(如果已被禁言)
     * 此方法会同时判断目标群是否开启全员禁言，如果开启，则返回 -1
     * @return 禁言时间(秒) - 全员禁言返回 -1
     */
    public int getBotMuteRemainTime(){ return event.getGroup().getBotAsMember().getMuteTimeRemaining(); }

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
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }
}
