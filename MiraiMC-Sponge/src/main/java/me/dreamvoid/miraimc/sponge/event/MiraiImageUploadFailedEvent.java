package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.ImageUploadEvent;
import org.jetbrains.annotations.NotNull;

/**
 * 图片上传失败
 */
public class MiraiImageUploadFailedEvent extends AbstractEvent {
    private final Cause cause;

    public MiraiImageUploadFailedEvent(ImageUploadEvent.Failed event, Cause cause) {
        this.event = event;
        this.cause = cause;
    }

    private final ImageUploadEvent.Failed event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getID() { return event.getBot().getId(); }

    /**
     * 获取接收此图片的ID
     * @return 接收者ID
     */
    public long getTargetID() { return event.getTarget().getId(); }

    /**
     * 获取图片ID
     * @return 图片ID
     */
    public String getImageID() { return event.getSource().calculateResourceId(); }

    /**
     * 获取图片MD5
     * @return 图片MD5
     */
    public byte[] getImageMd5() { return event.getSource().getMd5(); }

    /**
     * 获取图片Sha1
     * @return 图片Sha1
     */
    public byte[] getImageSha1() { return event.getSource().getSha1(); }

    /**
     * 获取图片格式化后的名称
     * @return 图片名称
     */
    public String getImageName() { return event.getSource().getFormatName(); }

    /**
     * 获取图片大小
     * @return 图片大小
     */
    public long getImageSize() { return event.getSource().getSize(); }

    /**
     * (?)获取消息（可能是失败原因）
     * @return 消息内容
     */
    public String getMessage() { return event.getMessage();}

    /**
     * (?)获取这是第几个失败的请求
     * @return 失败ID
     */
    public int getErrorID(){return event.getErrno();}

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    @Override
    public @NotNull Cause getCause() {
        return cause;
    }
}
