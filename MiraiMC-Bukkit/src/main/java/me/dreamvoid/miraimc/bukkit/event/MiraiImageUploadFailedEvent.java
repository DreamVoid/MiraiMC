package me.dreamvoid.miraimc.bukkit.event;

import me.dreamvoid.miraimc.bukkit.event.message.MiraiImageUploadEvent;
import net.mamoe.mirai.event.events.ImageUploadEvent;

/**
 * 图片上传失败
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.message.MiraiImageUploadEvent.Failed
 */
@Deprecated
public class MiraiImageUploadFailedEvent extends MiraiImageUploadEvent.Failed {
    public MiraiImageUploadFailedEvent(ImageUploadEvent.Failed event) {
        super(event);
    }
}
