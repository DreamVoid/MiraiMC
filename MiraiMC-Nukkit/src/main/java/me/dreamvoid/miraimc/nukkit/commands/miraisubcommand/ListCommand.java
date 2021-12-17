package me.dreamvoid.miraimc.nukkit.commands.miraisubcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseSubCommand;
import net.mamoe.mirai.Bot;

import java.util.List;

/**
 * @author LT_Name
 */
public class ListCommand extends BaseSubCommand {

    public ListCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.hasPermission("miraimc.command.mirai.list");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        sender.sendMessage(TextFormat.colorize('&',"&a存在的机器人: "));
        List<Long> BotList = MiraiBot.getOnlineBots();
        for (long bots : BotList){
            Bot bot = Bot.getInstance(bots);
            if(bot.isOnline()){
                sender.sendMessage(TextFormat.colorize('&', "&b"+bot.getId() + "&r &7-&r &6"+Bot.getInstance(bots).getNick()));
            } else {
                sender.sendMessage(TextFormat.colorize('&', "&b"+bot.getId() + "&r &7-&r &c离线"));
            }
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}
