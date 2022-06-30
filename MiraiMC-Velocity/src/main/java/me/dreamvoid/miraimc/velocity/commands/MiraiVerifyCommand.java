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
                case "captcha":{
                    if(args.length >= 3){
                        source.sendMessage(Component.text(Color.translate("&a已将验证码提交到服务器")));
                        MiraiLoginSolver.solve(Long.parseLong(args[1]),args[2]);
                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法：/miraiverify captcha <账号> <验证码>")));
                    break;
                }
                case "cancel":{
                    if(args.length >= 2){
                        MiraiLoginSolver.cancel(Long.parseLong(args[1]));
                        source.sendMessage(Component.text(Color.translate("&a已取消登录验证流程")));
                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法：/miraiverify cancel <账号>")));
                    break;
                }
                default:break;
            }
        }
    }
}
