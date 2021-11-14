package me.dreamvoid.miraimc.internal.httpapi.response;

import com.google.gson.annotations.SerializedName;

public class Verify {
    /**
     * 状态码<br>
     * 可在<a href="https://github.com/project-mirai/mirai-api-http/blob/master/docs/api/API.md#%E7%8A%B6%E6%80%81%E7%A0%81">API.md#状态码</a>查看可能出现的值
     */
    @SerializedName("code")
    public int code;

    /**
     * session key<br>
     * 仅当{@link #code}为0时存在，不存在时为null
     */
    @SerializedName("session")
    public String session;

    /**
     * 错误原因<br>
     * 仅当{@link #code}不为0时存在，存在时为null
     */
    @SerializedName("msg")
    public String msg;
}
