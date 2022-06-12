package me.dreamvoid.miraimc.bungee.event;

import me.dreamvoid.miraimc.bungee.event.message.MiraiImageUploadEvent;
import net.mamoe.mirai.event.events.ImageUploadEvent;

/**
 * 图片上传成功
 * @deprecated
 * @see me.dreamvoid.miraimc.bungee.event.message.MiraiImageUploadEvent.Succeed
 */
@Deprecated
public class MiraiImageUploadSucceedEvent extends MiraiImageUploadEvent.Succeed {
    public MiraiImageUploadSucceedEvent(ImageUploadEvent.Succeed event) {
        super(event);
    }
}
