package me.dreamvoid.miraimc.httpapi.response;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * 认证与会话 - 绑定
 */
public class Bind {
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
        return "Bind{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bind bind = (Bind) o;
        return code == bind.code && Objects.equals(msg, bind.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg);
    }
}
