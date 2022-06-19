package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.bukkit.event.bot.*;
import me.dreamvoid.miraimc.bukkit.event.friend.*;
import me.dreamvoid.miraimc.bukkit.event.group.*;
import me.dreamvoid.miraimc.bukkit.event.group.member.*;
import me.dreamvoid.miraimc.bukkit.event.group.setting.*;
import me.dreamvoid.miraimc.bukkit.event.message.*;
import me.dreamvoid.miraimc.bukkit.event.message.passive.*;
import me.dreamvoid.miraimc.bukkit.event.message.postsend.*;
import me.dreamvoid.miraimc.bukkit.event.message.presend.*;
import me.dreamvoid.miraimc.bukkit.event.message.recall.*;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.*;
import org.bukkit.Bukkit;

import static me.dreamvoid.miraimc.bukkit.event.bot.MiraiBotOfflineEvent.Type.*;

public class MiraiEvent {
    private Listener<BotOnlineEvent> BotOnlineListener;
    private Listener<BotOfflineEvent.Active> BotOfflineActiveListener;
    private Listener<BotOfflineEvent.Force> BotOfflineForceListener;
    private Listener<BotOfflineEvent.Dropped> BotOfflineDroppedListener;
    private Listener<BotOfflineEvent.RequireReconnect> BotOfflineRequireReconnectListener;
    private Listener<BotReloginEvent> BotReloginEventListener;
    private Listener<BotAvatarChangedEvent> BotAvatarChangedEventListener;
    private Listener<BotNickChangedEvent> BotNickChangedEventListener;

    private Listener<GroupMessageEvent> GroupMessageListener;
    private Listener<FriendMessageEvent> FriendMessageListener;
    private Listener<GroupTempMessageEvent> GroupTempMessageEventListener;
    private Listener<StrangerMessageEvent> StrangerMessageEventListener;
    private Listener<OtherClientMessageEvent> OtherClientMessageEventListener;

    private Listener<GroupMessagePreSendEvent> GroupMessagePreSendEventListener;
    private Listener<FriendMessagePreSendEvent> FriendMessagePreSendEventListener;
    private Listener<GroupTempMessagePreSendEvent> GroupTempMessagePreSendEventListener;
    private Listener<StrangerMessagePreSendEvent> StrangerMessagePreSendEventListener;

    private Listener<GroupMessagePostSendEvent> GroupMessagePostSendEventListener;
    private Listener<FriendMessagePostSendEvent> FriendMessagePostSendEventListener;
    private Listener<GroupTempMessagePostSendEvent> GroupTempMessagePostSendEventListener;
    private Listener<StrangerMessagePostSendEvent> StrangerMessagePostSendEventListener;

    private Listener<MessageRecallEvent.FriendRecall> FriendMessageRecallEventListener;
    private Listener<MessageRecallEvent.GroupRecall> GroupMessageRecallEventListener;

    private Listener<BeforeImageUploadEvent> BeforeImageUploadEventListener;
    private Listener<ImageUploadEvent.Succeed> ImageUploadSucceedEventListener;
    private Listener<ImageUploadEvent.Failed> ImageUploadFailedEventListener;
    private Listener<NudgeEvent> NudgeEventListener;

    private Listener<BotLeaveEvent.Active> BotLeaveActiveEventListener;
    private Listener<BotLeaveEvent.Kick> BotLeaveKickEventListener;
    private Listener<BotGroupPermissionChangeEvent> BotGroupPermissionChangeEventListener;
    private Listener<BotMuteEvent> BotMuteEventListener;
    private Listener<BotUnmuteEvent> BotUnmuteEventListener;
    private Listener<BotJoinGroupEvent> BotJoinGroupEventListener;

    private Listener<GroupNameChangeEvent> GroupNameChangeEventListener;
    private Listener<GroupEntranceAnnouncementChangeEvent> GroupEntranceAnnouncementChangeEventListener;
    private Listener<GroupMuteAllEvent> GroupMuteAllEventListener;
    private Listener<GroupAllowAnonymousChatEvent> GroupAllowAnonymousChatEventListener;
    private Listener<GroupAllowMemberInviteEvent> GroupAllowMemberInviteEventListener;

    private Listener<MemberJoinEvent.Invite> MemberJoinInviteEventListener;
    private Listener<MemberJoinEvent.Active> MemberJoinActiveEventListener;
    private Listener<MemberLeaveEvent.Kick> MemberLeaveKickEventListener;
    private Listener<MemberLeaveEvent.Quit> MemberLeaveQuitEventListener;
    private Listener<MemberJoinRequestEvent> MemberJoinRequestEventListener;
    private Listener<BotInvitedJoinGroupRequestEvent> BotInvitedJoinGroupRequestEventListener;

    private Listener<MemberCardChangeEvent> MemberCardChangeEventListener;
    private Listener<MemberSpecialTitleChangeEvent> MemberSpecialTitleChangeEventListener;

    private Listener<MemberPermissionChangeEvent> MemberPermissionChangeEventListener;

    private Listener<MemberMuteEvent> MemberMuteEventListener;
    private Listener<MemberUnmuteEvent> MemberUnmuteEventListener;

    private Listener<FriendRemarkChangeEvent> FriendRemarkChangeEventListener;
    private Listener<FriendAddEvent> FriendAddEventListener;
    private Listener<FriendDeleteEvent> FriendDeleteEventListener;
    private Listener<NewFriendRequestEvent> NewFriendRequestEventListener;
    private Listener<FriendAvatarChangedEvent> FriendAvatarChangedEventListener;
    private Listener<FriendNickChangedEvent> FriendNickChangedEventListener;
    private Listener<FriendInputStatusChangedEvent> FriendInputStatusChangedEventListener;

    public void startListenEvent(){
        // Bot
        BotOnlineListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOnlineEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotOnlineEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiBotOnlineEvent(event));
        });
        BotOfflineActiveListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Active.class,event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotOfflineEvent(event, Active));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiBotOfflineEvent(event, Active));
        });
        BotOfflineForceListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Force.class,event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotOfflineEvent(event, Force));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiBotOfflineEvent(event, Force));
        });
        BotOfflineDroppedListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Dropped.class,event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotOfflineEvent(event, Dropped));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiBotOfflineEvent(event, Dropped));
        });
        BotOfflineRequireReconnectListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.RequireReconnect.class,event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotOfflineEvent(event, RequireReconnect));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiBotOfflineEvent(event, RequireReconnect));
        });
        BotReloginEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotReloginEvent.class,event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotReloginEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiBotReloginEvent(event));
        });
        BotAvatarChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotAvatarChangedEvent.class,event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotAvatarChangedEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiBotAvatarChangedEvent(event));
        });
        BotNickChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotNickChangedEvent.class,event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotNickChangedEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiBotNickChangedEvent(event));
        });

        // 消息
        // - 被动
        GroupMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupMessageEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent(event));
        });
        FriendMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendMessageEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessageEvent(event));
        });
        GroupTempMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessageEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupTempMessageEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupTempMessageEvent(event));
        });
        StrangerMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessageEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiStrangerMessageEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiStrangerMessageEvent(event));
        });
        OtherClientMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(OtherClientMessageEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiOtherClientMessageEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiOtherClientMessageEvent(event));
        });
        // - 主动前
        GroupMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessagePreSendEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupMessagePreSendEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessagePreSendEvent(event));
        });
        FriendMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessagePreSendEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendMessagePreSendEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessagePreSendEvent(event));
        });
        GroupTempMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessagePreSendEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupTempMessagePreSendEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupTempMessagePreSendEvent(event));
        });
        StrangerMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessagePreSendEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiStrangerMessagePreSendEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiStrangerMessagePreSendEvent(event));
        });
        // - 主动后
        GroupMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessagePostSendEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupMessagePostSendEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessagePostSendEvent(event));
        });
        FriendMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessagePostSendEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendMessagePostSendEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessagePostSendEvent(event));
        });
        GroupTempMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessagePostSendEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupTempMessagePostSendEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupTempMessagePostSendEvent(event));
        });
        StrangerMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessagePostSendEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiStrangerMessagePostSendEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiStrangerMessagePostSendEvent(event));
        });
        // - 撤回
        FriendMessageRecallEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageRecallEvent.FriendRecall.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendMessageRecallEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessageRecallEvent(event));
        });
        GroupMessageRecallEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageRecallEvent.GroupRecall.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupMessageRecallEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageRecallEvent(event));
        });
        // - 图片上传
        // -- 图片上传前
        BeforeImageUploadEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BeforeImageUploadEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBeforeImageUploadEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiBeforeImageUploadEvent(event));
        });
        // -- 图片上传完成
        ImageUploadSucceedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(ImageUploadEvent.Succeed.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiImageUploadEvent.Succeed(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiImageUploadSucceedEvent(event));
        });
        ImageUploadFailedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(ImageUploadEvent.Failed.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiImageUploadEvent.Failed(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiImageUploadFailedEvent(event));
        });
        // - 戳一戳
        NudgeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(NudgeEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiNudgeEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiNudgeEvent(event));
        });

        // 群
        BotLeaveActiveEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotLeaveEvent.Active.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotLeaveEvent.Active(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupBotLeaveEvent.Active(event));
        });
        BotLeaveKickEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotLeaveEvent.Kick.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotLeaveEvent.Kick(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupBotLeaveEvent.Kick(event));
        });
        BotGroupPermissionChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotGroupPermissionChangeEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotGroupPermissionChangeEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupBotPermissionChangeEvent(event));
        });
        BotMuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotMuteEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotMuteEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupBotMuteEvent(event));
        });
        BotUnmuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotUnmuteEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotUnmuteEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupBotUnmuteEvent(event));
        });
        BotJoinGroupEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotJoinGroupEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotJoinGroupEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupBotJoinGroupEvent(event));
        });
        // - 群设置
        GroupNameChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupNameChangeEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupNameChangeEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupNameChangeEvent(event));
        });
        GroupEntranceAnnouncementChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupEntranceAnnouncementChangeEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupEntranceAnnouncementChangeEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupEntranceAnnouncementChangeEvent(event));
        });
        GroupMuteAllEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMuteAllEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupMuteAllEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMuteAllEvent(event));
        });
        GroupAllowAnonymousChatEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupAllowAnonymousChatEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupAllowAnonymousChatEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupAllowAnonymousChatEvent(event));
        });
        GroupAllowMemberInviteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupAllowMemberInviteEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupAllowMemberInviteEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupAllowMemberInviteEvent(event));
        });
        // - 群成员
        // -- 成员列表变更
        MemberJoinInviteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinEvent.Invite.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiMemberJoinEvent.Invite(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberJoinEvent(event,event));
        });
        MemberJoinActiveEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinEvent.Active.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiMemberJoinEvent.Active(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberJoinEvent(event,event));
        });
        MemberLeaveKickEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberLeaveEvent.Kick.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiMemberLeaveEvent.Kick(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberLeaveEvent(event, event));
        });
        MemberLeaveQuitEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberLeaveEvent.Quit.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiMemberLeaveEvent.Quit(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberLeaveEvent(event, event));
        });
        MemberJoinRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinRequestEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiMemberJoinRequestEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberJoinRequestEvent(event));
        });
        BotInvitedJoinGroupRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotInvitedJoinGroupRequestEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotInvitedJoinGroupRequestEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupBotInvitedJoinGroupRequestEvent(event));
        });
        // -- 名片和头衔
        MemberCardChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberCardChangeEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiMemberCardChangeEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberCardChangeEvent(event));
        });
        MemberSpecialTitleChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberSpecialTitleChangeEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiMemberSpecialTitleChangeEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberSpecialTitleChangeEvent(event));
        });
        // -- 成员权限
        MemberPermissionChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberPermissionChangeEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiMemberPermissionChangeEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberPermissionChangeEvent(event));
        });
        // -- 动作
        MemberMuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberMuteEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiMemberMuteEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberMuteEvent(event));
        });
        MemberUnmuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberUnmuteEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiMemberUnmuteEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberUnmuteEvent(event));
        });

        // 好友
        FriendRemarkChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendRemarkChangeEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendRemarkChangeEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiFriendRemarkChangeEvent(event));
        });
        FriendAddEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendAddEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendAddEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiFriendAddEvent(event));
        });
        FriendDeleteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendDeleteEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendDeleteEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiFriendDeleteEvent(event));
        });
        NewFriendRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(NewFriendRequestEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiNewFriendRequestEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiNewFriendRequestEvent(event));
        });
        FriendAvatarChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendAvatarChangedEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendAvatarChangedEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiFriendAvatarChangedEvent(event));
        });
        FriendNickChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendNickChangedEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendNickChangedEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiFriendNickChangedEvent(event));
        });
        FriendInputStatusChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendInputStatusChangedEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendInputStatusChangedEvent(event));
            Bukkit.getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.bukkit.event.MiraiFriendInputStatusChangedEvent(event));
        });
    }

    public void stopListenEvent(){
        BotOnlineListener.complete();
        BotOfflineActiveListener.complete();
        BotOfflineForceListener.complete();
        BotOfflineDroppedListener.complete();
        BotOfflineRequireReconnectListener.complete();
        BotReloginEventListener.complete();
        BotAvatarChangedEventListener.complete();
        BotNickChangedEventListener.complete();

        GroupMessageListener.complete();
        FriendMessageListener.complete();
        GroupTempMessageEventListener.complete();
        StrangerMessageEventListener.complete();
        OtherClientMessageEventListener.complete();

        GroupMessagePreSendEventListener.complete();
        FriendMessagePreSendEventListener.complete();
        GroupTempMessagePreSendEventListener.complete();
        StrangerMessagePreSendEventListener.complete();

        GroupMessagePostSendEventListener.complete();
        FriendMessagePostSendEventListener.complete();
        GroupTempMessagePostSendEventListener.complete();
        StrangerMessagePostSendEventListener.complete();

        FriendMessageRecallEventListener.complete();
        GroupMessageRecallEventListener.complete();

        BeforeImageUploadEventListener.complete();
        ImageUploadSucceedEventListener.complete();
        ImageUploadFailedEventListener.complete();

        NudgeEventListener.complete();

        BotLeaveActiveEventListener.complete();
        BotLeaveKickEventListener.complete();
        BotGroupPermissionChangeEventListener.complete();
        BotMuteEventListener.complete();
        BotUnmuteEventListener.complete();
        BotJoinGroupEventListener.complete();

        GroupNameChangeEventListener.complete();
        GroupEntranceAnnouncementChangeEventListener.complete();
        GroupMuteAllEventListener.complete();
        GroupAllowAnonymousChatEventListener.complete();
        GroupAllowMemberInviteEventListener.complete();

        MemberJoinInviteEventListener.complete();
        MemberJoinActiveEventListener.complete();
        MemberLeaveKickEventListener.complete();
        MemberLeaveQuitEventListener.complete();
        MemberJoinRequestEventListener.complete();
        BotInvitedJoinGroupRequestEventListener.complete();

        MemberCardChangeEventListener.complete();
        MemberSpecialTitleChangeEventListener.complete();

        MemberPermissionChangeEventListener.complete();

        MemberMuteEventListener.complete();
        MemberUnmuteEventListener.complete();

        FriendRemarkChangeEventListener.complete();
        FriendAddEventListener.complete();
        FriendDeleteEventListener.complete();
        NewFriendRequestEventListener.complete();
        FriendAvatarChangedEventListener.complete();
        FriendNickChangedEventListener.complete();
        FriendInputStatusChangedEventListener.complete();
    }
}
