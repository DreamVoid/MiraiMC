package me.dreamvoid.miraimc.nukkit.event;

import cn.nukkit.event.HandlerList;
import net.mamoe.mirai.event.events.ImageUploadEvent;
import cn.nukkit.event.Event;

/**
 * 图片上传成功
 */
public class MiraiImageUploadSucceedEvent extends Event {

    public MiraiImageUploadSucceedEvent(ImageUploadEvent.Succeed event) {
        this.event = event;
    }

    private final ImageUploadEvent.Succeed event;

    private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlers() { return handlers; }
    //public static HandlerList getHandlerList() { return handlers; }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getID() { return event.getBot().getId(); }

    /**
     * 获取接收此图片的ID
     * @return 接收者ID
     */
    public long getTargetID() { return event.getTarget().getId(); }

    /**
     * 获取图片ID
     * @return 图片ID
     */
    public String getImageID() { return event.getImage().getImageId(); }

    /**
     * 获取图片MD5
     * @return 图片MD5
     */
    public byte[] getImageMd5() { return event.getSource().getMd5(); }

    /**
     * 获取图片Sha1
     * @return 图片Sha1
     */
    public byte[] getImageSha1() { return event.getSource().getSha1(); }

    /**
     * 获取图片格式化后的名称
     * @return 图片名称
     */
    public String getImageName() { return event.getSource().getFormatName(); }

    /**
     * 获取图片大小
     * @return 图片大小
     */
    public long getImageSize() { return event.getSource().getSize(); }

    /**
     * 获取用于发送消息的Mirai消息码
     * @return Mirai格式化消息
     */
    public String getMiraiMsg(){return event.getImage().serializeToMiraiCode();}

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    public String eventToString() {
        return event.toString();
    }

    /**
     * 判断图片是否为表情
     * @return 是则返回true，不是返回false
     */
    public boolean isEmoji(){
        return event.getImage().isEmoji();
    }
}
