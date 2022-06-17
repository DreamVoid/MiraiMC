package me.dreamvoid.miraimc.velocity.event;

import net.mamoe.mirai.event.events.BeforeImageUploadEvent;

/**
 * 图片上传前
 * @deprecated
 * @see me.dreamvoid.miraimc.velocity.event.message.MiraiBeforeImageUploadEvent
 */
@Deprecated
public class MiraiBeforeImageUploadEvent extends me.dreamvoid.miraimc.velocity.event.message.MiraiBeforeImageUploadEvent {
    public MiraiBeforeImageUploadEvent(BeforeImageUploadEvent event) {
        super(event);
    }
}
