package me.dreamvoid.miraimc.commands;

import me.dreamvoid.miraimc.IMiraiAutoLogin;
import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.MiraiMCPlugin;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.httpapi.exception.AbnormalStatusException;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.libloader.JarLoader;
import me.dreamvoid.miraimc.internal.libloader.MiraiLoader;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.BotConfiguration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class MiraiCommand implements ICommandExecutor {
    private final IMiraiAutoLogin MiraiAutoLogin = MiraiMCPlugin.getPlatform().getAutoLogin();

    @Override
    public boolean onCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("This server is running "+ MiraiMCPlugin.getPlatform().getPluginName() +" version "+ MiraiMCPlugin.getPlatform().getPluginVersion()+" by "+ MiraiMCPlugin.getPlatform().getAuthors().toString().replace("[","").replace("]",""));
            return false;
        }

        switch (args[0].toLowerCase()){
            case "login": {
                if(sender.hasPermission("miraimc.command.mirai.login")){
                    if(args.length >= 3) {
                        MiraiMCPlugin.getPlatform().runTaskAsync(() -> {
                            BotConfiguration.MiraiProtocol Protocol = null;
                            boolean useHttpApi = false;
                            if (args.length == 3) {
                                Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                            } else if (args[3].equalsIgnoreCase("HTTPAPI")) {
                                useHttpApi = true;
                            } else try {
                                Protocol = BotConfiguration.MiraiProtocol.valueOf(args[3].toUpperCase());
                            } catch (IllegalArgumentException ignored) {
                                sender.sendMessage("&e无效的协议类型，请检查输入！");
                                sender.sendMessage("&e可用的协议类型: " + MiraiBot.getAvailableProtocol(true).toString().replace("[", "").replace("]", ""));
                                return;
                            }

                            try {
                                if(!useHttpApi){
                                    MiraiBot.doBotLogin(Long.parseLong(args[1]),args[2], Protocol);
                                } else {
                                    if(MiraiMCConfig.General.EnableHttpApi) {
                                        MiraiHttpAPI httpAPI = new MiraiHttpAPI(MiraiMCConfig.HttpApi.Url);
                                        httpAPI.bind(httpAPI.verify(args[2]).session, Long.parseLong(args[1]));
                                        sender.sendMessage("&a" + args[1] + " HTTP-API登录成功！");
                                    } else sender.sendMessage("&c此服务器没有启用HTTP-API模式，请检查配置文件！");
                                }
                            } catch (IOException e) {
                                Utils.logger.warning("登录机器人时出现异常，原因: " + e);
                                sender.sendMessage("&c登录机器人时出现异常，请检查控制台输出！");
                            } catch (AbnormalStatusException e) {
                                Utils.logger.warning("使用HTTPAPI登录机器人时出现异常，状态码："+e.getCode()+"，原因: " + e.getMessage());
                                sender.sendMessage("&c登录机器人时出现异常，状态码："+e.getCode()+"，原因: " + e.getMessage());
                            }
                        });
                    } else {
                        sender.sendMessage("&c无效的参数！用法: /mirai login <账号> <密码> [协议]");
                    }
                } else sender.sendMessage("&c你没有足够的权限执行此命令！");
                break;
            }
            case "logout":{
                if(sender.hasPermission("miraimc.command.mirai.logout")){
                    if(args.length >= 2) {
                        try {
                            MiraiBot.getBot(Long.parseLong(args[1])).close();
                            sender.sendMessage( "&a已退出指定机器人！");
                        } catch (NoSuchElementException e){
                            if(MiraiMCConfig.General.EnableHttpApi && MiraiHttpAPI.Bots.containsKey(Long.parseLong(args[1]))){
                                try {
                                    new MiraiHttpAPI(MiraiMCConfig.HttpApi.Url).release(MiraiHttpAPI.Bots.get(Long.parseLong(args[1])),Long.parseLong(args[1]));
                                    sender.sendMessage( "&a已退出指定机器人！");
                                } catch (IOException ex) {
                                    Utils.logger.warning("退出机器人时出现异常，原因: " + ex);
                                    sender.sendMessage("&c退出机器人时出现异常，请检查控制台输出！");
                                } catch (AbnormalStatusException ex) {
                                    if(ex.getCode() == 2){
                                        sender.sendMessage( "&c指定的机器人不存在！");
                                    } else {
                                        Utils.logger.warning("退出机器人时出现异常，状态码："+ex.getCode()+"，原因: "+ex.getMessage());
                                        sender.sendMessage("&c退出机器人时出现异常，状态码："+ex.getCode()+"，原因: "+ex.getMessage());
                                    }
                                }
                            } else sender.sendMessage( "&c指定的机器人不存在！");
                        }
                    } else {
                        sender.sendMessage("&c无效的参数！用法: /mirai logout <账号>");
                    }
                } else sender.sendMessage("&c你没有足够的权限执行此命令！");
                break;
            }
            case "sendgroupmessage":{
                if(sender.hasPermission("miraimc.command.mirai.sendgroupmessage")){
                    if(args.length >= 4){
                        StringBuilder message = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            if(i >= 3){
                                message.append(args[i]).append(" ");
                            }
                        }
                        String text = message.toString().replace("\\n",System.lineSeparator());
                        try {
                            MiraiBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2])).sendMessageMirai(text);
                        } catch (NoSuchElementException e){
                            if(MiraiMCConfig.General.EnableHttpApi && MiraiHttpAPI.Bots.containsKey(Long.parseLong(args[1]))){
                                try {
                                    MiraiHttpAPI.INSTANCE.sendGroupMessage(MiraiHttpAPI.Bots.get(Long.parseLong(args[1])), Long.parseLong(args[2]), text);
                                } catch (IOException ex) {
                                    Utils.logger.warning("发送群消息时出现异常，原因: "+ e);
                                    sender.sendMessage("&c发送群消息时出现异常，请检查控制台了解更多信息！");
                                } catch (AbnormalStatusException ex) {
                                    sender.sendMessage("&c发送群消息时出现异常，状态码: " + ex.getCode()+"，原因: "+ex.getMessage());
                                }
                            }
                        }
                    } else {
                        sender.sendMessage("&c无效的参数！用法: /mirai sendgroupmessage <账号> <群号> <消息>");
                    }
                } else sender.sendMessage("&c你没有足够的权限执行此命令！");
                break;
            }
            case "sendfriendmessage":{
                if(sender.hasPermission("miraimc.command.mirai.sendfriendmessage")){
                    if(args.length >= 4){
                        StringBuilder message = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            if(i >= 3){
                                message.append(args[i]).append(" ");
                            }
                        }
                        String text = message.toString().replace("\\n",System.lineSeparator());
                        try {
                            MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendMessageMirai(text);
                        } catch (NoSuchElementException e){
                            if(MiraiMCConfig.General.EnableHttpApi && MiraiHttpAPI.Bots.containsKey(Long.parseLong(args[1]))){
                                try {
                                    MiraiHttpAPI.INSTANCE.sendGroupMessage(MiraiHttpAPI.Bots.get(Long.parseLong(args[1])), Long.parseLong(args[2]), text);
                                } catch (IOException ex) {
                                    Utils.logger.warning("发送好友消息时出现异常，原因: "+ e);
                                    sender.sendMessage("&c发送好友消息时出现异常，请检查控制台了解更多信息！");
                                } catch (AbnormalStatusException ex) {
                                    sender.sendMessage("&c发送好友消息时出现异常，状态码: " + ex.getCode()+"，原因: "+ex.getMessage());
                                }
                            }
                        }
                    } else {
                        sender.sendMessage("&c无效的参数！用法: /mirai sendfriendmessage <账号> <好友> <消息>");
                    }
                } else sender.sendMessage("&c你没有足够的权限执行此命令！");
                break;
            }
            case "sendfriendnudge":{
                if(sender.hasPermission("miraimc.command.mirai.sendfriendnudge")){
                    if(args.length >= 3){
                        MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendNudge();
                    } else {
                        sender.sendMessage("&c无效的参数！用法: /mirai sendfriendnudge <账号> <好友>");
                    }
                } else sender.sendMessage("&c你没有足够的权限执行此命令！");
                break;
            }
            case "list":{
                if(sender.hasPermission("miraimc.command.mirai.list")){
                    sender.sendMessage("&a存在的机器人: ");

                    // Core
                    for (Long bots : MiraiBot.getOnlineBots()) {
                        Bot bot = Bot.getInstance(bots);
                        if (bot.isOnline()) {
                            sender.sendMessage("&b" + bot.getId() + "&r &7-&r &6" + Bot.getInstance(bots).getNick());
                        } else {
                            sender.sendMessage("&b" + bot.getId() + "&r &7-&r &c离线");
                        }
                    }

                    // HTTP API
                    for (long botWithHttp : MiraiHttpAPI.Bots.keySet()){
                        sender.sendMessage("&b"+botWithHttp + "&r &7-&r &eHTTP API");
                    }
                } else sender.sendMessage("&c你没有足够的权限执行此命令！");
                break;
            }
            case "checkonline":{
                if(sender.hasPermission("miraimc.command.mirai.checkonline")){
                    if(args.length >= 2){
                        try {
                            if (MiraiBot.getBot(Long.parseLong(args[1])).isOnline()) {
                                sender.sendMessage("&a当前机器人在线");
                            } else sender.sendMessage("&e当前机器人不在线");
                        } catch (NoSuchElementException e){
                            sender.sendMessage("&e当前机器人不存在");
                        }
                    } else sender.sendMessage("&c无效的参数！用法: /mirai checkonline <账号>");
                } else sender.sendMessage("&c你没有足够的权限执行此命令！");
                break;
            }
            case "autologin":{
                if(sender.hasPermission("miraimc.command.mirai.autologin")){
                    if(args.length>=2){
                        switch (args[1]){
                            case "add":{
                                boolean result;
                                if(args.length>=4){
                                    if(args.length == 5){
                                        result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], args[4]);
                                    } else result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], "ANDROID_PHONE");
                                    if(result){
                                        sender.sendMessage("&a新的自动登录机器人添加成功！");
                                    } else sender.sendMessage("&c新的自动登录机器人添加失败，请检查控制台错误输出！");
                                } else sender.sendMessage("&c无效的参数！用法: /mirai autologin add <账号> <密码> [协议]");
                                break;
                            }
                            case "remove":{
                                boolean result;
                                if(args.length>=3){
                                    result = MiraiAutoLogin.delAutoLoginBot(Long.parseLong(args[2]));
                                    if(result){
                                        sender.sendMessage("&a删除自动登录机器人成功！");
                                    } else sender.sendMessage("&c删除自动登录机器人失败，请检查控制台错误输出！");
                                } else sender.sendMessage("&c无效的参数！用法: /mirai autologin remove <账号>");
                                break;
                            }
                            case "list":{
                                sender.sendMessage("&a存在的自动登录机器人: ");
                                try {
                                    List<Map<?,?>> AutoLoginBotList = MiraiAutoLogin.loadAutoLoginList();
                                    for (Map<?,?> bots : AutoLoginBotList){
                                        sender.sendMessage("&b"+bots.get("account"));
                                    }
                                } catch (IOException e) {
                                    sender.sendMessage("&c执行自动登录命令时出现异常，原因: "+e);
                                }

                                break;
                            }
                            default:{
                                sender.sendMessage("&c未知或不完整的命令，请输入 /mirai help 查看帮助！");
                                break;
                            }
                        }
                    } else sender.sendMessage("&c未知或不完整的命令，请输入 /mirai help 查看帮助！");
                } else sender.sendMessage("&c你没有足够的权限执行此命令！");
                break;
            }
            case "uploadimage":{
                if(sender.hasPermission("miraimc.command.mirai.uploadimage")) {
                    if (args.length >= 3) {
                        File ImageDir = new File(MiraiMCConfig.PluginDir, "images");
                        if(!ImageDir.exists()) ImageDir.mkdir();
                        File image = new File(ImageDir, args[2]);

                        if(!image.exists() || image.isDirectory()) {
                            sender.sendMessage("&c指定的图片文件不存在，请检查是否存在文件" + image.getPath()+"！");
                            break;
                        }

                        try {
                            sender.sendMessage( "&a图片上传成功，可使用Mirai Code发送图片：[mirai:image:" + MiraiBot.getBot(Long.parseLong(args[1])).uploadImage(image) + "]");
                        } catch (NoSuchElementException e){
                            sender.sendMessage("&c指定的机器人不存在！");
                            break;
                        }

                    } else sender.sendMessage("&c未知或不完整的命令，请输入 /mirai help 查看帮助！");
                } else sender.sendMessage("&c你没有足够的权限执行此命令！");
                break;
            }
            case "help":{
                for (String s : Arrays.asList("&6&lMiraiMC&r &b机器人帮助菜单",
                        "&6/mirai login <账号> <密码> [协议]:&r 登录一个机器人",
                        "&6/mirai logout <账号>:&r 退出一个机器人",
                        "&6/mirai list:&r 查看当前存在的机器人",
                        "&6/mirai sendfriendmessage <账号> <好友> <消息>:&r 向指定好友发送私聊消息",
                        "&6/mirai sendgroupmessage <账号> <群号> <消息>:&r 向指定群发送群聊消息",
                        "&6/mirai sendfriendnudge <账号> <好友>:&r 向指定好友发送戳一戳",
                        "&6/mirai uploadimage <账号> <图片文件名>:&r 上传指定图片",
                        "&6/mirai checkonline <账号>:&r 检查指定的机器人是否在线",
                        "&6/mirai autologin add <账号> <密码> [协议]:&r 添加一个自动登录账号",
                        "&6/mirai autologin list:&r 查看自动登录账号列表",
                        "&6/mirai autologin remove <账号>:&r 删除一个自动登录账号")) {
                    sender.sendMessage(s);
                }
                break;
            }
            case "dev":{
                if(Boolean.getBoolean("MiraiMC.DevelopmentMode")) {
                    if (args.length == 1) {
                        sender.sendMessage("&6&lMiraiMC&r &b开发者模式已启用");
                        break;
                    }
                    switch (args[1].toLowerCase()){
                        case "load":{
                            try {
                                MiraiLoader.loadMiraiCore();
                            } catch (IOException | SAXException | ParserConfigurationException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                        case "unload":{
                            try {
                                JarLoader.unloadJar();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                        case "testclass":{
                            try {
                                Class.forName("net.mamoe.mirai.utils.BotConfiguration");
                                sender.sendMessage("Success");
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                        case "change":{
                            try {
                                JarLoader.unloadJar();
                                MiraiLoader.loadMiraiCore(args[2]);
                                sender.sendMessage("Success");
                            } catch (IOException | ParserConfigurationException | SAXException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                    }
                }
                break;
            }
            default:{
                sender.sendMessage("&c未知或不完整的命令，请输入 /mirai help 查看帮助！");
                break;
            }
        }
        return true;
    }
}
