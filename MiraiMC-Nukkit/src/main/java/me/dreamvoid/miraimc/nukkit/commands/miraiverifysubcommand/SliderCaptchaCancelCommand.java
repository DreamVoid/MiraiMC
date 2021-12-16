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
public class SliderCaptchaCancelCommand extends BaseSubCommand {

    public SliderCaptchaCancelCommand(String name) {
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
            MiraiLoginSolver.solveSliderCaptcha(Long.parseLong(args[1]),true);
            sender.sendMessage(TextFormat.colorize('&',"&a已取消登录验证流程"));
        } else {
            sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法：/miraiverify slidercaptchacancel <账号>"));
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] { CommandParameter.newType("账号", CommandParamType.INT) };
    }
}
