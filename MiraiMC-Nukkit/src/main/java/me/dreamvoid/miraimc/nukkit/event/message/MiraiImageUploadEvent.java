package me.dreamvoid.miraimc.nukkit.event.message;

import cn.nukkit.event.HandlerList;
import net.mamoe.mirai.event.events.ImageUploadEvent;
import cn.nukkit.event.Event;

/**
 * (Nukkit) 消息 - 图片上传失败
 */
public class MiraiImageUploadEvent extends Event {
    private final ImageUploadEvent event;

    private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlers() { return handlers; }

    public MiraiImageUploadEvent(ImageUploadEvent event) {
        this.event = event;
    }

    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    public long getBotID() { return event.getBot().getId(); }

    /**
     * 获取接收此图片的ID
     * @return 接收者ID
     */
    public long getTargetID() { return event.getTarget().getId(); }

    /**
     * 计算资源ID
     * @return 资源ID
     */
    public String calculateResourceID() { return event.getSource().calculateResourceId(); }

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
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    @Override
    public String toString() {
        return event.toString();
    }

    /**
     * 图片上传成功
     */
    public static class Succeed extends MiraiImageUploadEvent{
        public Succeed(ImageUploadEvent.Succeed event){
            super(event);
            this.event = event;
        }

        final ImageUploadEvent.Succeed event;

        /**
         * 获取图片ID
         * @return 图片ID
         */
        public String getImageID() {
            return event.getImage().getImageId();
        }

        /**
         * 获取用于发送消息的Mirai消息码
         * @return Mirai格式化消息
         */
        public String getMiraiMsg() {
            return event.getImage().serializeToMiraiCode();
        }

        /**
         * 判断图片是否为表情
         * @return 是则返回true，不是返回false
         */
        public boolean isEmoji(){
            return event.getImage().isEmoji();
        }
    }

    /**
     * 图片上传失败
     */
    public static class Failed extends MiraiImageUploadEvent{
        public Failed(ImageUploadEvent.Failed event){
            super(event);
            this.event = event;
        }

        final ImageUploadEvent.Failed event;

        /**
         * (?)获取消息（可能是失败原因）
         * @return 消息内容
         */
        public String getMessage() {
            return event.getMessage();
        }

        /**
         * (?)获取这是第几个失败的请求
         * @return 失败ID
         */
        public int getErrorID() {
            return event.getErrno();
        }
    }
}
