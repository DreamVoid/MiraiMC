package me.dreamvoid.miraimc.api;

import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Stranger;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.LoggerAdapters;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
     * 初始化
     * @param BotAccount 机器人账号
     * @throws NoSuchElementException 不存在机器人抛出
     */
    private MiraiBot(long BotAccount) throws NoSuchElementException {
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
     * @param Account 机器人账号
     * @param Password 机器人密码
     * @param Protocol 协议类型
     */
    public static void doBotLogin(long Account, String Password, BotConfiguration.MiraiProtocol Protocol) throws InterruptedException{
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(Password.getBytes(StandardCharsets.UTF_8));
            byte[] md5 = m.digest();
            privateBotLogin(Account, md5, Protocol);
        } catch (NoSuchAlgorithmException e) {
            if(Config.Gen_FriendlyException) {
                logger.warning("加密密码时出现异常，原因: " + e.getLocalizedMessage());
            } else e.printStackTrace();
        }
    }

    /**
     * 登录一个机器人账号
     * @param Account 机器人账号
     * @param PasswordMD5 机器人密码MD5
     * @param Protocol 协议类型
     */
    public static void doBotLogin(long Account, byte[] PasswordMD5, BotConfiguration.MiraiProtocol Protocol) throws InterruptedException{
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

    /**
     * 获取机器人昵称
     * @return 昵称
     */
    public String getNick() {
        return bot.getNick();
    }

    /**
     * 获取机器人QQ号
     * @return QQ号
     */
    public long getID() {
        return bot.getId();
    }

    /**
     * 获取机器人所有好友
     * @return 好友QQ号列表
     */
    public List<Long> getFriendList() {
        List<Long> result = new ArrayList<>();
        for(Friend friend : bot.getFriends()){
            result.add(friend.getId());
        }
        return result;
    }

    /**
     * 获取机器人加入的所有群
     * @return 群号列表
     */
    public List<Long> getGroupList() {
        List<Long> result = new ArrayList<>();
        for(Group group : bot.getGroups()){
            result.add(group.getId());
        }
        return result;
    }

    /**
     * 获取机器人所有陌生人
     * @return 陌生人QQ号列表
     */
    public List<Long> getStrangersList() {
        List<Long> result = new ArrayList<>();
        for(Stranger stranger : bot.getStrangers()){
            result.add(stranger.getId());
        }
        return result;
    }

    private static void privateBotLogin(long Account, byte[] Password, BotConfiguration.MiraiProtocol Protocol) throws InterruptedException {
        logger = Utils.logger;

        Bot existBot = Bot.getInstanceOrNull(Account);
        if(existBot != null){
            logger.info("另一个机器人进程已经存在，正在尝试关闭这个进程");
            MiraiLoginSolver.solvePicCaptcha(Account,true);
            MiraiLoginSolver.solveSliderCaptcha(Account, true);
            MiraiLoginSolver.solveUnsafeDeviceLoginVerify(Account, true);
            existBot.close();
            Thread.sleep(500);
        }

        logger.info("登录新的机器人账号: "+ Account+", 协议: "+ Protocol.name());

        File MiraiDir; if(!(Config.Gen_MiraiWorkingDir.equals("default"))) MiraiDir = new File(Config.Gen_MiraiWorkingDir); else MiraiDir = new File(Config.PluginDir,"MiraiBot"); // mirai数据文件夹
        File BotConfig = new File(MiraiDir, "bots/" + Account); // 当前机器人账号配置文件夹和相应的配置

        if(!BotConfig.exists() && !BotConfig.mkdirs()) throw new RuntimeException("Failed to create folder " + BotConfig.getPath());

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
        try{
            bot.login();
            logger.info(bot.getNick()+"("+bot.getId()+") 登录成功");
        } catch (Exception e){
            if(Config.Gen_FriendlyException) {
                logger.warning("登录机器人时出现异常，原因: " + e.getLocalizedMessage());
            } else e.printStackTrace();
        }
    }
}
