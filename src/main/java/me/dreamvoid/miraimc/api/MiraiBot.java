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

    public void doBotLogin(int Account, String Password, BotConfiguration.MiraiProtocol Protocol, Logger Logger) {
        privateBotLogin(Account, Password, Protocol, Logger);
    }

    public void doBotLogout(long Account) {
        Bot bot = Bot.getInstance(Account);
        bot.close();
    }

    public void sendFriendMessage(long BotAccount, long FriendID, String Message){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Send friend message to " + FriendID);
        bot.getFriendOrFail(FriendID).sendMessage(Message);
    }

    public void sendGroupMessage(long BotAccount, long GroupID, String Message){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Send group message to "+GroupID);
        bot.getGroupOrFail(GroupID).sendMessage(Message);
    }

    public boolean sendFriendNudge(long BotAccount, long FriendID){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Send friend nudge to " + FriendID);
        return(bot.nudge().sendTo(Bot.getInstance(BotAccount).getFriendOrFail(FriendID)));
    }

    public boolean sendGroupNudge(long BotAccount, long GroupID){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Send group nudge to " + GroupID);
        return(bot.nudge().sendTo(Bot.getInstance(BotAccount).getGroupOrFail(GroupID)));
    }

    public void setGroupMemberMute(long BotAccount, long GroupID, long TargetID, int Time){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Mute group member \""+GroupID+"\"/"+TargetID+"\" for "+Time+" second(s)");
        bot.getGroupOrFail(GroupID).getOrFail(TargetID).mute(Time);
    }

    public void setGroupMemberUnmute(long BotAccount, long GroupID, long TargetID){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Unmute group member \""+GroupID+"\"/"+TargetID+"\"");
        bot.getGroupOrFail(GroupID).getOrFail(TargetID).unmute();
    }

    public boolean isGroupMemberMuted(long BotAccount, long GroupID, long TargetID){
        return Bot.getInstance(BotAccount).getGroupOrFail(GroupID).getOrFail(TargetID).isMuted();
    }

    public void setGroupMemberKick(long BotAccount, long GroupID, long TargetID, String reason){
        Bot bot = Bot.getInstance(BotAccount);
        bot.getLogger().info("Kick group member \""+GroupID+"\"/"+TargetID+"\"");
        bot.getGroupOrFail(GroupID).getOrFail(TargetID).kick(reason);
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
            if(config.getBoolean("bot.new-logger.bot-logs",true)) { setBotLoggerSupplier(bot -> LoggerAdapters.asMiraiLogger(Logger)); }
            if(config.getBoolean("bot.new-logger.network-logs",true)) { setNetworkLoggerSupplier(bot -> LoggerAdapters.asMiraiLogger(Logger)); }

            // 是否使用缓存——对于开发者，请启用；对于用户，请禁用。详见 https://github.com/mamoe/mirai/blob/dev/docs/Bots.md#%E5%90%AF%E7%94%A8%E5%88%97%E8%A1%A8%E7%BC%93%E5%AD%98
            getContactListCache().setFriendListCacheEnabled(config.getBoolean("bot.contact-cache.enable-friend-list-cache",false));
            getContactListCache().setGroupMemberListCacheEnabled(config.getBoolean("bot.contact-cache.enable-group-member-list-cache",false));
            getContactListCache().setSaveIntervalMillis(config.getLong("bot.contact-cache.save-interval-millis",60000));

        }});

        // 开始登录
        bot.login();


    }

}
