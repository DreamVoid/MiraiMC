package me.dreamvoid.miraimc.httpapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import me.dreamvoid.miraimc.httpapi.exception.AbnormalStatusException;
import me.dreamvoid.miraimc.httpapi.response.*;
import me.dreamvoid.miraimc.internal.Utils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MiraiHttpAPI {
    public static MiraiHttpAPI INSTANCE;
    private final String url;
    private final Gson gson = new Gson();
    public final static HashMap<Long, String> Bots = new HashMap<>();

    public MiraiHttpAPI(String url) {
        this.url = url;
        INSTANCE = this;
    }

    public Verify verify(String verifyKey) throws IOException, AbnormalStatusException {
        JsonObject json = new JsonObject();
        json.addProperty("verifyKey", verifyKey);
        Verify verify = gson.fromJson(Utils.Http.post(json, url + "/verify"), Verify.class);
        if(verify.code != 0){
            throw new AbnormalStatusException(verify.code, verify.msg);
        }
        return verify;
    }

    public Bind bind(String sessionKey, long qq) throws IOException, AbnormalStatusException {
        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("qq", qq);
        Bind bind = gson.fromJson(Utils.Http.post(json, url + "/bind"), Bind.class);
        if(bind.code == 0) {
            Bots.put(qq, sessionKey);
        } else throw new AbnormalStatusException(bind.code, bind.msg);
        return bind;
    }

    public Release release(String sessionKey, long qq) throws IOException, AbnormalStatusException {
        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("qq", qq);
        Release release = gson.fromJson(Utils.Http.post(json, url + "/release"), Release.class);
        if(release.code == 0) {
            Bots.remove(qq);
        } else throw new AbnormalStatusException(release.code, release.msg);
        return release;
    }

    public SendMessage sendFriendMessage(String sessionKey, long target, String message) throws IOException, AbnormalStatusException {
        FetchMessage.Data.MessageChain chain = new FetchMessage.Data.MessageChain();
        chain.text = message;
        JsonArray messageArray = gson.toJsonTree(Collections.singletonList(chain), new TypeToken<List<FetchMessage.Data.MessageChain>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        SendMessage sendMessage = gson.fromJson(Utils.Http.post(json, url + "/sendFriendMessage"), SendMessage.class);
        if(sendMessage.code != 0){
            throw new AbnormalStatusException(sendMessage.code, sendMessage.msg);
        }
        return sendMessage;
    }

    public SendMessage sendFriendMessage(String sessionKey, long target, FetchMessage.Data.MessageChain[] messageChain) throws IOException, AbnormalStatusException {
        JsonArray messageArray = gson.toJsonTree(messageChain, new TypeToken<List<FetchMessage.Data.MessageChain>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        SendMessage sendMessage = gson.fromJson(Utils.Http.post(json, url + "/sendFriendMessage"), SendMessage.class);
        if(sendMessage.code != 0){
            throw new AbnormalStatusException(sendMessage.code, sendMessage.msg);
        }
        return sendMessage;
    }

    public SendMessage sendGroupMessage(String sessionKey, long target, String message) throws IOException, AbnormalStatusException {
        FetchMessage.Data.MessageChain chain = new FetchMessage.Data.MessageChain();
        chain.text = message;
        JsonArray messageArray = gson.toJsonTree(Collections.singletonList(chain), new TypeToken<List<FetchMessage.Data.MessageChain>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        SendMessage sendMessage = gson.fromJson(Utils.Http.post(json, url + "/sendGroupMessage"), SendMessage.class);
        if(sendMessage.code != 0){
            throw new AbnormalStatusException(sendMessage.code, sendMessage.msg);
        }
        return sendMessage;
    }

    public SendMessage sendGroupMessage(String sessionKey, long target, FetchMessage.Data.MessageChain[] messageChain) throws IOException, AbnormalStatusException {
        JsonArray messageArray = gson.toJsonTree(messageChain, new TypeToken<List<FetchMessage.Data.MessageChain>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        SendMessage sendMessage = gson.fromJson(Utils.Http.post(json, url + "/sendGroupMessage"), SendMessage.class);
        if(sendMessage.code != 0){
            throw new AbnormalStatusException(sendMessage.code, sendMessage.msg);
        }
        return sendMessage;
    }

    public FetchMessage fetchMessage(String sessionKey, int count) throws IOException {
        FetchMessage fetchMessage = gson.fromJson(Utils.Http.get(url + "/fetchMessage?sessionKey=" + sessionKey + "&count=" + count), FetchMessage.class);
        if(fetchMessage.code != 0){
            throw new AbnormalStatusException(fetchMessage.code, fetchMessage.msg);
        }
        return fetchMessage;
    }
}
