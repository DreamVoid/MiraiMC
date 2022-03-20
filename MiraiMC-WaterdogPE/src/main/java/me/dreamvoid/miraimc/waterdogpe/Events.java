package me.dreamvoid.miraimc.waterdogpe;

import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.event.defaults.PlayerChatEvent;
import dev.waterdog.waterdogpe.network.serverinfo.ServerInfo;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.waterdogpe.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.waterdogpe.event.MiraiGroupMessageEvent;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Collection;

public class Events {

    public static void onMiraiGroupMessageReceived(MiraiGroupMessageEvent e) {
        WDPEPlugin.getInstance().getLogger().info("[GroupMessage/" + e.getBotID() + "] [" + e.getGroupName() + "(" + e.getGroupID() + ")] " + e.getSenderNameCard() + "(" + e.getSenderID() + ") -> " + e.getMessage());
    }


    public static void onMiraiFriendMessageReceived(MiraiFriendMessageEvent e) {
        WDPEPlugin.getInstance().getLogger().info("[FriendMessage/" + e.getBotID() + "] " + e.getSenderNick() + "(" + e.getSenderID() + ") -> " + e.getMessage());
    }

}
