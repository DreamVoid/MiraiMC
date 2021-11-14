package me.dreamvoid.miraimc.internal.httpapi.type;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class Message {
    @SerializedName("type")
    public String type;

    @SerializedName("text")
    public String text = null;

    @SerializedName("url")
    public String url = null;

    public static Message getAtAll() {
        Message msg = new Message();
        msg.type = "AtAll";
        return msg;
    }

    /**
     * 多次调用时组合的消息不带换行符，需要自己在消息末尾加上
     * @param message 消息内容
     * @return 消息实例
     */
    public static Message getPlain(String message) {
        Message msg = new Message();
        msg.type = "Plain";
        msg.text = message;
        return msg;
    }

    public static Message getImage(URL url) {
        Message msg = new Message();
        msg.type = "Image";
        msg.url = url.toString();
        return msg;
    }

}
