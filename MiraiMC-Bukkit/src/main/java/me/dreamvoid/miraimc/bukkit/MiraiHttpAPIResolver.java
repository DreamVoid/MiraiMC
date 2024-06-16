package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.bukkit.event.message.passive.*;
import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import me.dreamvoid.miraimc.internal.config.PluginConfig;
import org.bukkit.Bukkit;

import static me.dreamvoid.miraimc.httpapi.MiraiHttpAPI.Bots;

public class MiraiHttpAPIResolver implements Runnable {
    private final BukkitPlugin plugin;

    public MiraiHttpAPIResolver(BukkitPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        MiraiHttpAPI api = new MiraiHttpAPI(PluginConfig.HttpApi.Url);
        for(long account : Bots.keySet()){
            try {
                String session = Bots.get(account);
                FetchMessage fetchMessage = api.fetchMessage(session, PluginConfig.HttpApi.MessageFetch.Count);
                if(fetchMessage.code == 0) {
                    for(FetchMessage.Data data : fetchMessage.data) { // 这里搞循环是因为mirai http api会返回一堆消息，需要挨个处理
                        String type = data.type; // 消息和事件类型，用于区分私聊群聊等，在这里只需要这一个，其他全部传参到具体的事件

                        // 准备广播事件
                        switch (type) {
                            case "FriendMessage":
                                Bukkit.getPluginManager().callEvent(new MiraiFriendMessageEvent(account, data));
                                break;
                            case "GroupMessage":
                                Bukkit.getPluginManager().callEvent(new MiraiGroupMessageEvent(account, data));
                                break;
                            case "TempMessage":
                                Bukkit.getPluginManager().callEvent(new MiraiGroupTempMessageEvent(account, data));
                                break;
                            case "StrangerMessage":
                                Bukkit.getPluginManager().callEvent(new MiraiStrangerMessageEvent(account, data));
                                break;
                            case "OtherClientMessage":
                                Bukkit.getPluginManager().callEvent(new MiraiOtherClientMessageEvent(account, data));
                                break;
                        }
                    }

                } else plugin.getLogger().warning("Unable to fetch " + account + "'s message, reason: " + fetchMessage.msg);
            } catch (Exception e) {
                plugin.getLogger().warning("An error occurred while fetching message for " + account + ": " + e);
            }
        }
    }
}
