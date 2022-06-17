package me.dreamvoid.miraimc.sponge;

import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.sponge.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.sponge.event.MiraiGroupMessageEvent;
import org.spongepowered.api.event.Listener;

public class Events {
    @Listener
    public void onMiraiGroupMessageReceived(MiraiGroupMessageEvent e){
        Utils.logger.info("[GroupMessage/"+e.getBotID()+"] ["+e.getGroupName()+"("+e.getGroupID()+")] "+e.getSenderNameCard()+"("+e.getSenderID()+") -> "+e.getMessage());
    }

    @Listener
    public void onMiraiFriendMessageReceived(MiraiFriendMessageEvent e){
        Utils.logger.info("[FriendMessage/"+e.getBotID()+"] "+e.getSenderName()+"("+e.getSenderID()+") -> "+e.getMessage());
    }
}
