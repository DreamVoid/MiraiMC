package me.dreamvoid.miraimc.velocity.event;

import me.dreamvoid.miraimc.velocity.event.message.MiraiImageUploadEvent;
import net.mamoe.mirai.event.events.ImageUploadEvent;

/**
 * 图片上传成功
 * @deprecated
 * @see MiraiImageUploadEvent.Succeed
 */
@Deprecated
public class MiraiImageUploadSucceedEvent extends MiraiImageUploadEvent.Succeed {
    public MiraiImageUploadSucceedEvent(ImageUploadEvent.Succeed event) {
        super(event);
    }
}
