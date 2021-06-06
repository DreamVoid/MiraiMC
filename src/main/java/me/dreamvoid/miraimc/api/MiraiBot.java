package me.dreamvoid.miraimc.api;

import me.dreamvoid.miraimc.internal.Config;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.LoggerAdapters;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Logger;

public class MiraiBot {

    private static final YamlConfiguration config = Config.config;

    public MiraiBot() { }

    /**
     * 登录一个机器人账号
     * [!] 尚未完善多机器人管理。目前，不建议插件开发者在发行版插件中调用此接口
     * @param Account 机器人账号
     * @param Password 机器人密码
     * @param Protocol 协议类型
     * @param Logger 日志方法 —— 使用 Bukkit.getLogger()
     */
    public void doBotLogin(int Account, String Password, BotConfiguration.MiraiProtocol Protocol, Logger Logger) {
        privateBotLogin(Account, Password, Protocol, Logger);
    }

    /**
     * 登出一个机器人账号
     * [!] 尚未完善多机器人管理。目前，不建议插件开发者在发行版插件中调用此接口
     * @param Account 机器人账号
     */
    public void doBotLogout(long Account) {
        Bot bot = Bot.getInstance(Account);
        bot.close();
    }

    /**
     * 向指定好友发送消息
     * @param BotAccount 机器人账号
     * @param FriendID 好友QQ号
     * @param Message 消息内容
     */
    public void sendFriendMessage(long BotAccount, long FriendID, String Message){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Send friend message to " + FriendID);
        bot.getFriendOrFail(FriendID).sendMessage(Message);
    }

    /**
     * 向指定群发送消息
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param Message 消息内容
     */
    public void sendGroupMessage(long BotAccount, long GroupID, String Message){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Send group message to "+GroupID);
        bot.getGroupOrFail(GroupID).sendMessage(Message);
    }

    /**
     * 向指定好友发送戳一戳
     * @param BotAccount 机器人账号
     * @param FriendID 好友QQ号
     * @return
     */
    public boolean sendFriendNudge(long BotAccount, long FriendID){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Send friend nudge to " + FriendID);
        return(bot.nudge().sendTo(Bot.getInstance(BotAccount).getFriendOrFail(FriendID)));
    }

    /**
     * 向指定群发送戳一戳
     * (!) 未经测试
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @return
     */
    public boolean sendGroupNudge(long BotAccount, long GroupID){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Send group nudge to " + GroupID);
        return(bot.nudge().sendTo(Bot.getInstance(BotAccount).getGroupOrFail(GroupID)));
    }

    /**
     * 在指定群禁言指定成员(要求机器人为管理员或群主)
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param TargetID 被操作群员QQ号
     * @param Time 时间(秒)
     */
    public void setGroupMemberMute(long BotAccount, long GroupID, long TargetID, int Time){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Mute group member \""+GroupID+"\"/"+TargetID+"\" for "+Time+" second(s)");
        bot.getGroupOrFail(GroupID).getOrFail(TargetID).mute(Time);
    }

    /**
     * 在指定群解除禁言指定成员(要求机器人为管理员或群主)
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param TargetID 被操作群员QQ号
     */
    public void setGroupMemberUnmute(long BotAccount, long GroupID, long TargetID){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Unmute group member \""+GroupID+"\"/"+TargetID+"\"");
        bot.getGroupOrFail(GroupID).getOrFail(TargetID).unmute();
    }

    /**
     * 判断指定群的指定成员是否被禁言
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param TargetID 被操作群员QQ号
     * @return
     */
    public boolean isGroupMemberMuted(long BotAccount, long GroupID, long TargetID){
        return Bot.getInstance(BotAccount).getGroupOrFail(GroupID).getOrFail(TargetID).isMuted();
    }

    /**
     * 踢出指定群的指定成员
     * @param BotAccount 机器人账号
     * @param GroupID 群号
     * @param TargetID 被操作群员QQ号
     * @param Reason 理由
     */
    public void setGroupMemberKick(long BotAccount, long GroupID, long TargetID, String Reason){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Kick group member \""+GroupID+"\"/"+TargetID+"\"");
        bot.getGroupOrFail(GroupID).getOrFail(TargetID).kick(Reason);
    }

    private void privateBotLogin(int Account, String Password, BotConfiguration.MiraiProtocol Protocol, Logger Logger){

        Logger.info("Preparing for bot account login: "+ Account+", Protocol: "+ Protocol.name());

        // 建立mirai数据文件夹
        File MiraiDir = new File(String.valueOf(Config.PluginDir),"MiraiBot");
        if(!(MiraiDir.exists())){ if(!(MiraiDir.mkdir())) { Logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立机器人账号文件夹
        File BotDir = new File(String.valueOf(MiraiDir),"bots");
        if(!(BotDir.exists())){ if(!(BotDir.mkdir())) { Logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立当前机器人账号配置文件夹和相应的配置
        File BotConfig = new File(String.valueOf(BotDir), String.valueOf(Account));
        if(!(BotConfig.exists())){ if(!(BotConfig.mkdir())) { Logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 登录前的准备工作
        Bot bot = BotFactory.INSTANCE.newBot(Account, Password, new BotConfiguration(){{
            // 设置登录信息
            setProtocol(Protocol); // 目前不打算让用户使用其他两个协议
            setWorkingDir(BotConfig);
            fileBasedDeviceInfo();

            // 是否关闭日志输出（不建议开发者关闭）
            if(config.getBoolean("bot.disable-network-logs",false)) { noNetworkLog(); }
            if(config.getBoolean("bot.disable-bot-logs",false)) { noBotLog(); }

            // 是否使用Bukkit的Logger接管Mirai的Logger
            if(config.getBoolean("bot.use-bukkit-logger.bot-logs",true)) { setBotLoggerSupplier(bot -> LoggerAdapters.asMiraiLogger(Logger)); }
            if(config.getBoolean("bot.use-bukkit-logger.network-logs",true)) { setNetworkLoggerSupplier(bot -> LoggerAdapters.asMiraiLogger(Logger)); }

            // 是否使用缓存——对于开发者，请启用；对于用户，请禁用。详见 https://github.com/mamoe/mirai/blob/dev/docs/Bots.md#%E5%90%AF%E7%94%A8%E5%88%97%E8%A1%A8%E7%BC%93%E5%AD%98
            getContactListCache().setFriendListCacheEnabled(config.getBoolean("bot.contact-cache.enable-friend-list-cache",false));
            getContactListCache().setGroupMemberListCacheEnabled(config.getBoolean("bot.contact-cache.enable-group-member-list-cache",false));
            getContactListCache().setSaveIntervalMillis(config.getLong("bot.contact-cache.save-interval-millis",60000));

        }});

        // 开始登录
        bot.login();


    }

}
