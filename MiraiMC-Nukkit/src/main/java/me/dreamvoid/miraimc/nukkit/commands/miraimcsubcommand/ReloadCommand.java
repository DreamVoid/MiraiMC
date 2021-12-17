package me.dreamvoid.miraimc.nukkit.commands.miraimcsubcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.nukkit.NukkitConfig;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseSubCommand;

/**
 * @author LT_Name
 */
public class ReloadCommand extends BaseSubCommand {

    public ReloadCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.hasPermission("miraimc.command.miraimc.reload");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        NukkitConfig.reloadConfig();
        sender.sendMessage(TextFormat.colorize('&', "&a配置文件已经重新加载，部分配置可能需要重新启动服务器才能生效！"));
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}
