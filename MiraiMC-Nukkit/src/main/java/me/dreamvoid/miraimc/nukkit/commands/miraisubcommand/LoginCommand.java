package me.dreamvoid.miraimc.nukkit.commands.miraisubcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.internal.httpapi.response.Bind;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseSubCommand;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.IOException;
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
                    BotConfiguration.MiraiProtocol Protocol = null;
                    boolean useHttpApi = false;
                    if(args.length == 3){
                        Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                    } else if (args[3].equalsIgnoreCase("httpapi")) {
                        useHttpApi = true;
                    } else try {
                        Protocol = BotConfiguration.MiraiProtocol.valueOf(args[3].toUpperCase());
                    } catch (IllegalArgumentException ignored) {
                        sender.sendMessage(TextFormat.colorize('&',"&e无效的协议类型，已自动选择 ANDROID_PHONE."));
                        sender.sendMessage(TextFormat.colorize('&', "&e可用的协议类型: " + Arrays.toString(BotConfiguration.MiraiProtocol.values())
                                .replace("[", "")
                                .replace("]", "") + ", HTTPAPI"));
                        Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                    }

                    try {
                        if(!useHttpApi){
                            MiraiBot.doBotLogin(Long.parseLong(args[1]),args[2], Protocol);
                        } else {
                            if(Config.Gen_WorkingMode_HttpApi) {
                                MiraiHttpAPI httpAPI = new MiraiHttpAPI(Config.HTTPAPI_Url);
                                Bind bind = httpAPI.bind(httpAPI.verify(args[2]).session, Long.parseLong(args[1]));
                                if(bind.code == 0) {
                                    sender.sendMessage(TextFormat.GREEN + args[1] + " HTTP-API登录成功！");
                                } else {
                                    sender.sendMessage(TextFormat.YELLOW + "登录机器人时出现异常，原因: " + bind.msg);
                                }
                            } else sender.sendMessage(TextFormat.RED + "此服务器没有启用HTTP-API模式，请检查配置文件！");
                        }
                    } catch (InterruptedException e) {
                        if(Config.Gen_FriendlyException) {
                            Utils.logger.warning("登录机器人时出现异常，原因: " + e.getLocalizedMessage());
                        } else e.printStackTrace();
                        sender.sendMessage(TextFormat.colorize('&',"&c登录机器人时出现异常，请检查控制台输出！"));
                    } catch (IOException e) {
                        if(Config.Gen_FriendlyException) {
                            Utils.logger.warning("登录机器人时出现异常，原因: " + e);
                        } else e.printStackTrace();
                        sender.sendMessage(TextFormat.colorize('&',"&c登录机器人时出现异常，请检查控制台输出！"));
                    }
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
