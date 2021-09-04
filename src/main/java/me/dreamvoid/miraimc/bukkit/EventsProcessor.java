package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageRecallEvent;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class EventsProcessor implements Listener {
    @EventHandler
    public void onMiraiGroupMessageReceived(MiraiGroupMessageEvent e){
        Utils.logger.info("[GroupMessage/"+e.getBotID()+"] ["+e.getGroupName()+"("+e.getGroupID()+")] "+e.getSenderNameCard()+"("+e.getSenderID()+") -> "+e.getMessage());
    }

    @EventHandler
    public void onMiraiFriendMessageReceived(MiraiFriendMessageEvent e){
        Utils.logger.info("[FriendMessage/"+e.getBotID()+"] "+e.getSenderNick()+"("+e.getSenderID()+") -> "+e.getMessage());
    }

    @EventHandler
    public void onMiraiGroupRecall(MiraiGroupMessageRecallEvent e){
        Utils.logger.info("[Event: GroupRecall/"+e.getBotID()+"] authorId="+e.getSenderID()+", messageIds="+ Arrays.toString(e.getMessageIds()) + ", messageTime="+e.getMessageTime()+", operator="+e.getOperatorID()+", group="+e.getGroupID());
    }
}
