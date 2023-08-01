package me.dreamvoid.miraimc.api.bot.group;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MusicKind;
import net.mamoe.mirai.message.data.MusicShare;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;

/**
 * MiraiMC 群成员
 * @author DreamVoid
 */
@SuppressWarnings("unused")
public class MiraiNormalMember{
    private final NormalMember member;

    public MiraiNormalMember(Group group,long account) throws NullPointerException{
        member = group.get(account);
    }

    public MiraiNormalMember(NormalMember member) {
        this.member = member;
    }

    /**
     * 获取成员QQ号
     * @return QQ号
     */
    public long getId(){
        return member.getId();
    }

    /**
     * 踢出成员(要求机器人为管理员或群主)
     * @param reason 理由
     * @deprecated {@link #kick(String)}
     */
    @Deprecated
    public void doKick(String reason){
        member.kick(reason);
    }

    /**
     * 踢出成员(要求机器人为管理员或群主)
     * @param reason 理由
     */
    public void kick(String reason){
        member.kick(reason);
    }

    /**
     * 禁言成员(要求机器人为管理员或群主)
     * @param time 时间(秒)
     * @deprecated {@link #mute(int)}
     */
    @Deprecated
    public void setMute(int time){
        member.mute(time);
    }

    /**
     * 解除禁言成员(要求机器人为管理员或群主)
     * @deprecated {@link #unmute()}
     */
    @Deprecated
    public void setUnmute(){
        member.unmute();
    }

    /**
     * 禁言成员(要求机器人为管理员或群主)
     * @param time 时间(秒)
     */
    public void mute(int time){
        member.mute(time);
    }

    /**
     * 解除禁言成员(要求机器人为管理员或群主)
     */
    public void unmute(){
        member.unmute();
    }

    /**
     * 判断是否被禁言
     * @return 被禁言返回true，未被禁言false
     */
    public boolean isMuted(){
        return member.isMuted();
    }

    /**
     * 获取禁言剩余时间
     * @return 剩余时间(秒)
     */
    public int getMuteTimeRemaining(){
        return member.getMuteTimeRemaining();
    }

    /**
     * 发送消息
     * @param message 消息内容
     */
    public void sendMessage(String message){
        member.sendMessage(message);
    }

    /**
     * 发送消息<br>
     * 此方法将自动转换为Mirai Code，可用于发送图片等特殊消息
     * @param message Mirai Code格式的消息文本
     */
    public void sendMessageMirai(String message){
        member.sendMessage(MiraiCode.deserializeMiraiCode(message));
    }

    /**
     * 获取成员昵称
     * @return 昵称
     */
    public String getNick(){
        return member.getNick();
    }

    /**
     * 获取成员备注
     * @return 备注
     */
    public String getRemark(){
        return member.getRemark();
    }

    /**
     * 获取成员特殊头衔内容
     * @return 头衔
     */
    public String getSpecialTitle(){
        return member.getSpecialTitle();
    }

    /**
     * 获取成员群名片
     * @return 群名片
     */
    public String getNameCard(){
        return member.getNameCard();
    }

    /**
     * 获取群员加群时间
     * @return 时间戳
     */
    public int getJoinTimestamp(){
        return member.getJoinTimestamp();
    }

    /**
     * 获取群员最后发言时间
     * @return 时间戳
     */
    public int getLastSpeakTimestamp(){
        return member.getLastSpeakTimestamp();
    }

    /**
     * 设置群员特殊头衔
     * @param specialTitle 头衔内容
     */
    public void setSpecialTitle(String specialTitle){
        member.setSpecialTitle(specialTitle);
    }

    /**
     * 设置群员名片
     * @param nameCard 名片内容
     */
    public void setNameCard(String nameCard){
        member.setNameCard(nameCard);
    }

    /**
     * 设置成员管理权限
     * @param setAdmin 为true设置为管理员，为false取消管理员
     */
    public void modifyAdmin(boolean setAdmin){
        member.modifyAdmin(setAdmin);
    }

    /**
     * 获取成员管理权限
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getPermission(){
        return member.getPermission().getLevel();
    }

    /**
     * 获取成员头像链接
     * @return Url
     */
    public String getAvatarUrl(){
        return member.getAvatarUrl();
    }

    /**
     * 上传一个图片，返回图片ID用于发送消息
     * @param imageFile 图片文件
     * @return 图片ID
     */
    public String uploadImage(File imageFile) {
        Image i = ExternalResource.uploadAsImage(imageFile, member);
        return i.getImageId();
    }

    /**
     * 判断图片是否为表情
     * @param ImageID 图片ID
     * @return 是则返回true，不是返回false
     */
    public boolean isImageEmoji(String ImageID){
        return Image.fromId(ImageID).isEmoji();
    }

    /**
     * 发送音乐分享<br>
     * [!]本方法中，Kind参数使用mirai提供的valueOf方法，请确保传递的音乐平台名真实存在，否则请注意使用try捕获异常
     * @param Kind 可选种类：QQMusic | MiguMusic | KugouMusic | KuwoMusic | NeteaseCLoudMusic
     * @param Title 标题
     * @param Summary 内容
     * @param JumpUrl 跳转链接
     * @param PictureUrl 图片链接
     * @param MusicUrl 音乐链接
     */
    public void sendMusicShare(String Kind, String Title, String Summary, String JumpUrl, String PictureUrl, String MusicUrl){
        member.sendMessage(new MusicShare(MusicKind.valueOf(Kind), Title, Summary, JumpUrl, PictureUrl, MusicUrl));
    }

    /**
     * 发送闪照
     * @param image 图片文件
     */
    public void sendFlashImage(File image) {
        member.sendMessage(FlashImage.from(member.uploadImage(ExternalResource.create(image).toAutoCloseable())));
    }

    /**
     * 发送闪照
     * @param imageID 图片ID
     */
    public void sendFlashImage(String imageID) {
        member.sendMessage(FlashImage.from(imageID));
    }

    /**
     * 获取群活跃度相关属性.
     * @return 群活跃度相关属性
     */
    public MiraiMemberActive getActive(){
        return new MiraiMemberActive(member.getActive());
    }
}
