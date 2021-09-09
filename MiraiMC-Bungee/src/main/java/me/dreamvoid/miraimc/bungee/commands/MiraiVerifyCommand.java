package me.dreamvoid.miraimc.bungee.commands;

import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class MiraiVerifyCommand extends Command {
    public MiraiVerifyCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length>0){
            switch (args[0].toLowerCase()){
                case "unsafedevice":{
                    if(args.length >= 2){
                        MiraiLoginSolver.solveUnsafeDeviceLoginVerify(Long.parseLong(args[1]),false);
                        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&a已将验证请求提交到服务器")));
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify unsafedevice <账号>")));
                    break;
                }
                case "unsafedevicecancel":{
                    if(args.length >= 2){
                        MiraiLoginSolver.solveUnsafeDeviceLoginVerify(Long.parseLong(args[1]),true);
                        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&a已取消登录验证流程")));
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify unsafedevicecancel <账号>")));
                    break;
                }
                case "slidercaptcha":{
                    if(args.length >= 3){
                        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&a已将ticket提交到服务器")));
                        MiraiLoginSolver.solveSliderCaptcha(Long.parseLong(args[1]),args[2]);
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify slidercaptcha <账号> <ticket>")));
                    break;
                }
                case "slidercaptchacancel":{
                    if(args.length >= 2){
                        MiraiLoginSolver.solveSliderCaptcha(Long.parseLong(args[1]),true);
                        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&a已取消登录验证流程")));
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify slidercaptchacancel <账号>")));
                    break;
                }
                case "piccaptcha":{
                    if(args.length >= 3){
                        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&a已将验证码提交到服务器")));
                        MiraiLoginSolver.solvePicCaptcha(Long.parseLong(args[1]),args[2]);
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify piccaptcha <账号> <验证码>")));
                    break;
                }
                case "piccaptchacancel":{
                    if(args.length >= 2){
                        MiraiLoginSolver.solvePicCaptcha(Long.parseLong(args[1]),true);
                        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&a已取消登录验证流程")));
                    } else sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify piccaptchacancel <账号>")));
                    break;
                }
            }
        }
    }
}
