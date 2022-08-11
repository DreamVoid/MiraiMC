package me.dreamvoid.miraimc.api.bot;

import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MusicKind;
import net.mamoe.mirai.message.data.MusicShare;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MiraiMC 群
 * @author DreamVoid
 */
public class MiraiGroup {
    private final Group group;

    public MiraiGroup(Bot bot, long groupID) throws NullPointerException {
        group = bot.getGroup(groupID);
    }

    /**
     * 获取指定群成员的实例
     * @param memberAccount 群成员QQ号
     * @return MiraiMC 群成员实例
     */
    public MiraiNormalMember getMember(long memberAccount){
        return new MiraiNormalMember(group, memberAccount);
    }

    /**
     * 向群发送消息
     * @param message 消息内容
     */
    public void sendMessage(String message){
        group.sendMessage(message);
    }

    /**
     * 向群发送消息<br>
     * 此方法将自动转换为Mirai Code，可用于发送图片等特殊消息
     * @param message Mirai Code格式的消息文本
     */
    public void sendMessageMirai(String message){
        group.sendMessage(MiraiCode.deserializeMiraiCode(message));
    }

    /**
     * 判断指定成员是否在群内
     * @param memberAccount 成员QQ号
     * @return 存在返回true
     */
    public boolean contains(long memberAccount){
        return group.contains(memberAccount);
    }

    /**
     * 获取群名称
     * @return 群名
     */
    public String getName(){
        return group.getName();
    }

    /**
     * 设置群名称
     * @param name 群名称
     */
    public void setName(String name){
        group.setName(name);
    }

    /**
     * 退出群
     * @return 执行结果
     */
    public boolean doQuit(){
        return group.quit();
    }

    /**
     * 获取机器人在群内的管理权限
     * @return 0 - 普通成员 | 1 - 管理员 | 2 - 群主
     */
    public int getBotPermission(){
        return group.getBotPermission().getLevel();
    }

    /**
     * 上传一个图片，返回图片ID用于发送消息
     * @param imageFile 图片文件
     * @return 图片ID
     */
    public String uploadImage(File imageFile) {
        Image i = ExternalResource.uploadAsImage(imageFile, group);
        return i.getImageId();
    }

    /**
     * 判断机器人是否被禁言
     * @return 被禁言返回true，未被禁言false
     */
    public boolean isBotMuted(){
        return group.getBotAsMember().isMuted();
    }

    /**
     * 获取机器人禁言剩余时间
     * @return 剩余时间(秒)
     */
    public int getBotMuteTimeRemaining(){
        return group.getBotMuteRemaining();
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
     * 判断群匿名聊天是否开启
     * @return 是则返回true，不是返回false
     */
    public boolean isAnonymousChatEnabled(){
        return group.getSettings().isAnonymousChatEnabled();
    }

    /**
     * 设置群匿名聊天开关<br>
     * 需要机器人拥有管理权限
     * @param enable 是否开启群匿名聊天
     */
    public void isAnonymousChatEnabled(boolean enable){
        group.getSettings().setAnonymousChatEnabled(enable);
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
        group.sendMessage(new MusicShare(MusicKind.valueOf(Kind), Title, Summary, JumpUrl, PictureUrl, MusicUrl));
    }

    /**
     * 获取群成员列表
     * @return 群成员实例数组
     */
    public List<MiraiNormalMember> getMembers(){
        return group.getMembers().stream().map(member -> new MiraiNormalMember(group, member.getId())).collect(Collectors.toList());
    }

    /**
     * 发送语音消息
     * @param audio 语音文件
     */
    public void sendAudio(File audio) {
        group.sendMessage(group.uploadAudio(ExternalResource.create(audio).toAutoCloseable()));
    }
}
