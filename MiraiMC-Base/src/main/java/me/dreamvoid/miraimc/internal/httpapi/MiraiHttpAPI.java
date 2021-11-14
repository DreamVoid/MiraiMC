package me.dreamvoid.miraimc.internal.httpapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import me.dreamvoid.miraimc.internal.httpapi.response.Bind;
import me.dreamvoid.miraimc.internal.httpapi.response.Release;
import me.dreamvoid.miraimc.internal.httpapi.response.SendMessage;
import me.dreamvoid.miraimc.internal.httpapi.response.Verify;
import me.dreamvoid.miraimc.internal.httpapi.type.Message;

import java.util.Collections;
import java.util.List;

public class MiraiHttpAPI {
    private final String url;

    public MiraiHttpAPI(String url) {
        this.url = url;
    }

    public Verify verify(String verifyKey) {
        JsonObject json = new JsonObject();
        json.addProperty("verifyKey", verifyKey);
        return new Gson().fromJson(HTTPUtils.sendPost(json, url + "/verify"), Verify.class);
    }

    public Bind bind(String sessionKey, long qq) {
        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("qq", qq);
        return new Gson().fromJson(HTTPUtils.sendPost(json, url + "/bind"), Bind.class);
    }

    public Release release(String sessionKey, long qq) {
        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("qq", qq);
        return new Gson().fromJson(HTTPUtils.sendPost(json, url + "/release"), Release.class);
    }

    public SendMessage sendFriendMessage(String sessionKey, long target, String message) {
        Message chain = Message.getPlain(message);
        JsonArray messageArray = new Gson().toJsonTree(Collections.singletonList(chain), new TypeToken<List<Message>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        return new Gson().fromJson(HTTPUtils.sendPost(json, url + "/sendFriendMessage"), SendMessage.class);
    }

    public SendMessage sendFriendMessage(String sessionKey, long target, Message[] messageChain) {
        JsonArray messageArray = new Gson().toJsonTree(messageChain, new TypeToken<List<Message>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        return new Gson().fromJson(HTTPUtils.sendPost(json, url + "/sendFriendMessage"), SendMessage.class);
    }

    public SendMessage sendGroupMessage(String sessionKey, long target, String message) {
        Message chain = Message.getPlain(message);
        JsonArray messageArray = new Gson().toJsonTree(Collections.singletonList(chain), new TypeToken<List<Message>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        return new Gson().fromJson(HTTPUtils.sendPost(json, url + "/sendGroupMessage"), SendMessage.class);
    }

    public SendMessage sendGroupMessage(String sessionKey, long target, Message[] messageChain) {
        JsonArray messageArray = new Gson().toJsonTree(messageChain, new TypeToken<List<Message>>(){}.getType()).getAsJsonArray();

        JsonObject json = new JsonObject();
        json.addProperty("sessionKey", sessionKey);
        json.addProperty("target", target);
        //json.addProperty("quote", quote); 如果之后的版本搞引用回复，这个加到参数部分
        json.add("messageChain", messageArray);
        return new Gson().fromJson(HTTPUtils.sendPost(json, url + "/sendGroupMessage"), SendMessage.class);
    }
}
