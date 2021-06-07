package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.listener.*;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.*;
import org.bukkit.Bukkit;

public class BotEvent {

    public BotEvent() { }

    private Listener BotOnlineListener;
    private Listener BotOfflineActiveListener;
    private Listener BotOfflineForceListener;
    private Listener BotOfflineDroppedListener;
    private Listener BotOfflineRequireReconnectListener;

    private Listener GroupMessageListener;
    private Listener FriendMessageListener;
    private Listener GroupTempMessageEventListener;
    private Listener StrangerMessageEventListener;

    private Listener GroupMessagePreSendEventListener;
    private Listener FriendMessagePreSendEventListener;
    private Listener GroupTempMessagePreSendEventListener;
    private Listener StrangerMessagePreSendEventListener;

    private Listener GroupMessagePostSendEventListener;

    public void startListenEvent(){
        // Bot
        BotOnlineListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOnlineEvent.class, event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiBotOnlineEvent(event)));
        BotOfflineActiveListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Active.class,event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiBotOfflineEvent(event, "Active")));
        BotOfflineForceListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Force.class,event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiBotOfflineEvent(event, "Force")));
        BotOfflineDroppedListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.Dropped.class,event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiBotOfflineEvent(event, "Dropped")));
        BotOfflineRequireReconnectListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOfflineEvent.RequireReconnect.class,event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiBotOfflineEvent(event, "RequireReconnect")));

        // 消息
        // - 被动
        GroupMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupMessageEvent(event)));
        FriendMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendMessageEvent(event)));
        GroupTempMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessageEvent.class, event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupTempMessageEvent(event)));
        StrangerMessageEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessageEvent.class, event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiStrangerMessageEvent(event)));
        // - 主动前
        GroupMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessagePreSendEvent.class, event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupMessagePreSendEvent(event)));
        FriendMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessagePreSendEvent.class, event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendMessagePreSendEvent(event)));
        GroupTempMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupTempMessagePreSendEvent.class, event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupTempMessagePreSendEvent(event)));
        StrangerMessagePreSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(StrangerMessagePreSendEvent.class, event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiStrangerMessagePreSendEvent(event)));
        // - 主动后
        GroupMessagePostSendEventListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessagePostSendEvent.class, event -> Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupMessagePostSendEvent(event)));

    }

    public void stopListenEvent(){
        BotOnlineListener.complete();
        BotOfflineActiveListener.complete();
        BotOfflineForceListener.complete();
        BotOfflineDroppedListener.complete();
        BotOfflineRequireReconnectListener.complete();

        GroupMessageListener.complete();
        FriendMessageListener.complete();
        GroupTempMessageEventListener.complete();
        StrangerMessageEventListener.complete();

        GroupMessagePreSendEventListener.complete();
        FriendMessagePreSendEventListener.complete();
        GroupTempMessagePreSendEventListener.complete();
        StrangerMessagePreSendEventListener.complete();

        GroupMessagePostSendEventListener.complete();
    }

}
