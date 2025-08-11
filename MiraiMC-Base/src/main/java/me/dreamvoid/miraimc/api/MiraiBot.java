package me.dreamvoid.miraimc.api;

import me.dreamvoid.miraimc.api.bot.MiraiFriend;
import me.dreamvoid.miraimc.api.bot.MiraiFriendGroup;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.api.bot.MiraiOtherClient;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Stranger;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.LoggerAdapters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Mirai Core 核心接口
 * @author DreamVoid
 */
@SuppressWarnings("unused")
public class MiraiBot {
    private final Bot bot;
    private static Logger logger;

    static {
        Thread.currentThread().setContextClassLoader(Utils.getClassLoader());
    }

    /**
     * 通过账号初始化机器人实例
     * @param account 机器人账号
     * @throws NoSuchElementException 当机器人不存在时抛出
     */
    private MiraiBot(long account) throws NoSuchElementException {
        logger = Utils.getLogger();
        bot = Bot.getInstance(account);
    }

    /**
     * 初始化
     * @param bot 机器人实例
     */
    private MiraiBot(Bot bot){
        logger = Utils.getLogger();
        this.bot = bot;
    }

    /**
     * 将一个机器人实例转换为MiraiBot
     * @param bot 机器人实例
     * @return MiraiMC 机器人实例
     */
    public static MiraiBot asBot(Bot bot){
        return new MiraiBot(bot);
    }

    /**
     * 获取指定机器人的实例
     * @param account 机器人账号
     * @return MiraiMC 机器人实例
     * @throws NoSuchElementException 不存在时抛出
     */
    public static MiraiBot getBot(long account) throws NoSuchElementException{
        return new MiraiBot(account);
    }

    /**
     * 获取可用的 mirai 协议列表用于登录
     * @return 协议列表
     */
    public static List<String> getAvailableProtocol(){
        return Arrays.stream(BotConfiguration.MiraiProtocol.values()).map(Enum::name).collect(Collectors.toList());
    }

    /**
     * 获取所有在线机器人
     * @return 机器人账号列表
     */
    public static List<Long> getOnlineBots() {
        return Bot.getInstances().stream().map(Bot::getId).collect(Collectors.toList());
    }

    /**
     * 获取机器人指定好友的实例
     * @param friendAccount 好友QQ号
     * @return MiraiMC 好友实例
     * @throws IllegalArgumentException 当 friendAccount 无效时抛出
     */
    public MiraiFriend getFriend(long friendAccount) {
        if (friendAccount <= 0) {
            throw new IllegalArgumentException("Invalid friend account: " + friendAccount);
        }
        return new MiraiFriend(bot, friendAccount);
    }

    /**
     * 获取机器人指定群的实例
     * @param groupID 群号
     * @return MiraiMC 群实例
     */
    public MiraiGroup getGroup(long groupID){
        return new MiraiGroup(bot, groupID);
    }

    /**
     * 登录一个机器人账号<br>
     * [!] 不建议插件开发者调用此方法，建议引导用户通过MiraiMC指令登录机器人
     * @param account 机器人账号
     * @param password 机器人密码MD5
     * @param protocol 协议类型
     */
    public static void doBotLogin(long account, byte[] password, BotConfiguration.MiraiProtocol protocol) {
        loginCore(account, password, protocol);
    }

    /**
     * 登录一个机器人账号<br>
     * [!] 不建议插件开发者调用此方法，建议引导用户通过MiraiMC指令登录机器人
     * @param account 机器人账号
     * @param password 机器人密码
     * @param protocol 协议类型
     * @throws IllegalArgumentException 协议不存在时抛出
     */
    public static void doBotLogin(long account, String password, String protocol) throws IllegalArgumentException{
        doBotLogin(account, password, BotConfiguration.MiraiProtocol.valueOf(protocol));
    }

    /**
     * 登录一个机器人账号<br>
     * [!] 不建议插件开发者调用此方法，建议引导用户通过MiraiMC指令登录机器人
     * @param account 机器人账号
     * @param password 机器人密码MD5
     * @param protocol 协议名称
     * @throws IllegalArgumentException 当参数无效或协议不存在时抛出
     * @since 1.7
     */
    public static void doBotLogin(long account, byte[] password, String protocol) throws IllegalArgumentException{
        doBotLogin(account, password, BotConfiguration.MiraiProtocol.valueOf(protocol));
    }

    /**
     * 登录一个机器人账号<br>
     * [!] 不建议插件开发者调用此方法，建议引导用户通过MiraiMC指令登录机器人
     * @param account 机器人账号
     * @param password 机器人密码
     * @param protocol 协议类型
     * @throws IllegalArgumentException 当参数无效时抛出
     * @since 1.7
     */
    public static void doBotLogin(long account, String password, BotConfiguration.MiraiProtocol protocol) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes(StandardCharsets.UTF_8));
            doBotLogin(account, md.digest(), protocol);
        } catch (NoSuchAlgorithmException e) {
            logger.warning("加密密码时出现异常，原因: " + e);
        }
    }

    /**
     * 尝试将机器人设置为在线状态
     */
    public void doOnline() {
        bot.join();
    }

    /**
     * 登出一个机器人账号
     * @deprecated 请使用 {@link #close()}
     */
    @Deprecated
    public void doLogout() {
        bot.close();
    }

    /**
     * 关闭一个机器人实例，立刻停止有关此机器人的所有任务并登出机器人<br>
     * [!] 不建议插件开发者调用此方法，建议引导用户通过MiraiMC指令关闭机器人
     */
    public void close() {
        bot.close();
    }

    /**
     * 检查机器人是否在线
     * @return 在线返回true，离线返回false
     */
    public boolean isOnline() {
        return bot.isOnline();
    }

    /**
     * 检查机器人是否存在
     * @return 存在返回true，不存在返回false
     */
    public boolean isExist() {
        return bot != null;
    }

    /**
     * 获取机器人昵称
     * @return 昵称
     */
    public String getNick() {
        return bot.getNick();
    }

    /**
     * 获取机器人账号
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
        return bot.getFriends().stream()
                .map(Friend::getId)
                .collect(Collectors.toList());
    }

    /**
     * 获取机器人加入的所有群
     * @return 群号列表
     */
    public List<Long> getGroupList() {
        return bot.getGroups().stream()
                .map(Group::getId)
                .collect(Collectors.toList());
    }

    /**
     * 获取机器人所有陌生人
     * @return 陌生人QQ号列表
     */
    public List<Long> getStrangersList() {
        return bot.getStrangers().stream()
                .map(Stranger::getId)
                .collect(Collectors.toList());
    }

    /**
     * 将机器人作为好友获取好友实例
     * @return MiraiFriend 实例
     */
    public MiraiFriend getAsFriend(){
        return new MiraiFriend(bot.getBot(),bot.getId());
    }

    /**
     * 上传指定图片，获取图片 ID 用于发送消息
     * @param image 图片文件
     * @return 图片ID
     * @exception IOException 上传文件发生异常时抛出
     */
    public String uploadImage(File image) throws IOException {
        try(ExternalResource resource = ExternalResource.create(image).toAutoCloseable()){
            return bot.getAsFriend().uploadImage(resource).getImageId();
        }
    }

    /**
     * 获取指定的机器人登录的其他客户端
     * @param otherClient 其他客户端ID
     * @return MiraiMC其他客户端实例
     * @throws NoSuchElementException 当客户端不存在时抛出
     */
    public MiraiOtherClient getOtherClient(long otherClient) throws NoSuchElementException{
        return new MiraiOtherClient(bot.getOtherClients().getOrFail(otherClient));
    }

    /**
     * 获取机器人登录的所有其他客户端
     * @return {@link MiraiOtherClient} 实例数组
     */
    public List<MiraiOtherClient> getOtherClients() {
        return bot.getOtherClients().stream().map(MiraiOtherClient::new).collect(Collectors.toList());
    }

    /**
     * 获取好友分组列表
     * @return 好友分组列表
     */
    public List<MiraiFriendGroup> getFriendGroups(){
        return bot.getFriendGroups().asCollection().stream().map(g -> new MiraiFriendGroup(bot, g)).collect(Collectors.toList());
    }

    /**
     * 登录机器人（Mirai 核心）
     * @param account 账号
     * @param password 密码
     * @param protocol 协议
     */
    private static void loginCore(long account, byte[] password, BotConfiguration.MiraiProtocol protocol) {
        logger = Utils.getLogger();

        Bot existBot = Bot.getInstanceOrNull(account);
        if(existBot != null){
            logger.info("另一个机器人进程已经存在，正在尝试关闭这个进程");
            MiraiLoginSolver.cancel(account);
            existBot.close();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) { }
        }

        logger.info("登录新的机器人账号: "+ account+", 协议: "+ protocol.name());

        File BotConfig = new File(new File(Utils.getMiraiDir(), "bots"), String.valueOf(account)); // 当前机器人账号配置文件夹和相应的配置

        if(!BotConfig.exists() && !BotConfig.mkdirs()) throw new RuntimeException("无法创建文件夹 " + BotConfig.getPath());

        // 登录前的准备工作
        BotAuthorization authorization = Arrays.equals(new byte[]{-6, -127, 29, -75, 79, 68, 2, -7, -15, -24, 106, 21, -50, 23, 76, -88}, password) ? BotAuthorization.byQRCode() : BotAuthorization.byPassword(password);

        Bot bot = BotFactory.INSTANCE.newBot(account, authorization, new BotConfiguration(){{
            // 设置登录信息
            setProtocol(protocol); // 目前不打算让用户使用其他两个协议
            setWorkingDir(BotConfig);
            fileBasedDeviceInfo();

            // 是否关闭日志输出（不建议开发者关闭）。如果不关闭，是否使用Bukkit的Logger接管Mirai的Logger
            if(MiraiMC.getConfig().Bot_DisableNetworkLogs) {
                noNetworkLog();
            } else if(MiraiMC.getConfig().Bot_UseMinecraftLogger_NetworkLogs) {
                setNetworkLoggerSupplier(bot -> LoggerAdapters.asMiraiLogger(logger));
            }
            if(MiraiMC.getConfig().Bot_DisableBotLogs) {
                noBotLog();
            } else if(MiraiMC.getConfig().Bot_UseMinecraftLogger_BotLogs) {
                setBotLoggerSupplier(bot -> LoggerAdapters.asMiraiLogger(logger));
            }

            // 是否使用缓存——对于开发者，请启用；对于用户，请禁用。详见 https://github.com/mamoe/mirai/blob/dev/docs/Bots.md#%E5%90%AF%E7%94%A8%E5%88%97%E8%A1%A8%E7%BC%93%E5%AD%98
            getContactListCache().setFriendListCacheEnabled(MiraiMC.getConfig().Bot_ContactCache_EnableFriendListCache);
            getContactListCache().setGroupMemberListCacheEnabled(MiraiMC.getConfig().Bot_ContactCache_EnableGroupMemberListCache);
            getContactListCache().setSaveIntervalMillis(MiraiMC.getConfig().Bot_ContactCache_SaveIntervalMillis);

            // 使用自己的验证解决器
            setLoginSolver(new MiraiLoginSolver());
        }});

        // 开始登录
        try{
            bot.login();
            logger.info(bot.getNick()+"("+bot.getId()+") 登录成功");
        } catch (Exception e){
            logger.warning("登录机器人时出现异常，原因: " + e.getLocalizedMessage());
        }
    }
}
