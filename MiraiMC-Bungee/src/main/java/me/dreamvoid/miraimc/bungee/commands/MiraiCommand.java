package me.dreamvoid.miraimc.bungee.commands;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bungee.BungeePlugin;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.BotConfiguration;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class MiraiCommand extends Command {

    private final BungeePlugin bungee;
    private final me.dreamvoid.miraimc.bungee.MiraiAutoLogin MiraiAutoLogin;

    public MiraiCommand(BungeePlugin bungee, String name) {
        super(name);
        this.bungee = bungee;
        this.MiraiAutoLogin = me.dreamvoid.miraimc.bungee.MiraiAutoLogin.Instance;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(args.length == 0)){
            switch (args[0].toLowerCase()){
                case "login": {
                    if(sender.hasPermission("miraimc.command.mirai.login")){
                        if(args.length >= 3) {
                            bungee.getProxy().getScheduler().runAsync(bungee, () -> {
                                BotConfiguration.MiraiProtocol Protocol;
                                if(args.length == 3){
                                    Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                                } else if (args[3].equalsIgnoreCase("android_phone")) {
                                    Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                                } else if(args[3].equalsIgnoreCase("android_pad")){
                                    Protocol= BotConfiguration.MiraiProtocol.ANDROID_PAD;
                                } else if (args[3].equalsIgnoreCase("android_watch")) {
                                    Protocol = BotConfiguration.MiraiProtocol.ANDROID_WATCH;
                                } else {
                                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&e无效的协议类型，已自动选择 ANDROID_PHONE.")));
                                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&e可用的协议类型: ANDROID_PHONE, ANDROID_PAD, ANDROID_WATCH.")));
                                    Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                                }
                                try {
                                    MiraiBot.doBotLogin(Long.parseLong(args[1]),args[2], Protocol);
                                } catch (InterruptedException e) {
                                    if(Config.Gen_FriendlyException){
                                        Utils.logger.warning("登录机器人时出现异常，原因: " + e.getLocalizedMessage());
                                    } else e.printStackTrace();
                                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c登录机器人时出现异常，请检查控制台输出！")));
                                }
                            });
                        } else {
                            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai login <账号> <密码> [协议]")));
                        }
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "logout":{
                    if(sender.hasPermission("miraimc.command.mirai.logout")){
                        if(args.length >= 2) {
                            try {
                                MiraiBot.getBot(Long.parseLong(args[1])).doLogout();
                                sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&a已退出指定机器人！")));
                            } catch (NoSuchElementException e){
                                sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c指定的机器人不存在！")));
                            }
                        } else {
                            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai logout <账号>")));
                        }
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "sendgroupmessage":{
                    if(sender.hasPermission("miraimc.command.mirai.sendgroupmessage")){
                        if(args.length >= 4){
                            MiraiBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2])).sendMessage(args[3]);
                        } else {
                            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai sendgroupmessage <账号> <群号> <消息>")));
                        }
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "sendfriendmessage":{
                    if(sender.hasPermission("miraimc.command.mirai.sendfriendmessage")){
                        if(args.length >= 4){
                            MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendMessage(args[3]);
                        } else {
                            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai sendfriendmessage <账号> <好友> <消息>")));
                        }
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "sendfriendnudge":{
                    if(sender.hasPermission("miraimc.command.mirai.sendfriendnudge")){
                        if(args.length >= 3){
                            MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendNudge();
                        } else {
                            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai sendfriendnudge <账号> <好友>")));
                        }
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "list":{
                    if(sender.hasPermission("miraimc.command.mirai.list")){
                        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&a存在的机器人: ")));
                        List<Long> BotList = MiraiBot.getOnlineBots();
                        for (long bots : BotList){
                            Bot bot = Bot.getInstance(bots);
                            if(bot.isOnline()) {
                                sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&b" + bot.getId() + "&r &7-&r &6" + Bot.getInstance(bots).getNick())));
                            } else {
                                sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&b" + bot.getId() + "&r &7-&r &c离线")));
                            }
                        }
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "checkonline":{
                    if(sender.hasPermission("miraimc.command.mirai.checkonline")){
                        if(args.length >= 2){
                            if(MiraiBot.getBot(Long.parseLong(args[1])).isOnline()){
                                sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&a当前机器人在线")));
                            } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&e当前机器人不在线")));
                        } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai checkonline <账号>")));
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "autologin":{
                    if(sender.hasPermission("miraimc.command.mirai.autologin")){
                        if(args.length>=2){
                            switch (args[1]){
                                case "add":{
                                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c此功能在当前环境(BungeeCord)下不可用，请等待未来的版本！")));
                                    /*
                                    boolean result;
                                    if(args.length>=4){
                                        if(args.length == 5){
                                            result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], args[4]);
                                        } else result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], "ANDROID_PHONE");
                                        if(result){
                                            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&a新的自动登录机器人添加成功！")));
                                        } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c新的自动登录机器人添加失败，请检查控制台错误输出！")));
                                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai autologin add <账号> <密码> [协议]")));
                                    */
                                    break;
                                }
                                case "remove":{
                                    boolean result;
                                    if(args.length>=3){
                                        result = MiraiAutoLogin.delAutoLoginBot(Long.parseLong(args[2]));
                                        if(result){
                                            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&a删除自动登录机器人成功！")));
                                        } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c删除自动登录机器人失败，请检查控制台错误输出！")));
                                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai autologin remove <账号>")));
                                    break;
                                }
                                case "list":{
                                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&a存在的自动登录机器人: ")));
                                    try {
                                        for(Object list : MiraiAutoLogin.loadAutoLoginList()){
                                            Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.JsonConfiguration.class).load(list.toString());

                                            long Account = data.getLong("account");
                                            if(Account != 123456){
                                                sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&b"+Account)));
                                            }
                                        }
                                    } catch (IOException e) {
                                        if(!Config.Gen_FriendlyException) {
                                            e.printStackTrace();
                                        }
                                        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c执行自动登录时出现异常，原因: "+e.getLocalizedMessage())));
                                    }
                                    break;
                                }
                                default:{
                                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /mirai help 查看帮助！")));
                                    break;
                                }
                            }
                        } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /mirai help 查看帮助！")));
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "help":{
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6&lMiraiMC&r &b机器人帮助菜单")));
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6/mirai login <账号> <密码> [协议]:&r 登录一个机器人")));
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6/mirai logout <账号>:&r 退出一个机器人")));
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6/mirai list:&r 查看当前存在的机器人")));
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6/mirai sendfriendmessage <账号> <好友> <消息>:&r 向指定好友发送私聊消息")));
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6/mirai sendgroupmessage <账号> <群号> <消息>:&r 向指定群发送群聊消息")));
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6/mirai sendfriendnudge <账号> <好友>:&r 向指定好友发送戳一戳")));
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6/mirai checkonline <账号>:&r 检查指定的机器人是否在线")));
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6/mirai autologin add <账号> <密码> [协议]:&r 添加一个自动登录账号")));
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6/mirai autologin list:&r 查看自动登录账号列表")));
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&6/mirai autologin remove <账号>:&r 删除一个自动登录账号")));
                    break;
                }
                default:{
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /mirai help 查看帮助！")));
                    break;
                }
            }
        } else {
            sender.sendMessage(new TextComponent("This server is running "+ bungee.getDescription().getName() +" version "+ bungee.getDescription().getVersion()+" by "+ bungee.getDescription().getAuthor()));
        }
    }
}
