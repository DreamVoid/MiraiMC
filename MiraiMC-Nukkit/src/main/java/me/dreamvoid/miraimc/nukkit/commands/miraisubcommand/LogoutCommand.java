package me.dreamvoid.miraimc.nukkit.commands.miraisubcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseSubCommand;

import java.util.NoSuchElementException;

/**
 * @author LT_Name
 */
public class LogoutCommand extends BaseSubCommand {

    public LogoutCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.hasPermission("miraimc.command.mirai.logout");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 2) {
            try {
                MiraiBot.getBot(Long.parseLong(args[1])).doLogout();
                sender.sendMessage(TextFormat.colorize('&', "&a已退出指定机器人！"));
            } catch (NoSuchElementException e){
                sender.sendMessage(TextFormat.colorize('&', "&c指定的机器人不存在！"));
            }
        } else {
            sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /mirai logout <账号>"));
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] { CommandParameter.newType("账号", CommandParamType.INT) };
    }
}
