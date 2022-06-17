package me.dreamvoid.miraimc.bukkit.event;

import net.mamoe.mirai.event.events.BeforeImageUploadEvent;

/**
 * 图片上传前
 * @deprecated
 * @see me.dreamvoid.miraimc.bukkit.event.message.MiraiBeforeImageUploadEvent
 */
@Deprecated
public class MiraiBeforeImageUploadEvent extends me.dreamvoid.miraimc.bukkit.event.message.MiraiBeforeImageUploadEvent {
    public MiraiBeforeImageUploadEvent(BeforeImageUploadEvent event) {
        super(event);
    }
}
