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
public class SendFriendNudgeCommand extends BaseSubCommand {

    public SendFriendNudgeCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.hasPermission("miraimc.command.mirai.sendfriendnudge");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 3){
            MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendNudge();
        } else {
            sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /mirai sendfriendnudge <账号> <好友>"));
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newType("账号", CommandParamType.INT),
                CommandParameter.newType("好友", CommandParamType.INT)
        };
    }
}
