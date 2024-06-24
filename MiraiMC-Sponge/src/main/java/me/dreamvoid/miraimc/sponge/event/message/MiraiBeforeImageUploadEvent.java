package me.dreamvoid.miraimc.sponge.event.message;

import org.jetbrains.annotations.NotNull;
import net.mamoe.mirai.event.events.BeforeImageUploadEvent;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * (Sponge) 消息 - 图片上传前
 */
@SuppressWarnings("unused")
public class MiraiBeforeImageUploadEvent extends AbstractEvent {

    private final Cause cause;

    public MiraiBeforeImageUploadEvent(BeforeImageUploadEvent event, Cause cause) {
        this.event = event;
        this.cause = cause;
    }

    private final BeforeImageUploadEvent event;

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

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
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    @Override
    public String toString() {
        return event.toString();
    }

    @Override
    public @NotNull Cause cause() {
        return cause;
    }
}
