package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.bukkit.event.*;
import me.dreamvoid.miraimc.internal.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Events implements Listener {
    @EventHandler
    public void onMiraiGroupMessageReceived(MiraiGroupMessageEvent e){
        Utils.logger.info("[GroupMessage/"+e.getBotID()+"] ["+e.getGroupName()+"("+e.getGroupID()+")] "+e.getSenderNameCard()+"("+e.getSenderID()+") -> "+e.getMessageToMiraiCode());
    }

    @EventHandler
    public void onMiraiFriendMessageReceived(MiraiFriendMessageEvent e){
        Utils.logger.info("[FriendMessage/"+e.getBotID()+"] "+e.getSenderNick()+"("+e.getSenderID()+") -> "+e.getMessageToMiraiCode());
    }
}
