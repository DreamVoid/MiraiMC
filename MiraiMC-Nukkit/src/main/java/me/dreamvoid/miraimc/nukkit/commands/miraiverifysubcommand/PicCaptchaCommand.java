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
public class PicCaptchaCommand extends BaseSubCommand {

    public PicCaptchaCommand(String name) {
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
        if(args.length >= 3){
            sender.sendMessage(TextFormat.colorize('&',"&a已将验证码提交到服务器"));
            MiraiLoginSolver.solvePicCaptcha(Long.parseLong(args[1]),args[2]);
        } else {
            sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法：/miraiverify piccaptcha <账号> <验证码>"));
        }
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
