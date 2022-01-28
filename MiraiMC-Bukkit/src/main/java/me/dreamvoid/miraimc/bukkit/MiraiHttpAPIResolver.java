package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.internal.httpapi.response.FetchMessage;
import me.dreamvoid.miraimc.internal.httpapi.type.Message;
import org.bukkit.Bukkit;

import static me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI.Bots;

public class MiraiHttpAPIResolver implements Runnable {
    @Override
    public void run() {
        MiraiHttpAPI api = new MiraiHttpAPI(Config.HTTPAPI_Url);
        for(long account : Bots.keySet()){
            try {
                String session = Bots.get(account);
                FetchMessage fetchMessage = api.fetchMessage(session, Config.HTTPAPI_MessageFetch_Count);
                if(fetchMessage.code == 0) {
                    for(FetchMessage.Data data : fetchMessage.data) { // 这里搞循环是因为mirai http api会返回一堆消息，需要挨个处理
                        long id = data.sender.id; // 发送者QQ
                        String nickname = data.sender.nickname; // 发送者昵称
                        String remark = data.sender.remark; // 发送者备注

                        String type = data.type; // 消息类型，用于区分私聊群聊等

                        int sendTime = 0; // 发送时间
                        int messageId = 0; // 消息ID

                        StringBuilder message = new StringBuilder(); // 消息内容
                        for (Message msg : data.messageChain) { // 这里搞循环是用来组合消息链
                            switch (msg.type) {
                                case "Source": { // 消息源
                                    sendTime = msg.time;
                                    messageId = msg.id;
                                    break;
                                }
                                case "Plain": { // 普通文本消息
                                    message.append(msg.text);
                                    break;
                                }
                                default:break;
                            }
                        }

                        // 准备广播事件
                        switch (type) {
                            case "FriendMessage": {
                                Bukkit.getPluginManager().callEvent(new MiraiFriendMessageEvent(account, new Message()
                                        .setSenderId(id)
                                        .setSenderNickname(nickname)
                                        .setSenderRemark(remark)
                                        .setSource(messageId, sendTime)
                                        .setPlain(message.toString())
                                ));
                                break;
                            }
                            case "GroupMessage": {
                                Bukkit.getPluginManager().callEvent(new MiraiGroupMessageEvent(account, data.sender, new Message()
                                        .setSenderId(id)
                                        .setSenderNickname(nickname)
                                        .setSenderRemark(remark)
                                        .setSource(messageId, sendTime)
                                        .setPlain(message.toString())
                                ));
                                break;
                            }
                            default:{
                                break;
                            }
                        }
                    }

                } else Utils.logger.warning("Unable to fetch " + account + "'s message, reason: " + fetchMessage.msg);
            } catch (Exception e) {
                Utils.logger.warning("An error occurred while fetching message for " + account + ": " + e);
            }
        }
    }
}
