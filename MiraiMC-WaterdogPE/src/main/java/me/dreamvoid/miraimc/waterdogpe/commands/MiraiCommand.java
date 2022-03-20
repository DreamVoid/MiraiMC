package me.dreamvoid.miraimc.waterdogpe.commands;

import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.plugin.Plugin;
import me.dreamvoid.miraimc.api.MiraiBot;

import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.internal.httpapi.exception.AbnormalStatusException;
import me.dreamvoid.miraimc.waterdogpe.MiraiAutoLogin;
import me.dreamvoid.miraimc.waterdogpe.WDPEPlugin;
import me.dreamvoid.miraimc.waterdogpe.utils.TextFormat;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class MiraiCommand extends Command {
    private final WDPEPlugin plugin;
    private final MiraiAutoLogin MiraiAutoLogin;

    public MiraiCommand(WDPEPlugin plugin) {
        super("mirai");
        this.plugin = plugin;
        this.MiraiAutoLogin = new MiraiAutoLogin(plugin);
    }

    @Override
    public boolean onExecute(CommandSender sender, String alias, String[] args) {
        if (!(args.length == 0)) {
            switch (args[0].toLowerCase()) {
                case "login": {
                    if (sender.hasPermission("miraimc.command.mirai.login")) {
                        if (args.length >= 3) {
                            plugin.getProxy().getScheduler().scheduleAsync(new Runnable() {

                                @Override
                                public void run() {
                                    BotConfiguration.MiraiProtocol Protocol = null;
                                    boolean useHttpApi = false;
                                    if (args.length == 3) {
                                        Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                                    } else if (args[3].equalsIgnoreCase("httpapi")) {
                                        useHttpApi = true;
                                    } else try {
                                        Protocol = BotConfiguration.MiraiProtocol.valueOf(args[3].toUpperCase());
                                    } catch (IllegalArgumentException ignored) {

                                        sender.sendMessage(TextFormat.colorize('&', "&e无效的协议类型，已自动选择 ANDROID_PHONE."));
                                        sender.sendMessage(TextFormat.colorize('&', "&e可用的协议类型: " + Arrays.toString(BotConfiguration.MiraiProtocol.values())
                                                .replace("[", "")
                                                .replace("]", "") + ", HTTPAPI"));
                                        Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                                    }

                                    try {
                                        if (!useHttpApi) {
                                            MiraiBot.doBotLogin(Long.parseLong(args[1]), args[2], Protocol);
                                        } else {
                                            if (Config.Gen_EnableHttpApi) {
                                                MiraiHttpAPI httpAPI = new MiraiHttpAPI(Config.HTTPAPI_Url);
                                                httpAPI.bind(httpAPI.verify(args[2]).session, Long.parseLong(args[1]));
                                                sender.sendMessage(TextFormat.GREEN + args[1] + " HTTP-API登录成功！");
                                            } else sender.sendMessage(TextFormat.RED + "此服务器没有启用HTTP-API模式，请检查配置文件！");
                                        }
                                    } catch (InterruptedException e) {
                                        Utils.logger.warning("登录机器人时出现异常，原因: " + e.getLocalizedMessage());
                                        sender.sendMessage(TextFormat.colorize('&', "&c登录机器人时出现异常，请检查控制台输出！"));
                                    } catch (IOException e) {
                                        Utils.logger.warning("登录机器人时出现异常，原因: " + e);
                                        sender.sendMessage(TextFormat.colorize('&', "&c登录机器人时出现异常，请检查控制台输出！"));
                                    } catch (AbnormalStatusException e) {
                                        Utils.logger.warning("使用HTTPAPI登录机器人时出现异常，状态码：" + e.getCode() + "，原因: " + e.getMessage());
                                        sender.sendMessage(TextFormat.colorize('&', "&c登录机器人时出现异常，状态码：" + e.getCode() + "，原因: " + e.getMessage()));
                                    }
                                }
                            });

                        } else {
                            sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法: /mirai login <账号> <密码> [协议]"));
                        }
                    } else sender.sendMessage(TextFormat.colorize('&', "&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "logout": {
                    if (sender.hasPermission("miraimc.command.mirai.logout")) {
                        if (args.length >= 2) {
                            try {
                                MiraiBot.getBot(Long.parseLong(args[1])).doLogout();
                                sender.sendMessage(TextFormat.colorize('&', "&a已退出指定机器人！"));
                            } catch (NoSuchElementException e) {
                                if (Config.Gen_EnableHttpApi && MiraiHttpAPI.Bots.containsKey(Long.parseLong(args[1]))) {
                                    try {
                                        new MiraiHttpAPI(Config.HTTPAPI_Url).release(MiraiHttpAPI.Bots.get(Long.parseLong(args[1])), Long.parseLong(args[1]));
                                        sender.sendMessage(TextFormat.colorize('&', "&a已退出指定机器人！"));
                                    } catch (IOException ex) {
                                        Utils.logger.warning("退出机器人时出现异常，原因: " + ex);
                                        sender.sendMessage(TextFormat.colorize('&', "&c退出机器人时出现异常，请检查控制台输出！"));
                                    } catch (AbnormalStatusException ex) {
                                        if (ex.getCode() == 2) {
                                            sender.sendMessage(TextFormat.colorize('&', "&c指定的机器人不存在！"));
                                        } else {
                                            Utils.logger.warning("退出机器人时出现异常，状态码：" + ex.getCode() + "，原因: " + ex.getMessage());
                                            sender.sendMessage(TextFormat.colorize('&', "&c退出机器人时出现异常，状态码：" + ex.getCode() + "，原因: " + ex.getMessage()));
                                        }
                                    }
                                } else sender.sendMessage(TextFormat.colorize('&', "&c指定的机器人不存在！"));
                            }
                        } else {
                            sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法: /mirai logout <账号>"));
                        }
                    } else sender.sendMessage(TextFormat.colorize('&', "&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "sendgroupmessage": {
                    if (sender.hasPermission("miraimc.command.mirai.sendgroupmessage")) {
                        if (args.length >= 4) {
                            StringBuilder message = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {    //list.size()就是循环的次数
                                if (i >= 3) {
                                    message.append(args[i]).append(" ");
                                }
                            }
                            String text = message.toString().replace("\\n", System.lineSeparator());
                            try {
                                MiraiBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2])).sendMessage(text);
                            } catch (NoSuchElementException e) {
                                if (Config.Gen_EnableHttpApi && MiraiHttpAPI.Bots.containsKey(Long.parseLong(args[1]))) {
                                    try {
                                        MiraiHttpAPI.INSTANCE.sendGroupMessage(MiraiHttpAPI.Bots.get(Long.parseLong(args[1])), Long.parseLong(args[2]), text);
                                    } catch (IOException ex) {
                                        Utils.logger.warning("发送群消息时出现异常，原因: " + e);
                                        sender.sendMessage(TextFormat.colorize('&', "&c发送群消息时出现异常，请检查控制台了解更多信息！"));
                                    } catch (AbnormalStatusException ex) {
                                        sender.sendMessage(TextFormat.colorize('&', "&c发送群消息时出现异常，状态码: " + ex.getCode() + "，原因: " + ex.getMessage()));
                                    }
                                }
                            }
                        } else {
                            sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法: /mirai sendgroupmessage <账号> <群号> <消息>"));
                        }
                    } else sender.sendMessage(TextFormat.colorize('&', "&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "sendfriendmessage": {
                    if (sender.hasPermission("miraimc.command.mirai.sendfriendmessage")) {
                        if (args.length >= 4) {
                            StringBuilder message = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {    //list.size()就是循环的次数
                                if (i >= 3) {
                                    message.append(args[i]).append(" ");
                                }
                            }
                            String text = message.toString().replace("\\n", System.lineSeparator());
                            try {
                                MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendMessage(text);
                            } catch (NoSuchElementException e) {
                                if (Config.Gen_EnableHttpApi && MiraiHttpAPI.Bots.containsKey(Long.parseLong(args[1]))) {
                                    try {
                                        MiraiHttpAPI.INSTANCE.sendGroupMessage(MiraiHttpAPI.Bots.get(Long.parseLong(args[1])), Long.parseLong(args[2]), text);
                                    } catch (IOException ex) {
                                        Utils.logger.warning("发送好友消息时出现异常，原因: " + e);
                                        sender.sendMessage(TextFormat.colorize('&', "&c发送好友消息时出现异常，请检查控制台了解更多信息！"));
                                    } catch (AbnormalStatusException ex) {
                                        sender.sendMessage(TextFormat.colorize('&', "&c发送好友消息时出现异常，状态码: " + ex.getCode() + "，原因: " + ex.getMessage()));
                                    }
                                }
                            }
                        } else {
                            sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法: /mirai sendfriendmessage <账号> <好友> <消息>"));
                        }
                    } else sender.sendMessage(TextFormat.colorize('&', "&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "sendfriendnudge": {
                    if (sender.hasPermission("miraimc.command.mirai.sendfriendnudge")) {
                        if (args.length >= 3) {
                            MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendNudge();
                        } else {
                            sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法: /mirai sendfriendnudge <账号> <好友>"));
                        }
                    } else sender.sendMessage(TextFormat.colorize('&', "&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "list": {
                    if (sender.hasPermission("miraimc.command.mirai.list")) {
                        sender.sendMessage(TextFormat.colorize('&', "&a存在的机器人: "));
                        List<Long> BotList = MiraiBot.getOnlineBots();

                        // Core
                        for (Long bots : BotList) {
                            Bot bot = Bot.getInstance(bots);
                            if (bot.isOnline()) {
                                sender.sendMessage(TextFormat.colorize('&', "&b" + bot.getId() + "&r &7-&r &6" + Bot.getInstance(bots).getNick()));
                            } else {
                                sender.sendMessage(TextFormat.colorize('&', "&b" + bot.getId() + "&r &7-&r &c离线"));
                            }
                        }

                        // HTTP API
                        for (long botWithHttp : MiraiHttpAPI.Bots.keySet()) {
                            sender.sendMessage(TextFormat.colorize('&', "&b" + botWithHttp + "&r &7-&r &eHTTP API"));
                        }
                    } else sender.sendMessage(TextFormat.colorize('&', "&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "checkonline": {
                    if (sender.hasPermission("miraimc.command.mirai.checkonline")) {
                        if (args.length >= 2) {
                            if (MiraiBot.getBot(Long.parseLong(args[1])).isOnline()) {
                                sender.sendMessage(TextFormat.colorize('&', "&a当前机器人在线"));
                            } else sender.sendMessage(TextFormat.colorize('&', "&e当前机器人不在线"));
                        } else sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法: /mirai checkonline <账号>"));
                    } else sender.sendMessage(TextFormat.colorize('&', "&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "autologin": {
                    if (sender.hasPermission("miraimc.command.mirai.autologin")) {
                        if (args.length >= 2) {
                            switch (args[1]) {
                                case "add": {
                                    boolean result;
                                    if (args.length >= 4) {
                                        if (args.length == 5) {
                                            result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], args[4]);
                                        } else
                                            result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], "ANDROID_PHONE");
                                        if (result) {
                                            sender.sendMessage(TextFormat.colorize('&', "&a新的自动登录机器人添加成功！"));
                                        } else
                                            sender.sendMessage(TextFormat.colorize('&', "&c新的自动登录机器人添加失败，请检查控制台错误输出！"));
                                    } else
                                        sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法: /mirai autologin add <账号> <密码> [协议]"));
                                    break;
                                }
                                case "remove": {
                                    boolean result;
                                    if (args.length >= 3) {
                                        result = MiraiAutoLogin.delAutoLoginBot(Long.parseLong(args[2]));
                                        if (result) {
                                            sender.sendMessage(TextFormat.colorize('&', "&a删除自动登录机器人成功！"));
                                        } else
                                            sender.sendMessage(TextFormat.colorize('&', "&c删除自动登录机器人失败，请检查控制台错误输出！"));
                                    } else
                                        sender.sendMessage(TextFormat.colorize('&', "&c无效的参数！用法: /mirai autologin remove <账号>"));
                                    break;
                                }
                                case "list": {
                                    sender.sendMessage(TextFormat.colorize('&', "&a存在的自动登录机器人: "));
                                    List<Map> AutoLoginBotList = MiraiAutoLogin.loadAutoLoginList();
                                    for (Map<?, ?> bots : AutoLoginBotList) {
                                        sender.sendMessage(TextFormat.colorize('&', "&b" + bots.get("account")));
                                    }
                                    break;
                                }
                                default: {
                                    sender.sendMessage(TextFormat.colorize('&', "&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                                    break;
                                }
                            }
                        } else sender.sendMessage(TextFormat.colorize('&', "&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                    } else sender.sendMessage(TextFormat.colorize('&', "&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "help": {
                    sender.sendMessage(TextFormat.colorize('&', "&6&lMiraiMC&r &b机器人帮助菜单"));
                    sender.sendMessage(TextFormat.colorize('&', "&6/mirai login <账号> <密码> [协议]:&r 登录一个机器人"));
                    sender.sendMessage(TextFormat.colorize('&', "&6/mirai logout <账号>:&r 退出一个机器人"));
                    sender.sendMessage(TextFormat.colorize('&', "&6/mirai list:&r 查看当前存在的机器人"));
                    sender.sendMessage(TextFormat.colorize('&', "&6/mirai sendfriendmessage <账号> <好友> <消息>:&r 向指定好友发送私聊消息"));
                    sender.sendMessage(TextFormat.colorize('&', "&6/mirai sendgroupmessage <账号> <群号> <消息>:&r 向指定群发送群聊消息"));
                    sender.sendMessage(TextFormat.colorize('&', "&6/mirai sendfriendnudge <账号> <好友>:&r 向指定好友发送戳一戳"));
                    sender.sendMessage(TextFormat.colorize('&', "&6/mirai checkonline <账号>:&r 检查指定的机器人是否在线"));
                    sender.sendMessage(TextFormat.colorize('&', "&6/mirai autologin add <账号> <密码> [协议]:&r 添加一个自动登录账号"));
                    sender.sendMessage(TextFormat.colorize('&', "&6/mirai autologin list:&r 查看自动登录账号列表"));
                    sender.sendMessage(TextFormat.colorize('&', "&6/mirai autologin remove <账号>:&r 删除一个自动登录账号"));
                    break;
                }
                default: {
                    sender.sendMessage(TextFormat.colorize('&', "&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                    break;
                }
            }
            return true;
        } else {
            sender.sendMessage(TextFormat.colorize('&', "&6&lMiraiMC&r &b机器人帮助菜单"));
            sender.sendMessage(TextFormat.colorize('&', "&6/mirai login <账号> <密码> [协议]:&r 登录一个机器人"));
            sender.sendMessage(TextFormat.colorize('&', "&6/mirai logout <账号>:&r 退出一个机器人"));
            sender.sendMessage(TextFormat.colorize('&', "&6/mirai list:&r 查看当前存在的机器人"));
            sender.sendMessage(TextFormat.colorize('&', "&6/mirai sendfriendmessage <账号> <好友> <消息>:&r 向指定好友发送私聊消息"));
            sender.sendMessage(TextFormat.colorize('&', "&6/mirai sendgroupmessage <账号> <群号> <消息>:&r 向指定群发送群聊消息"));
            sender.sendMessage(TextFormat.colorize('&', "&6/mirai sendfriendnudge <账号> <好友>:&r 向指定好友发送戳一戳"));
            sender.sendMessage(TextFormat.colorize('&', "&6/mirai checkonline <账号>:&r 检查指定的机器人是否在线"));
            sender.sendMessage(TextFormat.colorize('&', "&6/mirai autologin add <账号> <密码> [协议]:&r 添加一个自动登录账号"));
            sender.sendMessage(TextFormat.colorize('&', "&6/mirai autologin list:&r 查看自动登录账号列表"));
            sender.sendMessage(TextFormat.colorize('&', "&6/mirai autologin remove <账号>:&r 删除一个自动登录账号"));
            return false;
        }
    }
}
