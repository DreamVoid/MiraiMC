package me.dreamvoid.miraimc.sponge;

import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.httpapi.response.FetchMessage;
import me.dreamvoid.miraimc.internal.config.PluginConfig;
import me.dreamvoid.miraimc.sponge.event.message.passive.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.plugin.PluginContainer;

import static me.dreamvoid.miraimc.httpapi.MiraiHttpAPI.Bots;

public class MiraiHttpAPIResolver implements Runnable {
    private final SpongePlugin plugin;
    private final EventContext eventContext;
    private final PluginContainer pluginContainer;

    public MiraiHttpAPIResolver(SpongePlugin plugin){
        this.plugin = plugin;
        pluginContainer = plugin.getPluginContainer();
        eventContext = EventContext.builder().add(EventContextKeys.PLUGIN, pluginContainer).build();
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
                        String type = data.type; // 消息类型，用于区分私聊群聊等

                        // 准备广播事件
                        switch (type) {
                            case "FriendMessage":
                                Sponge.getEventManager().post(new MiraiFriendMessageEvent(account, data, Cause.of(eventContext, pluginContainer)));
                                break;
                            case "GroupMessage":
                                Sponge.getEventManager().post(new MiraiGroupMessageEvent(account, data, Cause.of(eventContext, pluginContainer)));
                                break;
                            case "TempMessage":
                                Sponge.getEventManager().post(new MiraiGroupTempMessageEvent(account, data, Cause.of(eventContext, pluginContainer)));
                                break;
                            case "StrangerMessage":
                                Sponge.getEventManager().post(new MiraiStrangerMessageEvent(account, data, Cause.of(eventContext, pluginContainer)));
                                break;
                            case "OtherClientMessage":
                                Sponge.getEventManager().post(new MiraiOtherClientMessageEvent(account, data, Cause.of(eventContext, pluginContainer)));
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
