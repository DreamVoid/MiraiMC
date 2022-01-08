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

    @SerializedName("id")
    public int id;

    @SerializedName("time")
    public int time;

    // 下方通用，并且只能调用一次
    public Message setAtAll() {
        type = "AtAll";
        return this;
    }

    public Message setPlain(String message) {
        this.type = "Plain";
        this.text = message;
        return this;
    }

    public Message setImage(URL url) {
        this.type = "Image";
        this.url = url.toString();
        return this;
    }

    public Message setSource(int messageId, int sendTime) {
        this.id=messageId;
        this.time=sendTime;
        return this;
    }

    // 下方用于接收到消息时
    public long senderId;
    public String senderNickname;
    public String remark;

    public Message setSenderId(long account){
        this.senderId=account;
        return this;
    }
    public Message setSenderNickname(String nickname){
        this.senderNickname =nickname;
        return this;
    }
    public Message setSenderRemark(String remark){
        this.remark=remark;
        return this;
    }
}
