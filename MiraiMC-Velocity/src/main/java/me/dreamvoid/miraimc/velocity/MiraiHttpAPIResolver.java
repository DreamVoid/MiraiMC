package me.dreamvoid.miraimc.velocity;

import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.velocity.event.message.passive.*;

import static me.dreamvoid.miraimc.httpapi.MiraiHttpAPI.Bots;

public class MiraiHttpAPIResolver implements Runnable {
    private final VelocityPlugin plugin;

    public MiraiHttpAPIResolver(VelocityPlugin plugin){
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
                                plugin.getServer().getEventManager().fire(new MiraiFriendMessageEvent(account, data));
                                break;
                            case "GroupMessage":
                                plugin.getServer().getEventManager().fire(new MiraiGroupMessageEvent(account, data));
                                break;
                            case "TempMessage":
                                plugin.getServer().getEventManager().fire(new MiraiGroupTempMessageEvent(account, data));
                                break;
                            case "StrangerMessage":
                                plugin.getServer().getEventManager().fire(new MiraiStrangerMessageEvent(account, data));
                                break;
                            case "OtherClientMessage":
                                plugin.getServer().getEventManager().fire(new MiraiOtherClientMessageEvent(account, data));
                                break;
                        }
                    }

                } else plugin.getLogger().warn("Unable to fetch " + account + "'s message, reason: " + fetchMessage.msg);
            } catch (Exception e) {
                plugin.getLogger().warn("An error occurred while fetching message for " + account + ": " + e);
            }
        }
    }
}
