package me.dreamvoid.miraimc.waterdogpe;


import me.dreamvoid.miraimc.waterdogpe.event.*;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.*;

public class MiraiEvent {
    private final WDPEPlugin plugin;

    private Listener<BotOnlineEvent> BotOnlineListener;
    private Listener<BotOfflineEvent.Active> BotOfflineActiveListener;
    private Listener<BotOfflineEvent.Force> BotOfflineForceListener;
    private Listener<BotOfflineEvent.Dropped> BotOfflineDroppedListener;
    private Listener<BotOfflineEvent.RequireReconnect> BotOfflineRequireReconnectListener;
    // TODO: BotOfflineEvent.MsfOffline
    // TODO: BotOfflineEvent.CauseAware
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
    private Listener<BotJoinGroupEvent> BotJoinGroupEventListener; // TODO: BotJoinGroupEvent.Active
    // TODO: BotJoinGroupEvent.Invite
    // TODO: BotJoinGroupEvent.Retrieve

    private Listener<GroupNameChangeEvent> GroupNameChangeEventListener;
    private Listener<GroupEntranceAnnouncementChangeEvent> GroupEntranceAnnouncementChangeEventListener;
    private Listener<GroupMuteAllEvent> GroupMuteAllEventListener;
    private Listener<GroupAllowAnonymousChatEvent> GroupAllowAnonymousChatEventListener;
    private Listener<GroupAllowMemberInviteEvent> GroupAllowMemberInviteEventListener;

    private Listener<MemberJoinEvent.Invite> MemberJoinInviteEventListener;
    private Listener<MemberJoinEvent.Active> MemberJoinActiveEventListener;
    // TODO: MemberJoinEvent.Retrieve
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

    public MiraiEvent(WDPEPlugin plugin) {
        this.plugin = plugin;
    }

    public void startListenEvent() {
        // Bot
        BotOnlineListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOnlineEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiBotOnlineEvent(event)));
        BotOfflineActiveListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Active.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiBotOfflineEvent(event, "Active")));
        BotOfflineForceListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Force.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiBotOfflineEvent(event, "Force")));
        BotOfflineDroppedListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Dropped.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiBotOfflineEvent(event, "Dropped")));
        BotOfflineRequireReconnectListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.RequireReconnect.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiBotOfflineEvent(event, "RequireReconnect")));
        BotReloginEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotReloginEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiBotReloginEvent(event)));
        BotAvatarChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotAvatarChangedEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiBotAvatarChangedEvent(event)));
        BotNickChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotNickChangedEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiBotNickChangedEvent(event)));

        // 消息
        // - 被动
        GroupMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMessageEvent(event)));
        FriendMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiFriendMessageEvent(event)));
        GroupTempMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessageEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupTempMessageEvent(event)));
        StrangerMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessageEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiStrangerMessageEvent(event)));
        OtherClientMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(OtherClientMessageEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiOtherClientMessageEvent(event)));
        // - 主动前
        GroupMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessagePreSendEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMessagePreSendEvent(event)));
        FriendMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessagePreSendEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiFriendMessagePreSendEvent(event)));
        GroupTempMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessagePreSendEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupTempMessagePreSendEvent(event)));
        StrangerMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessagePreSendEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiStrangerMessagePreSendEvent(event)));
        // - 主动后
        GroupMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessagePostSendEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMessagePostSendEvent(event)));
        FriendMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessagePostSendEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiFriendMessagePostSendEvent(event)));
        GroupTempMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessagePostSendEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupTempMessagePostSendEvent(event)));
        StrangerMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessagePostSendEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiStrangerMessagePostSendEvent(event)));
        // - 撤回
        FriendMessageRecallEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageRecallEvent.FriendRecall.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiFriendMessageRecallEvent(event)));
        GroupMessageRecallEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageRecallEvent.GroupRecall.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMessageRecallEvent(event)));
        // - 图片上传
        // -- 图片上传前
        BeforeImageUploadEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BeforeImageUploadEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiBeforeImageUploadEvent(event)));
        // -- 图片上传完成
        ImageUploadSucceedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(ImageUploadEvent.Succeed.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiImageUploadSucceedEvent(event)));
        ImageUploadFailedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(ImageUploadEvent.Failed.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiImageUploadFailedEvent(event)));
        // - 戳一戳
        NudgeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(NudgeEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiNudgeEvent(event)));

        // 群
        BotLeaveActiveEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotLeaveEvent.Active.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupBotLeaveEvent(event, event)));
        BotLeaveKickEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotLeaveEvent.Kick.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupBotLeaveEvent(event, event)));
        BotGroupPermissionChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotGroupPermissionChangeEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupBotPermissionChangeEvent(event)));
        BotMuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotMuteEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupBotMuteEvent(event)));
        BotUnmuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotUnmuteEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupBotUnmuteEvent(event)));
        BotJoinGroupEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotJoinGroupEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupBotJoinGroupEvent(event)));
        // - 群设置
        GroupNameChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupNameChangeEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupNameChangeEvent(event)));
        GroupEntranceAnnouncementChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupEntranceAnnouncementChangeEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupEntranceAnnouncementChangeEvent(event)));
        GroupMuteAllEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMuteAllEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMuteAllEvent(event)));
        GroupAllowAnonymousChatEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupAllowAnonymousChatEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupAllowAnonymousChatEvent(event)));
        GroupAllowMemberInviteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupAllowMemberInviteEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupAllowMemberInviteEvent(event)));
        // - 群成员
        // -- 成员列表变更
        MemberJoinInviteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinEvent.Invite.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMemberJoinEvent(event, event)));
        MemberJoinActiveEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinEvent.Active.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMemberJoinEvent(event, event)));
        MemberLeaveKickEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberLeaveEvent.Kick.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMemberLeaveEvent(event, event)));
        MemberLeaveQuitEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberLeaveEvent.Quit.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMemberLeaveEvent(event, event)));
        MemberJoinRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinRequestEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMemberJoinRequestEvent(event)));
        BotInvitedJoinGroupRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotInvitedJoinGroupRequestEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupBotInvitedJoinGroupRequestEvent(event)));
        // -- 名片和头衔
        MemberCardChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberCardChangeEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMemberCardChangeEvent(event)));
        MemberSpecialTitleChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberSpecialTitleChangeEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMemberSpecialTitleChangeEvent(event)));
        // -- 成员权限
        MemberPermissionChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberPermissionChangeEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMemberPermissionChangeEvent(event)));
        // -- 动作
        MemberMuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberMuteEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMemberMuteEvent(event)));
        MemberUnmuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberUnmuteEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiGroupMemberUnmuteEvent(event)));

        // 好友
        FriendRemarkChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendRemarkChangeEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiFriendRemarkChangeEvent(event)));
        FriendAddEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendAddEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiFriendAddEvent(event)));
        FriendDeleteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendDeleteEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiFriendDeleteEvent(event)));
        NewFriendRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(NewFriendRequestEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiNewFriendRequestEvent(event)));
        FriendAvatarChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendAvatarChangedEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiFriendAvatarChangedEvent(event)));
        FriendNickChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendNickChangedEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiFriendNickChangedEvent(event)));
        FriendInputStatusChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendInputStatusChangedEvent.class, event -> plugin.getProxy().getEventManager().callEvent(new MiraiFriendInputStatusChangedEvent(event)));
    }

    public void stopListenEvent() {
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
