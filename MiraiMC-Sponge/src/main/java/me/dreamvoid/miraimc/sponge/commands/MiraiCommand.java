package me.dreamvoid.miraimc.sponge.commands;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.internal.httpapi.exception.AbnormalStatusException;
import me.dreamvoid.miraimc.sponge.MiraiAutoLogin;
import me.dreamvoid.miraimc.sponge.SpongePlugin;
import me.dreamvoid.miraimc.sponge.utils.AutoLoginObject;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.BotConfiguration;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class MiraiCommand implements CommandExecutor {
    PluginContainer plugin;

    public MiraiCommand(SpongePlugin plugin){
        this.plugin = plugin.getPluginContainer();
    }

    @Override
    public @NotNull CommandResult execute(@NotNull CommandSource src, CommandContext arg) throws ArgumentParseException {
        if(arg.<String>getOne("args").isPresent()){
            String argo = arg.<String>getOne("args").get();
            String[] args = argo.split("\\s+");
            switch (args[0].toLowerCase()){
                case "login": {
                    if(src.hasPermission("miraimc.command.mirai.login")){
                        if(args.length >= 3) {
                            Task.builder().async().name("MiraiMC Bot Login Task").execute(() -> {
                                BotConfiguration.MiraiProtocol Protocol = null;
                                boolean useHttpApi = false;
                                if(args.length == 3){
                                    Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                                } else if (args[3].equalsIgnoreCase("httpapi")) {
                                    useHttpApi = true;
                                } else try {
                                    Protocol = BotConfiguration.MiraiProtocol.valueOf(args[3].toUpperCase());
                                } catch (IllegalArgumentException ignored) {
                                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&e无效的协议类型，已自动选择 ANDROID_PHONE."));
                                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&e可用的协议类型: " + Arrays.toString(BotConfiguration.MiraiProtocol.values())
                                            .replace("[", "")
                                            .replace("]", "") + ", HTTPAPI"));
                                    Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                                }

                                try {
                                    if(!useHttpApi){
                                        MiraiBot.doBotLogin(Long.parseLong(args[1]),args[2], Protocol);
                                    } else {
                                        if(Config.Gen_EnableHttpApi) {
                                            MiraiHttpAPI httpAPI = new MiraiHttpAPI(Config.HTTPAPI_Url);
                                            httpAPI.bind(httpAPI.verify(args[2]).session, Long.parseLong(args[1]));
                                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a" + args[1] + " HTTP-API登录成功！"));
                                        } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c" + "此服务器没有启用HTTP-API模式，请检查配置文件！"));
                                    }
                                } catch (InterruptedException|IOException e) {
                                    Utils.logger.warning("登录机器人时出现异常，原因: " + e);
                                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c登录机器人时出现异常，请检查控制台输出！"));
                                } catch (AbnormalStatusException e) {
                                    Utils.logger.warning("使用HTTPAPI登录机器人时出现异常，状态码："+e.getCode()+"，原因: " + e.getMessage());
                                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c登录机器人时出现异常，状态码："+e.getCode()+"，原因: " + e.getMessage()));
                                }
                            }).submit(plugin);
                        } else {
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /mirai login <账号> <密码> [协议]"));
                        }
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "logout":{
                    if(src.hasPermission("miraimc.command.mirai.logout")){
                        if(args.length >= 2) {
                            try {
                                MiraiBot.getBot(Long.parseLong(args[1])).doLogout();
                                src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize( "&a已退出指定机器人！"));
                            } catch (NoSuchElementException e){
                                src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize( "&c指定的机器人不存在！"));
                            }
                        } else {
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /mirai logout <账号>"));
                        }
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "sendgroupmessage":{
                    if(src.hasPermission("miraimc.command.mirai.sendgroupmessage")){
                        if(args.length >= 4){
                            StringBuilder message = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {    //list.size()就是循环的次数
                                if(i >= 3){
                                    message.append(args[i]).append(" ");
                                }
                            }
                            MiraiBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2])).sendMessageMirai(message.toString().replace("\\n", System.lineSeparator()));
                        } else {
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /mirai sendgroupmessage <账号> <群号> <消息>"));
                        }
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "sendfriendmessage":{
                    if(src.hasPermission("miraimc.command.mirai.sendfriendmessage")){
                        if(args.length >= 4){
                            StringBuilder message = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {    //list.size()就是循环的次数
                                if(i >= 3){
                                    message.append(args[i]).append(" ");
                                }
                            }
                            MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendMessageMirai(message.toString().replace("\\n", System.lineSeparator()));
                        } else {
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /mirai sendfriendmessage <账号> <好友> <消息>"));
                        }
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "sendfriendnudge":{
                    if(src.hasPermission("miraimc.command.mirai.sendfriendnudge")){
                        if(args.length >= 3){
                            MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendNudge();
                        } else {
                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /mirai sendfriendnudge <账号> <好友>"));
                        }
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "list":{
                    if(src.hasPermission("miraimc.command.mirai.list")){
                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a存在的机器人: "));
                        List<Long> BotList = MiraiBot.getOnlineBots();
                        for (long bots : BotList){
                            Bot bot = Bot.getInstance(bots);
                            if(bot.isOnline()){
                                src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize( "&b"+bot.getId() + "&r &7-&r &6"+Bot.getInstance(bots).getNick()));
                            } else {
                                src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize( "&b"+bot.getId() + "&r &7-&r &c离线"));
                            }
                        }
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "checkonline":{
                    if(src.hasPermission("miraimc.command.mirai.checkonline")){
                        if(args.length >= 2){
                            if(MiraiBot.getBot(Long.parseLong(args[1])).isOnline()){
                                src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a当前机器人在线"));
                            } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&e当前机器人不在线"));
                        } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /mirai checkonline <账号>"));
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "autologin":{
                    if(src.hasPermission("miraimc.command.mirai.autologin")){

                        if(args.length>=2){
                            switch (args[1]){
                                case "add":{
                                    boolean result;
                                    if(args.length>=4){
                                        if(args.length == 5){
                                            result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], args[4]);
                                        } else result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], "ANDROID_PHONE");
                                        if(result){
                                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a新的自动登录机器人添加成功！"));
                                        } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c新的自动登录机器人添加失败，请检查控制台错误输出！"));
                                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /mirai autologin add <账号> <密码> [协议]"));
                                    break;
                                }
                                case "remove":{
                                    boolean result;
                                    if(args.length>=3){
                                        result = MiraiAutoLogin.delAutoLoginBot(Long.parseLong(args[2]));
                                        if(result){
                                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a删除自动登录机器人成功！"));
                                        } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c删除自动登录机器人失败，请检查控制台错误输出！"));
                                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c无效的参数！用法: /mirai autologin remove <账号>"));
                                    break;
                                }
                                case "list":{
                                    try {
                                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&a存在的自动登录机器人: "));
                                        List<AutoLoginObject.Accounts> AutoLoginBotList = MiraiAutoLogin.loadAutoLoginList();
                                        for (AutoLoginObject.Accounts bots : AutoLoginBotList) {
                                            src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&b" + bots.getAccount()));
                                        }
                                    } catch (FileNotFoundException e) {
                                        plugin.getLogger().warn("读取自动登录机器人列表时出现异常，原因: " + e);
                                        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c读取列表时出现异常，请查看控制台了解更多信息！"));
                                    }
                                    break;
                                }
                                default:{
                                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                                    break;
                                }
                            }
                        } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                    } else src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "help":{
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6&lMiraiMC&r &b机器人帮助菜单"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/mirai login <账号> <密码> [协议]:&r 登录一个机器人"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/mirai logout <账号>:&r 退出一个机器人"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/mirai list:&r 查看当前存在的机器人"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/mirai sendfriendmessage <账号> <好友> <消息>:&r 向指定好友发送私聊消息"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/mirai sendgroupmessage <账号> <群号> <消息>:&r 向指定群发送群聊消息"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/mirai sendfriendnudge <账号> <好友>:&r 向指定好友发送戳一戳"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/mirai checkonline <账号>:&r 检查指定的机器人是否在线"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/mirai autologin add <账号> <密码> [协议]:&r 添加一个自动登录账号"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/mirai autologin list:&r 查看自动登录账号列表"));
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&6/mirai autologin remove <账号>:&r 删除一个自动登录账号"));
                    break;
                }
                default:{
                    src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                    break;
                }
            }
            return CommandResult.builder().successCount(1).build();
        } else throw new ArgumentParseException(Text.of("isPresent() returned false!"),"MiraiMC",0);
    }
}
