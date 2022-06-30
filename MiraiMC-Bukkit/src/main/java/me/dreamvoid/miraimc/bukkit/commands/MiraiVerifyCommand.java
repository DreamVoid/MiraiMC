package me.dreamvoid.miraimc.bukkit.commands;

import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MiraiVerifyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args[0].toLowerCase()){
            case "unsafedevice":{
                if(args.length >= 2){
                    MiraiLoginSolver.solve(Long.parseLong(args[1]));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已将验证请求提交到服务器"));
                } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify unsafedevice <账号>"));
                break;
            }
            case "captcha":{
                if(args.length >= 3){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已将验证码提交到服务器"));
                    MiraiLoginSolver.solve(Long.parseLong(args[1]),args[2]);
                } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify captcha <账号> <验证码>"));
                break;
            }
            case "cancel":{
                if(args.length >= 2){
                    MiraiLoginSolver.cancel(Long.parseLong(args[1]));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已取消登录验证流程"));
                } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify cancel <账号>"));
                break;
            }
        }
        return true;
    }
}
