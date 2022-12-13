package me.dreamvoid.miraimc.sponge;

import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.sponge.event.*;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.plugin.PluginContainer;

import static me.dreamvoid.miraimc.sponge.event.bot.MiraiBotOfflineEvent.Type.*;

@SuppressWarnings("deprecation")
public class MiraiEventLegacy extends MiraiEvent {
	final EventContext eventContext;
	final PluginContainer pluginContainer;

	public MiraiEventLegacy(SpongePlugin plugin) {
		super(plugin);
		Thread.currentThread().setContextClassLoader(Utils.classLoader);
		pluginContainer = plugin.getPluginContainer();
		eventContext = EventContext.builder().add(EventContextKeys.PLUGIN, pluginContainer).build();
	}

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

	@Override
	public void startListenEvent(){
		// Bot
		BotOnlineListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOnlineEvent.class, event -> Sponge.getEventManager().post(new MiraiBotOnlineEvent(event, Cause.of(eventContext, pluginContainer))));
		BotOfflineActiveListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Active.class,event -> Sponge.getEventManager().post(new MiraiBotOfflineEvent(event, Active, Cause.of(eventContext, pluginContainer))));
		BotOfflineForceListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Force.class,event -> Sponge.getEventManager().post(new MiraiBotOfflineEvent(event, Force, Cause.of(eventContext, pluginContainer))));
		BotOfflineDroppedListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Dropped.class,event -> Sponge.getEventManager().post(new MiraiBotOfflineEvent(event, Dropped, Cause.of(eventContext, pluginContainer))));
		BotOfflineRequireReconnectListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.RequireReconnect.class,event -> Sponge.getEventManager().post(new MiraiBotOfflineEvent(event, RequireReconnect, Cause.of(eventContext, pluginContainer))));
		BotReloginEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotReloginEvent.class,event -> Sponge.getEventManager().post(new MiraiBotReloginEvent(event, Cause.of(eventContext, pluginContainer))));
		BotAvatarChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotAvatarChangedEvent.class,event -> Sponge.getEventManager().post(new MiraiBotAvatarChangedEvent(event, Cause.of(eventContext, pluginContainer))));
		BotNickChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotNickChangedEvent.class,event -> Sponge.getEventManager().post(new MiraiBotNickChangedEvent(event, Cause.of(eventContext, pluginContainer))));

		// 消息
		// - 被动
		GroupMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupMessageEvent(event, Cause.of(eventContext, pluginContainer))));
		FriendMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> Sponge.getEventManager().post(new MiraiFriendMessageEvent(event, Cause.of(eventContext, pluginContainer))));
		GroupTempMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessageEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupTempMessageEvent(event, Cause.of(eventContext, pluginContainer))));
		StrangerMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessageEvent.class, event -> Sponge.getEventManager().post(new MiraiStrangerMessageEvent(event, Cause.of(eventContext, pluginContainer))));
		OtherClientMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(OtherClientMessageEvent.class, event -> Sponge.getEventManager().post(new MiraiOtherClientMessageEvent(event, Cause.of(eventContext, pluginContainer))));
		// - 主动前
		GroupMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessagePreSendEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupMessagePreSendEvent(event, Cause.of(eventContext, pluginContainer))));
		FriendMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessagePreSendEvent.class, event -> Sponge.getEventManager().post(new MiraiFriendMessagePreSendEvent(event, Cause.of(eventContext, pluginContainer))));
		GroupTempMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessagePreSendEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupTempMessagePreSendEvent(event, Cause.of(eventContext, pluginContainer))));
		StrangerMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessagePreSendEvent.class, event -> Sponge.getEventManager().post(new MiraiStrangerMessagePreSendEvent(event, Cause.of(eventContext, pluginContainer))));
		// - 主动后
		GroupMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessagePostSendEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupMessagePostSendEvent(event, Cause.of(eventContext, pluginContainer))));
		FriendMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessagePostSendEvent.class, event -> Sponge.getEventManager().post(new MiraiFriendMessagePostSendEvent(event, Cause.of(eventContext, pluginContainer))));
		GroupTempMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessagePostSendEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupTempMessagePostSendEvent(event, Cause.of(eventContext, pluginContainer))));
		StrangerMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessagePostSendEvent.class, event -> Sponge.getEventManager().post(new MiraiStrangerMessagePostSendEvent(event, Cause.of(eventContext, pluginContainer))));
		// - 撤回
		FriendMessageRecallEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageRecallEvent.FriendRecall.class, event -> Sponge.getEventManager().post(new MiraiFriendMessageRecallEvent(event, Cause.of(eventContext, pluginContainer))));
		GroupMessageRecallEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MessageRecallEvent.GroupRecall.class, event -> Sponge.getEventManager().post(new MiraiGroupMessageRecallEvent(event, Cause.of(eventContext, pluginContainer))));
		// - 图片上传
		// -- 图片上传前
		BeforeImageUploadEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BeforeImageUploadEvent.class, event -> Sponge.getEventManager().post(new MiraiBeforeImageUploadEvent(event, Cause.of(eventContext, pluginContainer))));
		// -- 图片上传完成
		ImageUploadSucceedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(ImageUploadEvent.Succeed.class, event -> Sponge.getEventManager().post(new MiraiImageUploadSucceedEvent(event, Cause.of(eventContext, pluginContainer))));
		ImageUploadFailedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(ImageUploadEvent.Failed.class, event -> Sponge.getEventManager().post(new MiraiImageUploadFailedEvent(event, Cause.of(eventContext, pluginContainer))));
		// - 戳一戳
		NudgeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(NudgeEvent.class, event -> Sponge.getEventManager().post(new MiraiNudgeEvent(event, Cause.of(eventContext, pluginContainer))));

		// 群
		BotLeaveActiveEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotLeaveEvent.Active.class, event -> Sponge.getEventManager().post(new MiraiGroupBotLeaveEvent(event, Cause.of(eventContext, pluginContainer))));
		BotLeaveKickEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotLeaveEvent.Kick.class, event -> Sponge.getEventManager().post(new MiraiGroupBotLeaveEvent(event, Cause.of(eventContext, pluginContainer))));
		BotGroupPermissionChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotGroupPermissionChangeEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupBotPermissionChangeEvent(event, Cause.of(eventContext, pluginContainer))));
		BotMuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotMuteEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupBotMuteEvent(event, Cause.of(eventContext, pluginContainer))));
		BotUnmuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotUnmuteEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupBotUnmuteEvent(event, Cause.of(eventContext, pluginContainer))));
		BotJoinGroupEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotJoinGroupEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupBotJoinGroupEvent(event, Cause.of(eventContext, pluginContainer))));
		// - 群设置
		GroupNameChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupNameChangeEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupNameChangeEvent(event, Cause.of(eventContext, pluginContainer))));
		GroupEntranceAnnouncementChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupEntranceAnnouncementChangeEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupEntranceAnnouncementChangeEvent(event, Cause.of(eventContext, pluginContainer))));
		GroupMuteAllEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMuteAllEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupMuteAllEvent(event, Cause.of(eventContext, pluginContainer))));
		GroupAllowAnonymousChatEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupAllowAnonymousChatEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupAllowAnonymousChatEvent(event, Cause.of(eventContext, pluginContainer))));
		GroupAllowMemberInviteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupAllowMemberInviteEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupAllowMemberInviteEvent(event, Cause.of(eventContext, pluginContainer))));
		// - 群成员
		// -- 成员列表变更
		MemberJoinInviteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinEvent.Invite.class, event -> Sponge.getEventManager().post(new MiraiGroupMemberJoinEvent(event, event, Cause.of(eventContext, pluginContainer))));
		MemberJoinActiveEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinEvent.Active.class, event -> Sponge.getEventManager().post(new MiraiGroupMemberJoinEvent(event, event, Cause.of(eventContext, pluginContainer))));
		MemberLeaveKickEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberLeaveEvent.Kick.class, event -> Sponge.getEventManager().post(new MiraiGroupMemberLeaveEvent(event, event, Cause.of(eventContext, pluginContainer))));
		MemberLeaveQuitEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberLeaveEvent.Quit.class, event -> Sponge.getEventManager().post(new MiraiGroupMemberLeaveEvent(event, event, Cause.of(eventContext, pluginContainer))));
		MemberJoinRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberJoinRequestEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupMemberJoinRequestEvent(event, Cause.of(eventContext, pluginContainer))));
		BotInvitedJoinGroupRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotInvitedJoinGroupRequestEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupBotInvitedJoinGroupRequestEvent(event, Cause.of(eventContext, pluginContainer))));
		// -- 名片和头衔
		MemberCardChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberCardChangeEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupMemberCardChangeEvent(event, Cause.of(eventContext, pluginContainer))));
		MemberSpecialTitleChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberSpecialTitleChangeEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupMemberSpecialTitleChangeEvent(event, Cause.of(eventContext, pluginContainer))));
		// -- 成员权限
		MemberPermissionChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberPermissionChangeEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupMemberPermissionChangeEvent(event, Cause.of(eventContext, pluginContainer))));
		// -- 动作
		MemberMuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberMuteEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupMemberMuteEvent(event, Cause.of(eventContext, pluginContainer))));
		MemberUnmuteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(MemberUnmuteEvent.class, event -> Sponge.getEventManager().post(new MiraiGroupMemberUnmuteEvent(event, Cause.of(eventContext, pluginContainer))));

		// 好友
		FriendRemarkChangeEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendRemarkChangeEvent.class, event -> Sponge.getEventManager().post(new MiraiFriendRemarkChangeEvent(event, Cause.of(eventContext, pluginContainer))));
		FriendAddEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendAddEvent.class, event -> Sponge.getEventManager().post(new MiraiFriendAddEvent(event, Cause.of(eventContext, pluginContainer))));
		FriendDeleteEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendDeleteEvent.class, event -> Sponge.getEventManager().post(new MiraiFriendDeleteEvent(event, Cause.of(eventContext, pluginContainer))));
		NewFriendRequestEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(NewFriendRequestEvent.class, event -> Sponge.getEventManager().post(new MiraiNewFriendRequestEvent(event, Cause.of(eventContext, pluginContainer))));
		FriendAvatarChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendAvatarChangedEvent.class, event -> Sponge.getEventManager().post(new MiraiFriendAvatarChangedEvent(event, Cause.of(eventContext, pluginContainer))));
		FriendNickChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendNickChangedEvent.class, event -> Sponge.getEventManager().post(new MiraiFriendNickChangedEvent(event, Cause.of(eventContext, pluginContainer))));
		FriendInputStatusChangedEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendInputStatusChangedEvent.class, event -> Sponge.getEventManager().post(new MiraiFriendInputStatusChangedEvent(event, Cause.of(eventContext, pluginContainer))));
	}

	@Override
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