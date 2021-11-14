package me.dreamvoid.miraimc.internal.httpapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.dreamvoid.miraimc.internal.httpapi.response.Bind;
import me.dreamvoid.miraimc.internal.httpapi.response.Release;
import me.dreamvoid.miraimc.internal.httpapi.response.Verify;

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
}
