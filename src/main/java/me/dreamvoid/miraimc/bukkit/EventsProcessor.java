package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.event.MiraiGroupMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventsProcessor implements Listener {
    @EventHandler
    public void onMiraiGroupMessageReceived(MiraiGroupMessageEvent e){
        Utils.logger.info("[GroupMessage/"+e.getBotID()+"] ["+e.getGroupName()+"("+e.getGroupID()+")] "+e.getSenderNameCard()+"("+e.getSenderID()+") -> "+e.getMessage());
    }

    @EventHandler
    public void onMiraiFriendMessageReceived(MiraiFriendMessageEvent e){
        Utils.logger.info("[FriendMessage/"+e.getBotID()+"] "+e.getSenderNick()+"("+e.getSenderID()+") -> "+e.getMessage());
    }

}
