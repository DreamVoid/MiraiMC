package me.dreamvoid.miraimc.event;

/**
 * 图片上传成功
 */
public interface ImageUploadSucceedEventImpl {
    /**
     * 获取机器人账号
     * @return 机器人账号
     */
    long getBotID();

    /**
     * 获取接收此图片的ID
     * @return 接收者ID
     */
    long getTargetID();

    /**
     * 获取图片ID
     * @return 图片ID
     */
    String getImageID();

    /**
     * 获取图片MD5
     * @return 图片MD5
     */
    byte[] getImageMd5();

    /**
     * 获取图片Sha1
     * @return 图片Sha1
     */
    byte[] getImageSha1();

    /**
     * 获取图片格式化后的名称
     * @return 图片名称
     */
    String getImageName();

    /**
     * 获取图片大小
     * @return 图片大小
     */
    long getImageSize();

    /**
     * 获取用于发送消息的Mirai消息码
     * @return Mirai格式化消息
     */
    String getMiraiMsg();

    /**
     * 获取原始事件内容<br>
     * [!] 不推荐使用
     * @return 原始事件内容
     */
    String eventToString();
}
