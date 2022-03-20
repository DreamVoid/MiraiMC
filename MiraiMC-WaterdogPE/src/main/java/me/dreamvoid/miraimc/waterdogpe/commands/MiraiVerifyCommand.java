package me.dreamvoid.miraimc.waterdogpe.commands;

import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.waterdogpe.utils.TextFormat;

/**
 * @author zixuan007
 * @description: 认证命令
 * @date: 2022/3/19 3:05 AM
 */
public class MiraiVerifyCommand extends Command {

    public MiraiVerifyCommand() {
        super("miraiverify");
    }

    @Override
    public boolean onExecute(CommandSender sender, String alias, String[] args) {
        switch (args[0].toLowerCase()) {
            case "unsafedevice": {
                if (args.length >= 2) {
                    MiraiLoginSolver.solveUnsafeDeviceLoginVerify(Long.parseLong(args[1]), false);
                    sender.sendMessage(TextFormat.colorize('&', "&a已将验证请求提交到服务器"));
                } else
                    sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法：/miraiverify unsafedevice <账号>"));
                break;
            }
            case "unsafedevicecancel": {
                if (args.length >= 2) {
                    MiraiLoginSolver.solveUnsafeDeviceLoginVerify(Long.parseLong(args[1]), true);
                    sender.sendMessage(TextFormat.colorize('&', "&a已取消登录验证流程"));
                } else
                    sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法：/miraiverify unsafedevicecancel <账号>"));
                break;
            }
            case "slidercaptcha": {
                if (args.length >= 3) {
                    sender.sendMessage(TextFormat.colorize('&', "&a已将ticket提交到服务器"));
                    MiraiLoginSolver.solveSliderCaptcha(Long.parseLong(args[1]), args[2]);
                } else
                    sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法：/miraiverify slidercaptcha <账号> <ticket>"));
                break;
            }
            case "slidercaptchacancel": {
                if (args.length >= 2) {
                    MiraiLoginSolver.solveSliderCaptcha(Long.parseLong(args[1]), true);
                    sender.sendMessage(TextFormat.colorize('&', "&a已取消登录验证流程"));
                } else
                    sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法：/miraiverify slidercaptchacancel <账号>"));
                break;
            }
            case "piccaptcha": {
                if (args.length >= 3) {
                    sender.sendMessage(TextFormat.colorize('&', "&a已将验证码提交到服务器"));
                    MiraiLoginSolver.solvePicCaptcha(Long.parseLong(args[1]), args[2]);
                } else
                    sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法：/miraiverify piccaptcha <账号> <验证码>"));
                break;
            }
            case "piccaptchacancel": {
                if (args.length >= 2) {
                    MiraiLoginSolver.solvePicCaptcha(Long.parseLong(args[1]), true);
                    sender.sendMessage(TextFormat.colorize('&', "&a已取消登录验证流程"));
                } else
                    sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法：/miraiverify piccaptchacancel <账号>"));
                break;
            }
            default:
                break;
        }
        return true;
    }
}
