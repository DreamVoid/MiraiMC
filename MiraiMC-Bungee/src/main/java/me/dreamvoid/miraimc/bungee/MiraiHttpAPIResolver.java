package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.bungee.event.message.passive.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.bungee.event.message.passive.MiraiGroupMessageEvent;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.httpapi.response.FetchMessage;

import static me.dreamvoid.miraimc.httpapi.MiraiHttpAPI.Bots;

public class MiraiHttpAPIResolver implements Runnable {
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
                            case "FriendMessage": {
                                BungeePlugin.INSTANCE.getProxy().getPluginManager().callEvent(new MiraiFriendMessageEvent(account, data));
                                break;
                            }
                            case "GroupMessage": {
                                BungeePlugin.INSTANCE.getProxy().getPluginManager().callEvent(new MiraiGroupMessageEvent(account, data));
                                break;
                            }
                            default:break;
                        }
                    }

                } else Utils.logger.warning("Unable to fetch " + account + "'s message, reason: " + fetchMessage.msg);
            } catch (Exception e) {
                Utils.logger.warning("An error occurred while fetching message for " + account + ": " + e);
            }
        }
    }
}
