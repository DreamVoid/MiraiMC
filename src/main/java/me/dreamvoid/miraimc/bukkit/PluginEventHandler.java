package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.listener.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.listener.MiraiGroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PluginEventHandler implements Listener {
    public PluginEventHandler(){}

    @EventHandler
    public void onMiraiGroupMessageReceived(MiraiGroupMessageEvent e){
        Bukkit.getLogger().info("[GroupMessage/"+e.getBotID()+"] ["+e.getGroupName()+"("+e.getGroupID()+")] "+e.getSenderNameCard()+"("+e.getSenderID()+") -> "+e.getMessage());
    }

    @EventHandler
    public void onMiraiFriendMessageReceived(MiraiFriendMessageEvent e){
        Bukkit.getLogger().info("[FriendMessage/"+e.getBotID()+"] "+e.getSenderNick()+"("+e.getSenderID()+") -> "+e.getMessage());
    }

}
