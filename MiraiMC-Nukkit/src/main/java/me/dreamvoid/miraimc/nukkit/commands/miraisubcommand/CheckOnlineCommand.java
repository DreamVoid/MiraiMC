package me.dreamvoid.miraimc.nukkit.commands.miraisubcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseSubCommand;

/**
 * @author LT_Name
 */
public class CheckOnlineCommand extends BaseSubCommand {

    public CheckOnlineCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.hasPermission("miraimc.command.mirai.checkonline");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 2){
            if(MiraiBot.getBot(Long.parseLong(args[1])).isOnline()){
                sender.sendMessage(TextFormat.colorize('&',"&a当前机器人在线"));
            } else {
                sender.sendMessage(TextFormat.colorize('&',"&e当前机器人不在线"));
            }
        } else {
            sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /mirai checkonline <账号>"));
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] { CommandParameter.newType("账号", CommandParamType.INT) };
    }
}
