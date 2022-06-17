package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.nukkit.event.message.MiraiImageUploadEvent;
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
