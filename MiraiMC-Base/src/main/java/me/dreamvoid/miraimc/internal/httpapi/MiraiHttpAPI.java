package me.dreamvoid.miraimc.internal.httpapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import me.dreamvoid.miraimc.internal.httpapi.response.*;
import me.dreamvoid.miraimc.internal.httpapi.type.Message;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MiraiHttpAPI {
    private final String url;
    private final Gson gson = new Gson();
    public final static HashMap<Long, String> hashMap = new HashMap<>();

    public MiraiHttpAPI(String url) {
        this.url = url;
    }

    public Verify verify(String verifyKey) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("verifyKey", verifyKey);
        return gson.fromJson(HTTPUtils.sendPost(json, url + "/verify"), Verify.class);
    }

    public Bind bind(String sessionKey, long qq) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("qq", qq);
        Bind bind = gson.fromJson(HTTPUtils.sendPost(json, url + "/bind"), Bind.class);
        if(bind.code == 0) hashMap.put(qq, sessionKey);
        return bind;
    }

    public Release release(String sessionKey, long qq) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("qq", qq);
        Release release = gson.fromJson(HTTPUtils.sendPost(json, url + "/release"), Release.class);
        if(release.code == 0) hashMap.remove(qq);
        return release;
    }

    public SendMessage sendFriendMessage(String sessionKey, long target, String message) throws IOException {
        Message chain = new Message().setPlain(message);
        JsonArray messageArray = gson.toJsonTree(Collections.singletonList(chain), new TypeToken<List<Message>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        return gson.fromJson(HTTPUtils.sendPost(json, url + "/sendFriendMessage"), SendMessage.class);
    }

    public SendMessage sendFriendMessage(String sessionKey, long target, Message[] messageChain) throws IOException {
        JsonArray messageArray = gson.toJsonTree(messageChain, new TypeToken<List<Message>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        return gson.fromJson(HTTPUtils.sendPost(json, url + "/sendFriendMessage"), SendMessage.class);
    }

    public SendMessage sendGroupMessage(String sessionKey, long target, String message) throws IOException {
        Message chain = new Message().setPlain(message);
        JsonArray messageArray = gson.toJsonTree(Collections.singletonList(chain), new TypeToken<List<Message>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        return gson.fromJson(HTTPUtils.sendPost(json, url + "/sendGroupMessage"), SendMessage.class);
    }

    public SendMessage sendGroupMessage(String sessionKey, long target, Message[] messageChain) throws IOException {
        JsonArray messageArray = gson.toJsonTree(messageChain, new TypeToken<List<Message>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        return gson.fromJson(HTTPUtils.sendPost(json, url + "/sendGroupMessage"), SendMessage.class);
    }

    public FetchMessage fetchMessage(String sessionKey, int count) throws Exception {
        return gson.fromJson(HTTPUtils.get(url+"/fetchMessage?sessionKey="+sessionKey+"&count="+count), FetchMessage.class);
    }
}
