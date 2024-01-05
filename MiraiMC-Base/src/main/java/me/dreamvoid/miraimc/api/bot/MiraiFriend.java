package me.dreamvoid.miraimc.api.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;

import javax.annotation.Nullable;
import java.io.File;

/**
 * MiraiMC 好友
 * @author DreamVoid
 */
@SuppressWarnings("unused")
public class MiraiFriend {
    private final Bot bot;
    private final Friend friend;

    /**
     * 获取指定好友的实例
     * @param bot 机器人实例
     * @param friendAccount 好友账号
     * @throws NullPointerException 不存在指定好友时抛出
     */
    public MiraiFriend(Bot bot, long friendAccount) throws NullPointerException{
        this.bot = bot;
        friend = bot.getFriend(friendAccount);
    }

    /**
     * 获取指定好友的实例
     * @param friend 好友
     * @throws NullPointerException 不存在指定好友时抛出
     */
    public MiraiFriend(Bot bot, Friend friend) throws NullPointerException{
        this.bot = bot;
        this.friend = friend;
    }

    /**
     * 向好友发送消息
     * @param message 消息文本
     */
    public void sendMessage(String message){
        friend.sendMessage(message);
    }

    /**
     * 向好友发送消息<br>
     * 此方法将自动转换为Mirai Code，可用于发送图片等特殊消息
     * @param message Mirai Code格式的消息文本
     */
    public void sendMessageMirai(String message){
        friend.sendMessage(MiraiCode.deserializeMiraiCode(message));
    }

    /**
     * 向好友发送戳一戳
     * @deprecated {@link #nudge()}
     */
    @Deprecated
    public void sendNudge(){
        friend.nudge();
    }

    /**
     * 向好友发送戳一戳
     */
    public void nudge(){
        friend.nudge();
    }

    /**
     * 获取好友昵称
     * @return 昵称
     */
    public String getNick(){
        return friend.getNick();
    }

    /**
     * 获取好友备注
     * @return 备注
     */
    public String getRemark(){
        return friend.getRemark();
    }

    /**
     * 删除好友
     * @deprecated {@link #delete()}
     */
    @Deprecated
    public void doDelete(){
        friend.delete();
    }

    /**
     * 删除好友
     */
    public void delete(){
        friend.delete();
    }

    /**
     * 获取好友QQ号
     * @return QQ号
     */
    public long getID(){
        return friend.getId();
    }

    /**
     * 获取好友头像链接
     * @return Url
     */
    public String getAvatarUrl(){
        return friend.getAvatarUrl();
    }

    /**
     * 上传一个图片，返回图片ID用于发送消息
     * @param imageFile 图片文件
     * @return 图片ID
     */
    public String uploadImage(File imageFile) {
        Image i = ExternalResource.uploadAsImage(imageFile, friend);
        return i.getImageId();
    }

    /**
     * 判断图片是否为表情
     * @param imageID 图片ID
     * @return 是则返回true，不是返回false
     */
    public boolean isImageEmoji(String imageID){
        return Image.fromId(imageID).isEmoji();
    }

    /**
     * 发送音乐分享<br>
     * [!]本方法中，Kind参数使用mirai提供的valueOf方法，请确保传递的音乐平台名真实存在，否则请注意使用try捕获异常
     * @param kind 可选种类：QQMusic | MiguMusic | KugouMusic | KuwoMusic | NeteaseCLoudMusic
     * @param title 标题
     * @param summary 内容
     * @param jumpUrl 跳转链接
     * @param pictureUrl 图片链接
     * @param musicUrl 音乐链接
     */
    public void sendMusicShare(String kind, String title, String summary, String jumpUrl, String pictureUrl, String musicUrl){
        friend.sendMessage(new MusicShare(MusicKind.valueOf(kind), title, summary, jumpUrl, pictureUrl, musicUrl));
    }

    /**
     * 发送语音消息
     * @param audio 语音文件
     */
    public void sendAudio(File audio) {
        friend.sendMessage(friend.uploadAudio(ExternalResource.create(audio).toAutoCloseable()));
    }

    /**
     * 发送闪照
     * @param image 图片文件
     */
    public void sendFlashImage(File image) {
        friend.sendMessage(FlashImage.from(friend.uploadImage(ExternalResource.create(image).toAutoCloseable())));
    }

    /**
     * 发送闪照
     * @param imageID 图片ID
     */
    public void sendFlashImage(String imageID) {
        friend.sendMessage(FlashImage.from(imageID));
    }

    /**
     * 获取好友所属分组
     * @return 好友分组
     */
    public MiraiFriendGroup getFriendGroup(){
        return new MiraiFriendGroup(bot, friend.getFriendGroup());
    }

    /**
     * 上传并发送一个短视频
     * @param thumbnailFile 短视频封面图
     * @param videoFile 视频资源，目前仅支持上传 mp4 格式的视频
     * @param fileName 文件名，若为 null 则根据 video 自动生成.
     */
    public void sendShortVideo(File thumbnailFile, File videoFile, @Nullable String fileName){
        ShortVideo shortVideo = friend.uploadShortVideo(ExternalResource.create(thumbnailFile).toAutoCloseable(), ExternalResource.create(videoFile).toAutoCloseable(), fileName);
        friend.sendMessage(shortVideo);
    }
}
