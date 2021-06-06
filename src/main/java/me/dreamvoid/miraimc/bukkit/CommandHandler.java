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
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e未指定协议类型，已自动选择 ANDROID_PHONE."));
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e可用的协议类型: ANDROID_PHONE, ANDROID_PAD, ANDROID_WATCH."));
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
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai login <账号> <密码>"));
                            return true;
                        }
                    }
                    case "logout":{
                        if(args.length >= 2) {
                            mirai.doBotLogout(Long.parseLong(args[1]));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已关闭指定机器人进程！"));
                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c请指定需要关闭的机器人账号！"));
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
