package me.dreamvoid.miraimc.nukkit;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import me.dreamvoid.miraimc.nukkit.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.nukkit.event.MiraiGroupMessageEvent;

public class Events implements Listener {
    private final NukkitPlugin plugin;

    public Events(NukkitPlugin plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onMiraiGroupMessageReceived(MiraiGroupMessageEvent e){
        plugin.getLogger().info("[GroupMessage/"+e.getBotID()+"] ["+e.getGroupName()+"("+e.getGroupID()+")] "+e.getSenderNameCard()+"("+e.getSenderID()+") -> "+e.getMessage());
    }

    @EventHandler
    public void onMiraiFriendMessageReceived(MiraiFriendMessageEvent e){
        plugin.getLogger().info("[FriendMessage/"+e.getBotID()+"] "+e.getSenderNick()+"("+e.getSenderID()+") -> "+e.getMessage());
    }
}
