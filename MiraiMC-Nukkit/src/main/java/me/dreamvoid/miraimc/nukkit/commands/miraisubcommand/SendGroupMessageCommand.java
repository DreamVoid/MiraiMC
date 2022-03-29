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
public class SendGroupMessageCommand extends BaseSubCommand {

    public SendGroupMessageCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.hasPermission("miraimc.command.mirai.sendgroupmessage");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 4){
            MiraiBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2])).sendMessageMirai(args[3]);
        } else {
            sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /mirai sendgroupmessage <账号> <群号> <消息>"));
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newType("账号", CommandParamType.INT),
                CommandParameter.newType("群号", CommandParamType.INT),
                CommandParameter.newType("消息", CommandParamType.TEXT)
        };
    }
}
