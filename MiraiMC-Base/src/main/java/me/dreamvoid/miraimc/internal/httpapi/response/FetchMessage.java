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
        // 通用
        @SerializedName("id")
        public long id;

        // 好友
        @SerializedName("remark")
        public String remark;
        @SerializedName("nickname")
        public String nickname;

        // 群
        @SerializedName("memberName")
        public String memberName;
        @SerializedName("specialTitle")
        public String specialTitle;
        @SerializedName("permission")
        public String permission;
        @SerializedName("joinTimestamp")
        public long joinTimestamp;
        @SerializedName("lastSpeakTimestamp")
        public long lastSpeakTimestamp;
        @SerializedName("muteTimeRemaining")
        public long muteTimeRemaining;
        @SerializedName("group")
        public Group group;
    }

    public static class Group {
        @SerializedName("id")
        public long id;
        @SerializedName("name")
        public String name;
        @SerializedName("permission")
        public String permission;
    }
}
