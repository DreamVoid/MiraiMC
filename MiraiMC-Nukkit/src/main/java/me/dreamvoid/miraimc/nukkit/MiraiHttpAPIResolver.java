package me.dreamvoid.miraimc.nukkit;

import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.nukkit.event.message.passive.*;

import static me.dreamvoid.miraimc.httpapi.MiraiHttpAPI.Bots;

public class MiraiHttpAPIResolver implements Runnable {
    private final NukkitPlugin plugin;

    public MiraiHttpAPIResolver(NukkitPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        MiraiHttpAPI api = new MiraiHttpAPI(Config.HttpApi.Url);
        for(long account : Bots.keySet()){
            try {
                String session = Bots.get(account);
                FetchMessage fetchMessage = api.fetchMessage(session, Config.HttpApi.MessageFetch.Count);
                if(fetchMessage.code == 0) {
                    for(FetchMessage.Data data : fetchMessage.data) { // 这里搞循环是因为mirai http api会返回一堆消息，需要挨个处理
                        String type = data.type; // 消息类型，用于区分私聊群聊等

                        // 准备广播事件
                        switch (type) {
                            case "FriendMessage":
                                plugin.getServer().getPluginManager().callEvent(new MiraiFriendMessageEvent(account, data));
                                break;
                            case "GroupMessage":
                                plugin.getServer().getPluginManager().callEvent(new MiraiGroupMessageEvent(account, data));
                                break;
                            case "TempMessage":
                                plugin.getServer().getPluginManager().callEvent(new MiraiGroupTempMessageEvent(account, data));
                                break;
                            case "StrangerMessage":
                                plugin.getServer().getPluginManager().callEvent(new MiraiStrangerMessageEvent(account, data));
                                break;
                            case "OtherClientMessage":
                                plugin.getServer().getPluginManager().callEvent(new MiraiOtherClientMessageEvent(account, data));
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
