package me.dreamvoid.miraimc.httpapi.response;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * 认证与会话 - 释放
 */
public class Release {
    /**
     * 状态码
     */
    @SerializedName("code")
    public int code;

    /**
     * 返回消息
     */
    @SerializedName("msg")
    public String msg;

    @Override
    public String toString() {
        return "Release{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Release release = (Release) o;
        return code == release.code && Objects.equals(msg, release.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg);
    }
}
