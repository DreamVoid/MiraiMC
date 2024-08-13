package me.dreamvoid.miraimc.nukkit.commands.miraisubcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseSubCommand;
import net.mamoe.mirai.utils.BotConfiguration;

import java.util.Arrays;

/**
 * @author LT_Name
 */
public class LoginCommand extends BaseSubCommand {

    public LoginCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.hasPermission("miraimc.command.mirai.login");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 3) {
            plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                @Override
                public void onRun() {
                    BotConfiguration.MiraiProtocol Protocol;
                    if(args.length == 3){
                        Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                    } else try {
                        Protocol = BotConfiguration.MiraiProtocol.valueOf(args[3].toUpperCase());
                    } catch (IllegalArgumentException ignored) {
                        sender.sendMessage(TextFormat.colorize('&',"&e无效的协议类型，已自动选择 ANDROID_PHONE."));
                        sender.sendMessage(TextFormat.colorize('&', "&e可用的协议类型: " + Arrays.toString(BotConfiguration.MiraiProtocol.values())
                                .replace("[", "")
                                .replace("]", "")));
                        Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                    }

                    MiraiBot.doBotLogin(Long.parseLong(args[1]), args[2], Protocol);
                }
            });

        } else {
            sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /mirai login <账号> <密码> [协议]"));
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[] {
                CommandParameter.newType("账号", CommandParamType.INT),
                CommandParameter.newType("密码", CommandParamType.TEXT),
                CommandParameter.newType("协议", true, CommandParamType.TEXT)
        };
    }
}
