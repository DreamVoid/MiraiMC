package me.dreamvoid.miraimc.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.velocity.utils.Color;
import net.kyori.adventure.text.Component;

public class MiraiVerifyCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();

        if(args.length>0){
            switch (args[0].toLowerCase()){
                case "unsafedevice":{
                    if(args.length >= 2){
                        MiraiLoginSolver.solve(Long.parseLong(args[1]));
                        source.sendMessage(Component.text(Color.translate("&a已将验证请求提交到服务器")));
                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法：/miraiverify unsafedevice <账号>")));
                    break;
                }
                case "unsafedevicecancel":{
                    if(args.length >= 2){
                        MiraiLoginSolver.cancel(Long.parseLong(args[1]));
                        source.sendMessage(Component.text(Color.translate("&a已取消登录验证流程")));
                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法：/miraiverify unsafedevicecancel <账号>")));
                    break;
                }
                case "slidercaptcha":{
                    if(args.length >= 3){
                        source.sendMessage(Component.text(Color.translate("&a已将ticket提交到服务器")));
                        MiraiLoginSolver.solve(Long.parseLong(args[1]),args[2]);
                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法：/miraiverify slidercaptcha <账号> <ticket>")));
                    break;
                }
                case "slidercaptchacancel":{
                    if(args.length >= 2){
                        MiraiLoginSolver.cancel(Long.parseLong(args[1]));
                        source.sendMessage(Component.text(Color.translate("&a已取消登录验证流程")));
                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法：/miraiverify slidercaptchacancel <账号>")));
                    break;
                }
                case "piccaptcha":{
                    if(args.length >= 3){
                        source.sendMessage(Component.text(Color.translate("&a已将验证码提交到服务器")));
                        MiraiLoginSolver.solve(Long.parseLong(args[1]),args[2]);
                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法：/miraiverify piccaptcha <账号> <验证码>")));
                    break;
                }
                case "piccaptchacancel":{
                    if(args.length >= 2){
                        MiraiLoginSolver.cancel(Long.parseLong(args[1]));
                        source.sendMessage(Component.text(Color.translate("&a已取消登录验证流程")));
                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法：/miraiverify piccaptchacancel <账号>")));
                    break;
                }
                default:break;
            }
        }
    }
}
