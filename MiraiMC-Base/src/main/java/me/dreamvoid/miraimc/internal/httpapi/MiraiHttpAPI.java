package me.dreamvoid.miraimc.internal.httpapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.dreamvoid.miraimc.internal.httpapi.response.Verify;

public class MiraiHttpAPI {
    private final String url;

    public MiraiHttpAPI(String url) {
        this.url = url;
    }
    public Verify verify(String verifyKey) {
        JsonObject msgObj = new JsonObject();

        msgObj.addProperty("verifyKey", verifyKey);

        return new Gson().fromJson(HTTPUtils.sendPost(msgObj, url + "/verify"), Verify.class);
    }
}
