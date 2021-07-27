package me.dreamvoid.miraimc.api;

import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.LoggerAdapters;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Mirai Core 核心接口
 * @author DreamVoid
 */
public class MiraiBot {
    private final Bot bot;
    private static Logger logger;
    /**
     * MiraiBot 实例
     * [!] 即将转为private
     * @deprecated
     */
    @Deprecated
    public static MiraiBot Instance;

    /**
     * 初始化
     * @param BotAccount 机器人账号
     * @throws NoSuchElementException 不存在机器人抛出
     */
    private MiraiBot(long BotAccount) throws NoSuchElementException {
        Instance = this;
        logger = Utils.logger;
        bot = Bot.getInstance(BotAccount);
    }

    /**
     * 获取指定机器人的实例
     * @param BotAccount 机器人账号
     * @return MiraiMC 机器人实例
     * @throws NoSuchElementException 不存在时抛出
     */
    public static MiraiBot getBot(long BotAccount) throws NoSuchElementException{
        return new MiraiBot(BotAccount);
    }

    /**
     * 获取所有在线的机器人
     * @return 机器人账号列表
     */
    public static List<Long> getOnlineBots(){
        List<Long> BotList = new ArrayList<>();
        for (Bot bot : Bot.getInstances()) {
            BotList.add(bot.getId());
        }
        return BotList;
    }

    /**
     * 获取机器人指定好友的实例
     * @param FriendAccount 好友QQ号
     * @return MiraiMC 好友实例
     */
    public MiraiFriend getFriend(long FriendAccount){
        return new MiraiFriend(bot, FriendAccount);
    }
    /**
     * 获取机器人指定群的实例
     * @param GroupID 群号
     * @return MiraiMC 群实例
     */
    public MiraiGroup getGroup(long GroupID){
        return new MiraiGroup(bot, GroupID);
    }

    /**
     * 登录一个机器人账号
     * [!] 尚未完善多机器人管理。目前，不建议插件开发者在发行版插件中调用此接口
     * @param Account 机器人账号
     * @param Password 机器人密码
     * @param Protocol 协议类型
     */
    public static void doBotLogin(long Account, String Password, BotConfiguration.MiraiProtocol Protocol) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(Password.getBytes(StandardCharsets.UTF_8));
            byte[] md5 = m.digest();
            privateBotLogin(Account, md5, Protocol);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录一个机器人账号
     * [!] 尚未完善多机器人管理。目前，不建议插件开发者在发行版插件中调用此接口
     * @param Account 机器人账号
     * @param PasswordMD5 机器人密码MD5
     * @param Protocol 协议类型
     */
    public static void doBotLogin(long Account, byte[] PasswordMD5, BotConfiguration.MiraiProtocol Protocol) {
        privateBotLogin(Account, PasswordMD5, Protocol);
    }

    /**
     * 尝试设置为在线状态
     */
    public void doOnline(){
        bot.join();
    }

    /**
     * 登出一个机器人账号
     */
    public void doLogout() {
        bot.close();
    }

    /**
     * 判断机器人是否在线
     * @return 在线返回true，离线返回false
     */
    public boolean isOnline(){
        return bot.isOnline();
    }

    /**
     * 判断机器人是否存在
     * @return 存在返回true，不存在返回false
     */
    public boolean isExist() { return !(Objects.equals(bot, null)); }

    private static void privateBotLogin(long Account, byte[] Password, BotConfiguration.MiraiProtocol Protocol){
        logger = Utils.logger;
        logger.info("登录新的机器人账号: "+ Account+", 协议: "+ Protocol.name());

        // 建立mirai数据文件夹
        File MiraiDir;
        if(!(Config.Gen_MiraiWorkingDir.equals("default"))){
            MiraiDir = new File(Config.Gen_MiraiWorkingDir);
        } else {
            MiraiDir = new File(String.valueOf(Config.PluginDir),"MiraiBot");
        }
        if(!(MiraiDir.exists())){ if(!(MiraiDir.mkdir())) { logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立机器人账号文件夹
        File BotDir = new File(String.valueOf(MiraiDir),"bots");
        if(!(BotDir.exists())){ if(!(BotDir.mkdir())) { logger.warning("Unable to create folder: \"" + BotDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立当前机器人账号配置文件夹和相应的配置
        File BotConfig = new File(String.valueOf(BotDir), String.valueOf(Account));
        if(!(BotConfig.exists())){ if(!(BotConfig.mkdir())) { logger.warning("Unable to create folder: \"" + BotConfig.getPath()+"\", make sure you have enough permission."); } }

        // 登录前的准备工作
        Bot bot = BotFactory.INSTANCE.newBot(Account, Password, new BotConfiguration(){{
            // 设置登录信息
            setProtocol(Protocol); // 目前不打算让用户使用其他两个协议
            setWorkingDir(BotConfig);
            fileBasedDeviceInfo();

            // 是否关闭日志输出（不建议开发者关闭）。如果不关闭，是否使用Bukkit的Logger接管Mirai的Logger
            if(Config.Bot_DisableNetworkLogs) {
                noNetworkLog();
            } else if(Config.Bot_UseBukkitLogger_NetworkLogs) {
                setNetworkLoggerSupplier(bot -> LoggerAdapters.asMiraiLogger(logger));
            }
            if(Config.Bot_DisableBotLogs) {
                noBotLog();
            } else if(Config.Bot_UseBukkitLogger_BotLogs) {
                setBotLoggerSupplier(bot -> LoggerAdapters.asMiraiLogger(logger));
            }

            // 是否使用缓存——对于开发者，请启用；对于用户，请禁用。详见 https://github.com/mamoe/mirai/blob/dev/docs/Bots.md#%E5%90%AF%E7%94%A8%E5%88%97%E8%A1%A8%E7%BC%93%E5%AD%98
            getContactListCache().setFriendListCacheEnabled(Config.Bot_ContactCache_EnableFriendListCache);
            getContactListCache().setGroupMemberListCacheEnabled(Config.Bot_ContactCache_EnableGroupMemberListCache);
            getContactListCache().setSaveIntervalMillis(Config.Bot_ContactCache_SaveIntervalMillis);

            // 使用自己的验证解决器
            setLoginSolver(new MiraiLoginSolver());
        }});

        // 开始登录
        bot.login();
        logger.info(bot.getNick()+"("+bot.getId()+") 登录成功");
    }

    // 弃用的方法
    @Deprecated
    public MiraiBot() {
        this.logger = Utils.logger;
        Instance = this;
        this.bot = null;
    }
    /**
     * 登出一个机器人账号
     * [!] 尚未完善多机器人管理。目前，不建议插件开发者在发行版插件中调用此接口
     * @param Account 机器人账号
     * @deprecated
     */
    @Deprecated
    public void doBotLogout(long Account) {
        Bot bot = Bot.getInstanceOrNull(Account);
        if(bot != null){
            bot.close();
        }
    }
    /**
     * 登出一个机器人账号
     * [!] 尚未完善多机器人管理。目前，不建议插件开发者在发行版插件中调用此接口
     * @param Bot 机器人
     * @deprecated
     */
    @Deprecated
    public void doBotLogout(Bot Bot) {
        if(Bot != null){
            Bot.close();
        }
    }
    /**
     * 向指定好友发送消息
     * @param BotAccount 机器人账号
     * @param FriendID 好友QQ号
     * @param Message 消息内容
     * @return 成功返回true，失败返回false (此方法若返回false，则指定的机器人账号不存在)
     * @see MiraiFriend
     * @deprecated
     */
    @Deprecated
    public boolean sendFriendMessage(long BotAccount, long FriendID, String Message){
        if(isBotOnline(BotAccount)) {
            Bot bot = Bot.getInstance(BotAccount);
            bot.getLogger().verbose("[MessageSend/"+BotAccount+"] "+ "Friend("+FriendID + ") <- "+Message);
            bot.getFriendOrFail(FriendID).sendMessage(Message);
            return true;
        } else {
            logger.warning("Bot account \""+BotAccount+"\" doesn't exist!");
            return false;
        }
    }
    /**
     * 向指定好友发送消息
     * @param BotAccount 机器人账号
     * @param FriendID 好友QQ号
     * @param MessageChain 消息链内容
     * @return 成功返回true，失败返回false (此方法若返回false，则指定的机器人账号不存在)
     * @see MiraiFriend
     * @deprecated
     */
    @Deprecated
    public boolean sendFriendMessage(long BotAccount, long FriendID, MessageChain MessageChain){
        if(isBotOnline(BotAccount)) {
            Bot bot = Bot.getInstance(BotAccount);
            bot.getLogger().verbose("[MessageSend/"+BotAccount+"] "+ "Friend("+FriendID +") <- "+MessageChain.serializeToMiraiCode());
            bot.getFriendOrFail(FriendID).sendMessage(MessageChain);
            return true;
        } else {
            logger.warning("Bot account \""+BotAccount+"\" doesn't exist!");
            return false;
        }
    }
    /**
     * 获取指定好友的昵称
     * 如果好友不存在，则返回空文本
     * @param BotAccount 机器人账号
     * @param Friend 好友QQ
     * @return 好友昵称
     * @see MiraiFriend
     * @deprecated
     */
    @Deprecated
    public String getFriendNick(long BotAccount, long Friend){
        if(isBotOnline(BotAccount)){
            Bot bot = Bot.getInstance(BotAccount);
            return bot.getFriendOrFail(Friend).getNick();
        } else return "";
    }
    /**
     * 获取指定好友的备注
     * 如果好友不存在或没有好友备注，则返回空文本
     * @param BotAccount 机器人账号
     * @param Friend 好友QQ
     * @return 好友备注
     * @see MiraiFriend
     * @deprecated
     */
    @Deprecated
    public String getFriendRemark(long BotAccount, long Friend){
        if(isBotOnline(BotAccount)){
            Bot bot = Bot.getInstance(BotAccount);
            return bot.getFriendOrFail(Friend).getRemark();
        } else return "";
    }
    /**
     * 向指定好友发送戳一戳
     * @param BotAccount 机器人账号
     * @param FriendID 好友QQ号
     * @return 成功返回true，失败返回false
     * @see MiraiFriend
     * @deprecated
     */
    @Deprecated
    public boolean sendFriendNudge(long BotAccount, long FriendID){
        if(isBotOnline(BotAccount)){
            Bot bot = Bot.getInstance(BotAccount);
            bot.getLogger().verbose("[NudgeSend/"+BotAccount+"] "+ "Friend("+FriendID + ") <- ");
            return(bot.nudge().sendTo(bot.getFriendOrFail(FriendID)));
        } else {
            logger.warning("Bot account \""+BotAccount+"\" doesn't exist!");
            return false;
        }
    }
    /**
     * 向指定群发送戳一戳
     * (!) 未经测试
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @return 成功返回true，失败返回false
     * @deprecated
     */
    @Deprecated
    public boolean sendGroupNudge(long BotAccount, long GroupID){
        if(isBotExist(BotAccount)) {
            Bot bot = Bot.getInstanceOrNull(BotAccount);
            assert bot != null;
            bot.getLogger().verbose("[NudgeSend/" + BotAccount + "] " + "Group("+GroupID + ") <- ");
            return (bot.nudge().sendTo(bot.getGroupOrFail(GroupID)));
        } else {
            logger.warning("Bot account \""+BotAccount+"\" doesn't exist!");
            return false;
        }
    }
    /**
     * 判断机器人是否存在
     * @param Account 机器人账号
     * @return 存在返回true，不存在返回false
     * @deprecated
     */
    @Deprecated
    public boolean isBotExist(long Account){ return !(Objects.equals(Bot.getInstanceOrNull(Account), null)); }
    /**
     * 判断机器人是否存在
     * @param bot 机器人
     * @return 存在返回true，不存在返回false
     * @deprecated
     */
    @Deprecated
    public boolean isBotExist(Bot bot) { return !(Objects.equals(bot, null)); }
    /**
     * 判断机器人是否在线
     * @param Account 机器人账号
     * @return 在线返回true，离线返回false
     * @deprecated
     */
    @Deprecated
    public boolean isBotOnline(long Account){
        Bot bot=Bot.getInstanceOrNull(Account);
        if(isBotExist(bot)){
            assert bot != null;
            return bot.isOnline();
        } else {
            logger.warning("Bot account \""+Account+"\" doesn't exist!");
            return false;
        }
    }
    /**
     * 向指定群发送消息
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param Message 消息内容
     * @return 成功返回true，失败返回false (此方法若返回false，则指定的机器人账号不存在)
     * @deprecated
     */
    @Deprecated
    public boolean sendGroupMessage(long BotAccount, long GroupID, String Message){
        if(isBotOnline(BotAccount)){
            Bot bot = Bot.getInstance(BotAccount);
            bot.getLogger().verbose("[MessageSend/"+BotAccount+"] "+ "Group("+GroupID +") <- "+Message);
            bot.getGroupOrFail(GroupID).sendMessage(Message);
            return true;
        } else {
            logger.warning("Bot account \""+BotAccount+"\" doesn't exist!");
            return false;
        }
    }
    /**
     * 向指定群发送消息
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param MessageChain 消息链内容
     * @return 成功返回true，失败返回false (此方法若返回false，则指定的机器人账号不存在)
     * @deprecated
     */
    @Deprecated
    public boolean sendGroupMessage(long BotAccount, long GroupID, MessageChain MessageChain){
        if(isBotOnline(BotAccount)){
            Bot bot = Bot.getInstance(BotAccount);
            bot.getLogger().verbose("[MessageSend/"+BotAccount+"] "+ "Group("+GroupID +") <- "+MessageChain.contentToString());
            bot.getGroupOrFail(GroupID).sendMessage(MessageChain);
            return true;
        } else {
            logger.warning("Bot account \""+BotAccount+"\" doesn't exist!");
            return false;
        }
    }
    /**
     * 在指定群禁言指定成员(要求机器人为管理员或群主)
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param TargetID 被操作群员QQ号
     * @param Time 时间(秒)
     * @return 成功返回true，失败返回false (此方法若返回false，则指定的机器人账号不存在)
     * @see MiraiNormalMember
     * @deprecated
     */
    @Deprecated
    public boolean setGroupMemberMute(long BotAccount, long GroupID, long TargetID, int Time){
        if(isBotOnline(BotAccount)){
            Bot bot = Bot.getInstance(BotAccount);
            Group group = bot.getGroupOrFail(GroupID);
            bot.getLogger().verbose("[GroupMute/"+BotAccount+"] " +"Group("+GroupID+") Target(" + TargetID + ")"+" <- Mute ("+Time+"s)");
            group.getOrFail(TargetID).mute(Time);
            return true;
        } else {
            logger.warning("Bot account \""+BotAccount+"\" doesn't exist!");
            return false;
        }
    }
    /**
     * 在指定群解除禁言指定成员(要求机器人为管理员或群主)
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param TargetID 被操作群员QQ号
     * @return 成功返回true，失败返回false (此方法若返回false，则指定的机器人账号不存在)
     * @see MiraiNormalMember
     * @deprecated
     */
    @Deprecated
    public boolean setGroupMemberUnmute(long BotAccount, long GroupID, long TargetID){
        if(isBotOnline(BotAccount)){
            Bot bot = Bot.getInstance(BotAccount);
            bot.getLogger().verbose("[GroupMute/"+BotAccount+"] " +"Group("+GroupID+") Target(" + TargetID + ")"+" <- Unmute");
            bot.getGroupOrFail(GroupID).getOrFail(TargetID).unmute();
            return true;
        } else {
            logger.warning("Bot account \""+BotAccount+"\" doesn't exist!");
            return false;
        }
    }
    /**
     * 踢出指定群的指定成员(要求机器人为管理员或群主)
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param TargetID 被操作群员QQ号
     * @param Reason 理由
     * @return 成功返回true，失败返回false (此方法若返回false，则指定的机器人账号不存在)
     * @see MiraiNormalMember
     * @deprecated
     */
    @Deprecated
    public boolean setGroupMemberKick(long BotAccount, long GroupID, long TargetID, String Reason){
        if(isBotOnline(BotAccount)){
            Bot bot = Bot.getInstance(BotAccount);
            bot.getLogger().verbose("[GroupKick/"+BotAccount+"] " +"Group("+GroupID+") Target(" + TargetID + ")"+" <- "+Reason);
            bot.getGroupOrFail(GroupID).getOrFail(TargetID).kick(Reason);
            return true;
        } else {
            logger.warning("Bot account \""+BotAccount+"\" doesn't exist!");
            return false;
        }
    }
    /**
     * 向指定群的指定成员发送消息
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param TargetID 群成员QQ号
     * @param Message 消息内容
     * @return 成功返回true，失败返回false (此方法若返回false，则指定的机器人不在线)
     * @see MiraiNormalMember
     * @deprecated
     */
    @Deprecated
    public boolean sendGroupMemberMessage(long BotAccount, long GroupID, long TargetID, String Message){
        if(isBotOnline(BotAccount)){
            Bot bot = Bot.getInstance(BotAccount);
            bot.getLogger().verbose("[MessageSend/"+BotAccount+"] " +"Group("+GroupID+") Target(" + TargetID + ")"+" <- " + Message);
            bot.getGroupOrFail(GroupID).getOrFail(TargetID).sendMessage(Message);
            return true;
        } else return false;
    }
    /**
     * 判断指定群的指定成员是否被禁言
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param TargetID 被操作群员QQ号
     * @return 被禁言返回true，未被禁言false
     * @see MiraiNormalMember
     * @deprecated
     */
    @Deprecated
    public boolean isGroupMemberMuted(long BotAccount, long GroupID, long TargetID){
        Bot bot = Bot.getInstanceOrNull(BotAccount);
        if(isBotExist(bot)){
            assert bot != null;
            return bot.getGroupOrFail(GroupID).getOrFail(TargetID).isMuted();
        } else {
            logger.warning("Bot account \""+BotAccount+"\" doesn't exist!");
            return false;
        }
    }
}
