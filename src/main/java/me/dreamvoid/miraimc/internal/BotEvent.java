package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.listener.MiraiBotOnlineEvent;
import me.dreamvoid.miraimc.listener.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.listener.MiraiGroupMessageEvent;

import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import org.bukkit.Bukkit;

public class BotEvent {

    public BotEvent(BukkitPlugin plugin) {
    }

    private Listener GroupMessageListener;
    private Listener FriendMessageListener;
    private Listener BotOnlineListener;

    public void startListenEvent(){
        GroupMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiGroupMessageEvent(event));
        });
        FriendMessageListener = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiFriendMessageEvent(event));
        });
        BotOnlineListener = GlobalEventChannel.INSTANCE.subscribeAlways(BotOnlineEvent.class, event -> {
            Bukkit.getServer().getPluginManager().callEvent(new MiraiBotOnlineEvent(event));
        });
    }

    public void stopListenEvent(){
        GroupMessageListener.complete();
        FriendMessageListener.complete();
    }

}
