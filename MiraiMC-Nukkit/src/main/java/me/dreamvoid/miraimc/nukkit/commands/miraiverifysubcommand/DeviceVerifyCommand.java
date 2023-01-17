package me.dreamvoid.miraimc.nukkit.commands.miraiverifysubcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseSubCommand;

/**
 * @author LT_Name
 */
public class DeviceVerifyCommand extends BaseSubCommand {

    public DeviceVerifyCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return true;
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
            if(args.length >= 2){
                if(args.length == 2){
                    MiraiLoginSolver.solve(Long.parseLong(args[1]));
                    sender.sendMessage(TextFormat.colorize('&',"&a已将验证请求提交到服务器"));
                } else if(args.length == 3){
                    MiraiLoginSolver.solve(Long.parseLong(args[1]), args[2]);
                    sender.sendMessage(TextFormat.colorize('&',"&a已将验证码提交到服务器"));
                }
            } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法：/miraiverify unsafedevice <账号>"));
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newType("账号", CommandParamType.INT),
                CommandParameter.newType("验证码", CommandParamType.TEXT)
        };
    }
}
