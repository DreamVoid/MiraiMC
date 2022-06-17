package me.dreamvoid.miraimc.sponge.event;

import net.mamoe.mirai.event.events.BeforeImageUploadEvent;
import org.spongepowered.api.event.cause.Cause;

/**
 * 图片上传前
 * @deprecated
 * @see me.dreamvoid.miraimc.sponge.event.message.MiraiBeforeImageUploadEvent
 */
@Deprecated
public class MiraiBeforeImageUploadEvent extends me.dreamvoid.miraimc.sponge.event.message.MiraiBeforeImageUploadEvent {
    public MiraiBeforeImageUploadEvent(BeforeImageUploadEvent event, Cause cause) {
        super(event, cause);
    }
}
