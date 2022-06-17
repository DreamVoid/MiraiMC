package me.dreamvoid.miraimc.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import me.dreamvoid.miraimc.velocity.event.bot.*;
import me.dreamvoid.miraimc.velocity.event.friend.*;
import me.dreamvoid.miraimc.velocity.event.group.*;
import me.dreamvoid.miraimc.velocity.event.group.member.*;
import me.dreamvoid.miraimc.velocity.event.group.setting.*;
import me.dreamvoid.miraimc.velocity.event.message.*;
import me.dreamvoid.miraimc.velocity.event.message.passive.*;
import me.dreamvoid.miraimc.velocity.event.message.postsend.*;
import me.dreamvoid.miraimc.velocity.event.message.presend.*;
import me.dreamvoid.miraimc.velocity.event.message.recall.*;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.*;

import static me.dreamvoid.miraimc.velocity.event.bot.MiraiBotOfflineEvent.Type.*;

public class MiraiEvent {
    final ProxyServer server;

    public MiraiEvent(VelocityPlugin plugin) {
        server = plugin.getServer();
    }

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
        BotOnlineListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOnlineEvent.class, event -> server.getEventManager().fire(new MiraiBotOnlineEvent(event)));
        BotOfflineActiveListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Active.class,event -> server.getEventManager().fire(new MiraiBotOfflineEvent(event, Active)));
        BotOfflineForceListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Force.class,event -> server.getEventManager().fire(new MiraiBotOfflineEvent(event, Force)));
        BotOfflineDroppedListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Dropped.class,event -> server.getEventManager().fire(new MiraiBotOfflineEvent(event, Dropped)));
        BotOfflineRequireReconnectListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.RequireReconnect.class,event -> server.getEventManager().fire(new MiraiBotOfflineEvent(event, RequireReconnect)));
        BotReloginEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotReloginEvent.class,event -> server.getEventManager().fire(new MiraiBotReloginEvent(event)));
        BotAvatarChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotAvatarChangedEvent.class,event -> server.getEventManager().fire(new MiraiBotAvatarChangedEvent(event)));
        BotNickChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotNickChangedEvent.class,event -> server.getEventManager().fire(new MiraiBotNickChangedEvent(event)));

        // 消息
        // - 被动
        GroupMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> server.getEventManager().fire(new MiraiGroupMessageEvent(event)));
        FriendMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> server.getEventManager().fire(new MiraiFriendMessageEvent(event)));
        GroupTempMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessageEvent.class, event -> server.getEventManager().fire(new MiraiGroupTempMessageEvent(event)));
        StrangerMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessageEvent.class, event -> server.getEventManager().fire(new MiraiStrangerMessageEvent(event)));
        OtherClientMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(OtherClientMessageEvent.class, event -> server.getEventManager().fire(new MiraiOtherClientMessageEvent(event)));
        // - 主动前
        GroupMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessagePreSendEvent.class, event -> server.getEventManager().fire(new MiraiGroupMessagePreSendEvent(event)));
        FriendMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessagePreSendEvent.class, event -> server.getEventManager().fire(new MiraiFriendMessagePreSendEvent(event)));
        GroupTempMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessagePreSendEvent.class, event -> server.getEventManager().fire(new MiraiGroupTempMessagePreSendEvent(event)));
        StrangerMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessagePreSendEvent.class, event -> server.getEventManager().fire(new MiraiStrangerMessagePreSendEvent(event)));
        // - 主动后
        GroupMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessagePostSendEvent.class, event -> server.getEventManager().fire(new MiraiGroupMessagePostSendEvent(event)));
        FriendMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessagePostSendEvent.class, event -> server.getEventManager().fire(new MiraiFriendMessagePostSendEvent(event)));
        GroupTempMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessagePostSendEvent.class, event -> server.getEventManager().fire(new MiraiGroupTempMessagePostSendEvent(event)));
        StrangerMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessagePostSendEvent.class, event -> server.getEventManager().fire(new MiraiStrangerMessagePostSendEvent(event)));
        // - 撤回
        FriendMessageRecallEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageRecallEvent.FriendRecall.class, event -> server.getEventManager().fire(new MiraiFriendMessageRecallEvent(event)));
        GroupMessageRecallEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageRecallEvent.GroupRecall.class, event -> server.getEventManager().fire(new MiraiGroupMessageRecallEvent(event)));
        // - 图片上传
        // -- 图片上传前
        BeforeImageUploadEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BeforeImageUploadEvent.class, event -> server.getEventManager().fire(new MiraiBeforeImageUploadEvent(event)));
        // -- 图片上传完成
        ImageUploadSucceedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(ImageUploadEvent.Succeed.class, event -> server.getEventManager().fire(new MiraiImageUploadEvent.Succeed(event)));
        ImageUploadFailedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(ImageUploadEvent.Failed.class, event -> server.getEventManager().fire(new MiraiImageUploadEvent.Failed(event)));
        // - 戳一戳
        NudgeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(NudgeEvent.class, event -> server.getEventManager().fire(new MiraiNudgeEvent(event)));

        // 群
        BotLeaveActiveEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotLeaveEvent.Active.class, event -> server.getEventManager().fire(new MiraiBotLeaveEvent(event)));
        BotLeaveKickEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotLeaveEvent.Kick.class, event -> server.getEventManager().fire(new MiraiBotLeaveEvent(event)));
        BotGroupPermissionChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotGroupPermissionChangeEvent.class, event -> server.getEventManager().fire(new MiraiBotGroupPermissionChangeEvent(event)));
        BotMuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotMuteEvent.class, event -> server.getEventManager().fire(new MiraiBotMuteEvent(event)));
        BotUnmuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotUnmuteEvent.class, event -> server.getEventManager().fire(new MiraiBotUnmuteEvent(event)));
        BotJoinGroupEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotJoinGroupEvent.class, event -> server.getEventManager().fire(new MiraiBotJoinGroupEvent(event)));
        // - 群设置
        GroupNameChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupNameChangeEvent.class, event -> server.getEventManager().fire(new MiraiGroupNameChangeEvent(event)));
        GroupEntranceAnnouncementChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupEntranceAnnouncementChangeEvent.class, event -> server.getEventManager().fire(new MiraiGroupEntranceAnnouncementChangeEvent(event)));
        GroupMuteAllEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMuteAllEvent.class, event -> server.getEventManager().fire(new MiraiGroupMuteAllEvent(event)));
        GroupAllowAnonymousChatEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupAllowAnonymousChatEvent.class, event -> server.getEventManager().fire(new MiraiGroupAllowAnonymousChatEvent(event)));
        GroupAllowMemberInviteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupAllowMemberInviteEvent.class, event -> server.getEventManager().fire(new MiraiGroupAllowMemberInviteEvent(event)));
        // - 群成员
        // -- 成员列表变更
        MemberJoinInviteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinEvent.Invite.class, event -> server.getEventManager().fire(new MiraiMemberJoinEvent.Invite(event)));
        MemberJoinActiveEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinEvent.Active.class, event -> server.getEventManager().fire(new MiraiMemberJoinEvent.Active(event)));
        MemberLeaveKickEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberLeaveEvent.Kick.class, event -> server.getEventManager().fire(new MiraiMemberLeaveEvent.Kick(event)));
        MemberLeaveQuitEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberLeaveEvent.Quit.class, event -> server.getEventManager().fire(new MiraiMemberLeaveEvent.Quit(event)));
        MemberJoinRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinRequestEvent.class, event -> server.getEventManager().fire(new MiraiMemberJoinRequestEvent(event)));
        BotInvitedJoinGroupRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotInvitedJoinGroupRequestEvent.class, event -> server.getEventManager().fire(new MiraiBotInvitedJoinGroupRequestEvent(event)));
        // -- 名片和头衔
        MemberCardChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberCardChangeEvent.class, event -> server.getEventManager().fire(new MiraiMemberCardChangeEvent(event)));
        MemberSpecialTitleChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberSpecialTitleChangeEvent.class, event -> server.getEventManager().fire(new MiraiMemberSpecialTitleChangeEvent(event)));
        // -- 成员权限
        MemberPermissionChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberPermissionChangeEvent.class, event -> server.getEventManager().fire(new MiraiMemberPermissionChangeEvent(event)));
        // -- 动作
        MemberMuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberMuteEvent.class, event -> server.getEventManager().fire(new MiraiMemberMuteEvent(event)));
        MemberUnmuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberUnmuteEvent.class, event -> server.getEventManager().fire(new MiraiMemberUnmuteEvent(event)));

        // 好友
        FriendRemarkChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendRemarkChangeEvent.class, event -> server.getEventManager().fire(new MiraiFriendRemarkChangeEvent(event)));
        FriendAddEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendAddEvent.class, event -> server.getEventManager().fire(new MiraiFriendAddEvent(event)));
        FriendDeleteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendDeleteEvent.class, event -> server.getEventManager().fire(new MiraiFriendDeleteEvent(event)));
        NewFriendRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(NewFriendRequestEvent.class, event -> server.getEventManager().fire(new MiraiNewFriendRequestEvent(event)));
        FriendAvatarChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendAvatarChangedEvent.class, event -> server.getEventManager().fire(new MiraiFriendAvatarChangedEvent(event)));
        FriendNickChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendNickChangedEvent.class, event -> server.getEventManager().fire(new MiraiFriendNickChangedEvent(event)));
        FriendInputStatusChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendInputStatusChangedEvent.class, event -> server.getEventManager().fire(new MiraiFriendInputStatusChangedEvent(event)));
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
