package me.dreamvoid.miraimc.httpapi.response;

import com.google.gson.annotations.SerializedName;

public class SendMessage {
    @SerializedName("code")
    public int code;

    @SerializedName("msg")
    public String msg;

    @SerializedName("messageId")
    public int messageId;
}
