package me.dreamvoid.miraimc.nukkit.commands.miraimcsubcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseSubCommand;

import java.io.IOException;

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
        try {
            MiraiMC.getConfig().loadConfig();
            sender.sendMessage(TextFormat.colorize('&', "&a配置文件已经重新加载，部分配置可能需要重新启动服务器才能生效！"));
        } catch (IOException e) {
            plugin.getLogger().warning("加载配置文件时出现问题，原因："+e);
            sender.sendMessage("&c重新配置文件时出现问题，请查看控制台了解更多信息！");
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}
