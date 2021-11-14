package me.dreamvoid.miraimc.internal.httpapi.response;

import com.google.gson.annotations.SerializedName;
import me.dreamvoid.miraimc.internal.httpapi.type.Message;

import java.util.List;

public class FetchMessage {
    @SerializedName("code")
    public int code;

    @SerializedName("msg")
    public String msg;

    @SerializedName("data")
    public List<Data> data;

    public static class Data {
        @SerializedName("type")
        public String type;
        @SerializedName("messageChain")
        public List<Message> messageChain;
        @SerializedName("sender")
        public Sender sender;
    }

    public static class Sender {
        @SerializedName("id")
        public long id;
        @SerializedName("nickname")
        public String nickname;
        @SerializedName("remark")
        public String remark;
    }
}
