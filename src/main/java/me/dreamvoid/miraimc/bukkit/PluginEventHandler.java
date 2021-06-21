package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.listener.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.listener.MiraiGroupMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PluginEventHandler implements Listener {
    public PluginEventHandler(){}

    @EventHandler
    public void onMiraiGroupMessageReceived(MiraiGroupMessageEvent e){
        new Utils().getLogger().info("[GroupMessage/"+e.getBotID()+"] ["+e.getGroupName()+"("+e.getGroupID()+")] "+e.getSenderNameCard()+"("+e.getSenderID()+") -> "+e.getMessage());
    }

    @EventHandler
    public void onMiraiFriendMessageReceived(MiraiFriendMessageEvent e){
        new Utils().getLogger().info("[FriendMessage/"+e.getBotID()+"] "+e.getSenderNick()+"("+e.getSenderID()+") -> "+e.getMessage());
    }

}
