package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import net.mamoe.mirai.utils.BotConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import org.jetbrains.annotations.NotNull;

public class CommandHandler implements CommandExecutor {

    private final BukkitPlugin plugin;
    private final MiraiBot mirai;

    public CommandHandler(BukkitPlugin plugin) {
        this.plugin = plugin;
        this.mirai = new MiraiBot();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(command.getName().equals("mirai")) {
            if(!(args.length == 0)){
                switch (args[0].toLowerCase()){
                    case "login": {
                        if(args.length >= 3) {
                            new BukkitRunnable(){

                                @Override
                                public void run() {
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
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e无效的协议类型，已自动选择 ANDROID_PHONE."));
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e可用的协议类型: ANDROID_PHONE, ANDROID_PAD, ANDROID_WATCH."));
                                        Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                                    }
                                    mirai.doBotLogin(Integer.parseInt(args[1]),args[2], Protocol, Bukkit.getLogger());
                                }
                            }.runTaskAsynchronously(plugin);
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai login <账号> <密码> [协议]"));
                        }
                        return true;
                    }
                    case "logout":{
                        if(args.length >= 2) {
                            mirai.doBotLogout(Long.parseLong(args[1]));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已关闭指定机器人进程！"));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai logout <账号>"));
                        }
                        return true;
                    }
                    case "sendgroupmessage":{
                        if(args.length>=4){
                            mirai.sendGroupMessage(Long.parseLong(args[1]), Long.parseLong(args[2]),args[3]);
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai sendgroupmessage <账号> <群号> <消息>"));
                        }
                        return true;
                    }
                    case "sendfriendmessage":{
                        if(args.length>=4){
                            mirai.sendFriendMessage(Long.parseLong(args[1]), Long.parseLong(args[2]),args[3]);
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai sendfriendmessage <账号> <好友账号> <消息>"));
                        }
                        return true;
                    }
                    case "help":{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&lMiraiMC&r &b机器人帮助菜单"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai login <账号> <密码> [协议]:&r 登录一个机器人"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai logout <账号>:&r 关闭一个机器人线程"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai sendfriendmessage <账号> <好友账号> <消息>:&r 向指定好友发送私聊消息"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai sendgroupmessage <账号> <群号> <消息>:&r 向指定群发送群聊消息"));
                        return true;
                    }
                    default:{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                        return true;
                    }
                }
            } else {
                sender.sendMessage("This server is running "+ plugin.getDescription().getName() +" version "+ plugin.getDescription().getVersion()+" by "+ plugin.getDescription().getAuthors().toString().replace("[","").replace("]",""));
                return false;
            }
        }
        if(command.getName().equals("miraimc")) {
            if(!(args.length == 0)) {
                switch (args[0].toLowerCase()) {
                    case "reload": {
                        Config.LoadConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a配置文件已经重新加载！"));
                        return true;
                    }
                    case "help":{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&lMiraiMC&r &b插件帮助菜单"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/miraimc reload:&r 重新加载插件"));
                        return true;
                    }
                    default:{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /miraimc help 查看帮助！"));
                        return true;
                    }
                }
            } else {
                sender.sendMessage("This server is running "+ plugin.getDescription().getName() +" version "+ plugin.getDescription().getVersion()+" by "+ plugin.getDescription().getAuthors().toString().replace("[","").replace("]",""));
                return false;
            }
        }
        return false;

    }
}
