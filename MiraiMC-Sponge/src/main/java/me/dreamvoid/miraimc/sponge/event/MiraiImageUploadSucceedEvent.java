package me.dreamvoid.miraimc.sponge.event;

import me.dreamvoid.miraimc.sponge.event.message.MiraiImageUploadEvent;
import net.mamoe.mirai.event.events.ImageUploadEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 图片上传成功
 * @deprecated
 * @see MiraiImageUploadEvent.Succeed
 */
@Deprecated
public class MiraiImageUploadSucceedEvent extends MiraiImageUploadEvent.Succeed {
    public MiraiImageUploadSucceedEvent(ImageUploadEvent.Succeed event, Cause cause) {
        super(event, cause);
    }
}
