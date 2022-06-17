package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.message.MiraiImageUploadEvent;
import net.mamoe.mirai.event.events.ImageUploadEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 图片上传失败
 * @deprecated
 * @see MiraiImageUploadEvent.Failed
 */
@Deprecated
public class MiraiImageUploadFailedEvent extends MiraiImageUploadEvent.Failed {
    public MiraiImageUploadFailedEvent(ImageUploadEvent.Failed event, Cause cause) {
        super(event, cause);
    }
}
