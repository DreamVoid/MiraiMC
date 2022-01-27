package me.dreamvoid.miraimc.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.internal.httpapi.exception.AbnormalStatusException;
import me.dreamvoid.miraimc.velocity.VelocityPlugin;
import me.dreamvoid.miraimc.velocity.utils.AutoLoginObject;
import me.dreamvoid.miraimc.velocity.utils.Color;
import net.kyori.adventure.text.Component;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class MiraiCommand implements SimpleCommand {

    private final VelocityPlugin plugin;
    private final me.dreamvoid.miraimc.velocity.MiraiAutoLogin MiraiAutoLogin;

    public MiraiCommand(VelocityPlugin plugin) {
        this.plugin = plugin;
        this.MiraiAutoLogin = plugin.MiraiAutoLogin;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();

        if(!(args.length == 0)){
            switch (args[0].toLowerCase()){
                case "login": {
                    if(source.hasPermission("miraimc.command.mirai.login")){
                        if(args.length >= 3) {
                            plugin.getServer().getScheduler().buildTask(plugin, () -> {
                                BotConfiguration.MiraiProtocol Protocol = null;
                                boolean useHttpApi = false;
                                if(args.length == 3){
                                    Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                                } else if (args[3].equalsIgnoreCase("httpapi")) {
                                    useHttpApi = true;
                                } else try {
                                    Protocol = BotConfiguration.MiraiProtocol.valueOf(args[3].toUpperCase());
                                } catch (IllegalArgumentException ignored) {
                                    source.sendMessage(Component.text(Color.translate("&e无效的协议类型，已自动选择 ANDROID_PHONE.")));
                                    source.sendMessage(Component.text(Color.translate("&e可用的协议类型: " + Arrays.toString(BotConfiguration.MiraiProtocol.values())
                                            .replace("[", "")
                                            .replace("]", "") + ", HTTPAPI")));
                                    Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                                }

                                try {
                                    if(!useHttpApi){
                                        MiraiBot.doBotLogin(Long.parseLong(args[1]),args[2], Protocol);
                                    } else {
                                        if(Config.Gen_EnableHttpApi) {
                                            MiraiHttpAPI httpAPI = new MiraiHttpAPI(Config.HTTPAPI_Url);
                                            httpAPI.bind(httpAPI.verify(args[2]).session, Long.parseLong(args[1]));
                                            source.sendMessage(Component.text(Color.translate("&a" + args[1] + " HTTP-API登录成功！")));
                                        } else source.sendMessage(Component.text(Color.translate("&c" + "此服务器没有启用HTTP-API模式，请检查配置文件！")));
                                    }
                                } catch (InterruptedException e) {
                                    if(Config.Gen_FriendlyException){
                                        Utils.logger.warning("登录机器人时出现异常，原因: " + e.getLocalizedMessage());
                                    } else e.printStackTrace();
                                    source.sendMessage(Component.text(Color.translate("&c登录机器人时出现异常，请检查控制台输出！")));
                                } catch (IOException e) {
                                    if(Config.Gen_FriendlyException) {
                                        Utils.logger.warning("登录机器人时出现异常，原因: " + e);
                                    } else e.printStackTrace();
                                    source.sendMessage(Component.text(Color.translate("&c登录机器人时出现异常，请检查控制台输出！")));
                                } catch (AbnormalStatusException e) {
                                    Utils.logger.warning("使用HTTPAPI登录机器人时出现异常，状态码："+e.getCode()+"，原因: " + e.getLocalizedMessage());
                                    source.sendMessage(Component.text(Color.translate("&c登录机器人时出现异常，状态码："+e.getCode()+"，原因: " + e.getLocalizedMessage())));
                                }
                            }).schedule();
                        } else {
                            source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /mirai login <账号> <密码> [协议]")));
                        }
                    } else source.sendMessage(Component.text(Color.translate("&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "logout":{
                    if(source.hasPermission("miraimc.command.mirai.logout")){
                        if(args.length >= 2) {
                            try {
                                MiraiBot.getBot(Long.parseLong(args[1])).doLogout();
                                source.sendMessage(Component.text(Color.translate( "&a已退出指定机器人！")));
                            } catch (NoSuchElementException e){
                                source.sendMessage(Component.text(Color.translate( "&c指定的机器人不存在！")));
                            }
                        } else {
                            source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /mirai logout <账号>")));
                        }
                    } else source.sendMessage(Component.text(Color.translate("&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "sendgroupmessage":{
                    if(source.hasPermission("miraimc.command.mirai.sendgroupmessage")){
                        if(args.length >= 4){
                            StringBuilder message = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {    //list.size()就是循环的次数
                                if(i >= 3){
                                    message.append(args[i]).append(" ");
                                }
                            }
                            MiraiBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2])).sendMessage(message.toString().replace("\\n",System.lineSeparator()));
                        } else {
                            source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /mirai sendgroupmessage <账号> <群号> <消息>")));
                        }
                    } else source.sendMessage(Component.text(Color.translate("&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "sendfriendmessage":{
                    if(source.hasPermission("miraimc.command.mirai.sendfriendmessage")){
                        if(args.length >= 4){
                            StringBuilder message = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {    //list.size()就是循环的次数
                                if(i >= 3){
                                    message.append(args[i]).append(" ");
                                }
                            }
                            MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendMessage(message.toString().replace("\\n",System.lineSeparator()));
                        } else {
                            source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /mirai sendfriendmessage <账号> <好友> <消息>")));
                        }
                    } else source.sendMessage(Component.text(Color.translate("&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "sendfriendnudge":{
                    if(source.hasPermission("miraimc.command.mirai.sendfriendnudge")){
                        if(args.length >= 3){
                            MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendNudge();
                        } else {
                            source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /mirai sendfriendnudge <账号> <好友>")));
                        }
                    } else source.sendMessage(Component.text(Color.translate("&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "list":{
                    if(source.hasPermission("miraimc.command.mirai.list")){
                        source.sendMessage(Component.text(Color.translate("&a存在的机器人: ")));
                        List<Long> BotList = MiraiBot.getOnlineBots();
                        for (long bots : BotList){
                            Bot bot = Bot.getInstance(bots);
                            if(bot.isOnline()) {
                                source.sendMessage(Component.text(Color.translate( "&b" + bot.getId() + "&r &7-&r &6" + Bot.getInstance(bots).getNick())));
                            } else {
                                source.sendMessage(Component.text(Color.translate( "&b" + bot.getId() + "&r &7-&r &c离线")));
                            }
                        }
                    } else source.sendMessage(Component.text(Color.translate("&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "checkonline":{
                    if(source.hasPermission("miraimc.command.mirai.checkonline")){
                        if(args.length >= 2){
                            if(MiraiBot.getBot(Long.parseLong(args[1])).isOnline()){
                                source.sendMessage(Component.text(Color.translate("&a当前机器人在线")));
                            } else source.sendMessage(Component.text(Color.translate("&e当前机器人不在线")));
                        } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /mirai checkonline <账号>")));
                    } else source.sendMessage(Component.text(Color.translate("&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "autologin":{
                    if(source.hasPermission("miraimc.command.mirai.autologin")){
                        if(args.length>=2){
                            switch (args[1]){
                                case "add":{
                                    boolean result;
                                    if(args.length>=4){
                                        if(args.length == 5){
                                            result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], args[4]);
                                        } else result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], "ANDROID_PHONE");
                                        if(result){
                                            source.sendMessage(Component.text(Color.translate("&a新的自动登录机器人添加成功！")));
                                        } else source.sendMessage(Component.text(Color.translate("&c新的自动登录机器人添加失败，请检查控制台错误输出！")));
                                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /mirai autologin add <账号> <密码> [协议]")));

                                    break;
                                }
                                case "remove":{
                                    boolean result;
                                    if(args.length>=3){
                                        result = MiraiAutoLogin.delAutoLoginBot(Long.parseLong(args[2]));
                                        if(result){
                                            source.sendMessage(Component.text(Color.translate("&a删除自动登录机器人成功！")));
                                        } else source.sendMessage(Component.text(Color.translate("&c删除自动登录机器人失败，请检查控制台错误输出！")));
                                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /mirai autologin remove <账号>")));
                                    break;
                                }
                                case "list":{
                                    source.sendMessage(Component.text(Color.translate("&a存在的自动登录机器人: ")));
                                    try {
                                        source.sendMessage(Component.text(Color.translate("&a存在的自动登录机器人: ")));
                                        List<AutoLoginObject.Accounts> AutoLoginBotList = MiraiAutoLogin.loadAutoLoginList();
                                        for (AutoLoginObject.Accounts bots : AutoLoginBotList) {
                                            source.sendMessage(Component.text(Color.translate("&b" + bots.getAccount())));
                                        }
                                    } catch (FileNotFoundException e) {
                                        plugin.getLogger().warn("读取自动登录机器人列表时出现异常，原因: " + e);
                                        source.sendMessage(Component.text(Color.translate("&c读取列表时出现异常，请查看控制台了解更多信息！")));
                                    }
                                    break;
                                }
                                default:{
                                    source.sendMessage(Component.text(Color.translate("&c未知或不完整的命令，请输入 /mirai help 查看帮助！")));
                                    break;
                                }
                            }
                        } else source.sendMessage(Component.text(Color.translate("&c未知或不完整的命令，请输入 /mirai help 查看帮助！")));
                    } else source.sendMessage(Component.text(Color.translate("&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "help":{
                    source.sendMessage(Component.text(Color.translate("&6&lMiraiMC&r &b机器人帮助菜单")));
                    source.sendMessage(Component.text(Color.translate("&6/mirai login <账号> <密码> [协议]:&r 登录一个机器人")));
                    source.sendMessage(Component.text(Color.translate("&6/mirai logout <账号>:&r 退出一个机器人")));
                    source.sendMessage(Component.text(Color.translate("&6/mirai list:&r 查看当前存在的机器人")));
                    source.sendMessage(Component.text(Color.translate("&6/mirai sendfriendmessage <账号> <好友> <消息>:&r 向指定好友发送私聊消息")));
                    source.sendMessage(Component.text(Color.translate("&6/mirai sendgroupmessage <账号> <群号> <消息>:&r 向指定群发送群聊消息")));
                    source.sendMessage(Component.text(Color.translate("&6/mirai sendfriendnudge <账号> <好友>:&r 向指定好友发送戳一戳")));
                    source.sendMessage(Component.text(Color.translate("&6/mirai checkonline <账号>:&r 检查指定的机器人是否在线")));
                    source.sendMessage(Component.text(Color.translate("&6/mirai autologin add <账号> <密码> [协议]:&r 添加一个自动登录账号")));
                    source.sendMessage(Component.text(Color.translate("&6/mirai autologin list:&r 查看自动登录账号列表")));
                    source.sendMessage(Component.text(Color.translate("&6/mirai autologin remove <账号>:&r 删除一个自动登录账号")));
                    break;
                }
                default:{
                    source.sendMessage(Component.text(Color.translate("&c未知或不完整的命令，请输入 /mirai help 查看帮助！")));
                    break;
                }
            }
        } else {
            source.sendMessage(Component.text("This server is running "+ plugin.getPluginContainer().getDescription().getName() +" version "+ plugin.getPluginContainer().getDescription().getVersion()+" by "+ plugin.getPluginContainer().getDescription().getAuthors().toString().replace("[","").replace("]","")));
        }
    }
}
