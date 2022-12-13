package me.dreamvoid.miraimc.nukkit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseCommand;
import me.dreamvoid.miraimc.nukkit.commands.miraimcsubcommand.BindCommand;
import me.dreamvoid.miraimc.nukkit.commands.miraimcsubcommand.ReloadCommand;

import java.util.Arrays;

/**
 * @author LT_Name
 */
public class MiraiMcCommand extends BaseCommand {

    public MiraiMcCommand() {
        super("miraimc", "MiraiBot Plugin Command.");
        this.setPermission("miraimc.command.miraimc");
        this.setUsage("For help, type /miraimc help");

        this.addSubCommand(new ReloadCommand("Reload"));
        this.addSubCommand(new BindCommand("Bind"));
    }

    @Override
    public void sendHelp(CommandSender sender) {
        for (String s : Arrays.asList("&6&lMiraiMC&r &b插件帮助菜单",
                "&6/miraimc bind add <玩家名> <QQ号>:&r 为玩家和QQ号添加绑定",
                "&6/miraimc bind getplayer <玩家名>:&r 获取指定玩家名绑定的QQ号",
                "&6/miraimc bind getqq <QQ号>:&r 获取指定QQ号绑定的玩家名",
                "&6/miraimc bind removeplayer <玩家名>:&r 删除一个玩家的绑定",
                "&6/miraimc bind removeqq <QQ号>:&r 删除一个QQ号的绑定",
                "&6/miraimc reload:&r 重新加载插件")) {
            sender.sendMessage(TextFormat.colorize('&', s));
        }
    }

    @Override
    public void sendUI(Player player) {
        this.sendHelp(player);
    }
}
