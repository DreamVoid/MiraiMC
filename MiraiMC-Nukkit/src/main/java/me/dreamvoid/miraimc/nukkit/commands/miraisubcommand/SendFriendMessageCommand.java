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
public class SendFriendMessageCommand extends BaseSubCommand {

    public SendFriendMessageCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.hasPermission("miraimc.command.mirai.sendfriendmessage");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 4){
            MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendMessage(args[3]);
        } else {
            sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /mirai sendfriendmessage <账号> <好友> <消息>"));
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newType("账号", CommandParamType.INT),
                CommandParameter.newType("好友", CommandParamType.INT),
                CommandParameter.newType("消息", CommandParamType.TEXT)
        };
    }
}
