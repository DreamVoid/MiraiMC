package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.internal.httpapi.response.FetchMessage;
import me.dreamvoid.miraimc.internal.httpapi.type.Message;
import org.bukkit.Bukkit;

import static me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI.hashMap;

public class MiraiHttpAPIResolver implements Runnable {
    @Override
    public void run() {
        MiraiHttpAPI api = new MiraiHttpAPI(Config.HTTPAPI_Url);
        for(long account : hashMap.keySet()){
            try {
                String session = hashMap.get(account);
                FetchMessage fetchMessage = api.fetchMessage(session, Config.HTTPAPI_MessageFetch_Count);
                if(fetchMessage.code == 0) {
                    for(FetchMessage.Data data : fetchMessage.data) {
                        long id = data.sender.id;
                        String nickname = data.sender.nickname;
                        String remark = data.sender.remark;

                        String type = data.type;

                        int sendTime = 0;
                        int messageId = 0;
                        String message = "";
                        for (Message msg : data.messageChain) {
                            if(msg.type.equalsIgnoreCase("Source")){
                                sendTime = msg.time;
                                messageId = msg.id;
                            } else if(msg.type.equalsIgnoreCase("Plain")){
                                message = message + msg.text;
                            }
                        }


                        if(type.equalsIgnoreCase("FriendMessage")){
                            Bukkit.getPluginManager().callEvent(new MiraiFriendMessageEvent(new Message()
                                    .setSenderId(id)
                                    .setSenderNickname(nickname)
                                    .setSenderRemark(remark)
                                    .setSource(messageId,sendTime)
                                    .setPlain(message)
                            ));
                        }
                    }

                } else Utils.logger.warning("Unable to fetch " + account + "'s message, reason: " + fetchMessage.msg);
            } catch (Exception e) {
                Utils.logger.warning("An error occurred while fetching message for " + account + ": " + e);
            }
        }
    }
}
