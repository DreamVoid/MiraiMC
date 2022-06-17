package me.dreamvoid.miraimc.nukkit.event;

import me.dreamvoid.miraimc.nukkit.event.message.MiraiImageUploadEvent;
import net.mamoe.mirai.event.events.ImageUploadEvent;

/**
 * 图片上传失败
 * @deprecated
 * @see MiraiImageUploadEvent.Failed
 */
@Deprecated
public class MiraiImageUploadFailedEvent extends MiraiImageUploadEvent.Failed {
    public MiraiImageUploadFailedEvent(ImageUploadEvent.Failed event) {
        super(event);
    }
}
