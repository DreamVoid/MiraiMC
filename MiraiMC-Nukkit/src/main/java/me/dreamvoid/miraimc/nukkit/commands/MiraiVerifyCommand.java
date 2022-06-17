package me.dreamvoid.miraimc.nukkit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseCommand;
import me.dreamvoid.miraimc.nukkit.commands.miraiverifysubcommand.*;

/**
 * @author LT_Name
 */
public class MiraiVerifyCommand extends BaseCommand {

    public MiraiVerifyCommand() {
        super("miraiverify", "MiraiBot LoginVerify Command.");
        this.setPermission("miraimc.command.miraiverify");
        this.setUsage("For help, type /mirai help");

        this.addSubCommand(new UnsafeDeviceCommand("UnsafeDevice"));
        this.addSubCommand(new UnsafeDeviceCancelCommand("UnsafeDeviceCancel"));
        this.addSubCommand(new SliderCaptchaCommand("SliderCaptcha"));
        this.addSubCommand(new SliderCaptchaCancelCommand("SliderCaptchaCancel"));
        this.addSubCommand(new PicCaptchaCommand("PicCaptcha"));
        this.addSubCommand(new PicCaptchaCancelCommand("PicCaptchaCancel"));
    }

    @Override
    public void sendHelp(CommandSender sender) {

    }

    @Override
    public void sendUI(Player player) {
        this.sendHelp(player);
    }
}
