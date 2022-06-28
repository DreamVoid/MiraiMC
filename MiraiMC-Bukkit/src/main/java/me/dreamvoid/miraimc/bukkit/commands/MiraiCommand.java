package me.dreamvoid.miraimc.bukkit.commands;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import me.dreamvoid.miraimc.bukkit.MiraiAutoLogin;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.internal.httpapi.exception.AbnormalStatusException;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.BotConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MiraiCommand implements TabExecutor {
    private final BukkitPlugin plugin;
    private final MiraiAutoLogin MiraiAutoLogin;

    public MiraiCommand(BukkitPlugin plugin) {
        this.plugin = plugin;
        this.MiraiAutoLogin = new MiraiAutoLogin(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 0){
            switch (args[0].toLowerCase()){
                case "login": {
                    if(sender.hasPermission("miraimc.command.mirai.login")){
                        if(args.length >= 3) {
                            new BukkitRunnable(){

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
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e无效的协议类型，已自动选择 ANDROID_PHONE."));
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e可用的协议类型: " + MiraiBot.getAvailableProtocol(true).toString().replace("[", "").replace("]", "")));
                                        Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                                    }

                                    try {
                                        if(!useHttpApi){
                                            MiraiBot.doBotLogin(Long.parseLong(args[1]),args[2], Protocol);
                                        } else {
                                            if(Config.Gen_EnableHttpApi) {
                                                MiraiHttpAPI httpAPI = new MiraiHttpAPI(Config.HTTPAPI_Url);
                                                httpAPI.bind(httpAPI.verify(args[2]).session, Long.parseLong(args[1]));
                                                sender.sendMessage(ChatColor.GREEN + args[1] + " HTTP-API登录成功！");
                                            } else sender.sendMessage(ChatColor.RED + "此服务器没有启用HTTP-API模式，请检查配置文件！");
                                        }
                                    } catch (InterruptedException e) {
                                        Utils.logger.warning("登录机器人时出现异常，原因: " + e.getLocalizedMessage());
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c登录机器人时出现异常，请检查控制台输出！"));
                                    } catch (IOException e) {
                                        Utils.logger.warning("登录机器人时出现异常，原因: " + e);
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c登录机器人时出现异常，请检查控制台输出！"));
                                    } catch (AbnormalStatusException e) {
                                        Utils.logger.warning("使用HTTPAPI登录机器人时出现异常，状态码："+e.getCode()+"，原因: " + e.getMessage());
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c登录机器人时出现异常，状态码："+e.getCode()+"，原因: " + e.getMessage()));
                                    }
                                }
                            }.runTaskAsynchronously(plugin);
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai login <账号> <密码> [协议]"));
                        }
                    } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "logout":{
                    if(sender.hasPermission("miraimc.command.mirai.logout")){
                        if(args.length >= 2) {
                            try {
                                MiraiBot.getBot(Long.parseLong(args[1])).doLogout();
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a已退出指定机器人！"));
                            } catch (NoSuchElementException e){
                                if(Config.Gen_EnableHttpApi && MiraiHttpAPI.Bots.containsKey(Long.parseLong(args[1]))){
                                    try {
                                        new MiraiHttpAPI(Config.HTTPAPI_Url).release(MiraiHttpAPI.Bots.get(Long.parseLong(args[1])),Long.parseLong(args[1]));
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a已退出指定机器人！"));
                                    } catch (IOException ex) {
                                        Utils.logger.warning("退出机器人时出现异常，原因: " + ex);
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c退出机器人时出现异常，请检查控制台输出！"));
                                    } catch (AbnormalStatusException ex) {
                                        if(ex.getCode() == 2){
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c指定的机器人不存在！"));
                                        } else {
                                            Utils.logger.warning("退出机器人时出现异常，状态码："+ex.getCode()+"，原因: "+ex.getMessage());
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c退出机器人时出现异常，状态码："+ex.getCode()+"，原因: "+ex.getMessage()));
                                        }
                                    }
                                } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c指定的机器人不存在！"));
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai logout <账号>"));
                        }
                    } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "sendgroupmessage":{
                    if(sender.hasPermission("miraimc.command.mirai.sendgroupmessage")){
                        if(args.length >= 4){
                            StringBuilder message = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {    //list.size()就是循环的次数
                                if(i >= 3){
                                    message.append(args[i]).append(" ");
                                }
                            }
                            String text = message.toString().replace("\\n",System.lineSeparator());
                            try {
                                MiraiBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2])).sendMessageMirai(text);
                            } catch (NoSuchElementException e){
                                if(Config.Gen_EnableHttpApi && MiraiHttpAPI.Bots.containsKey(Long.parseLong(args[1]))){
                                    try {
                                        MiraiHttpAPI.INSTANCE.sendGroupMessage(MiraiHttpAPI.Bots.get(Long.parseLong(args[1])), Long.parseLong(args[2]), text);
                                    } catch (IOException ex) {
                                        Utils.logger.warning("发送群消息时出现异常，原因: "+ e);
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c发送群消息时出现异常，请检查控制台了解更多信息！"));
                                    } catch (AbnormalStatusException ex) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c发送群消息时出现异常，状态码: " + ex.getCode()+"，原因: "+ex.getMessage()));
                                    }
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai sendgroupmessage <账号> <群号> <消息>"));
                        }
                    } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "sendfriendmessage":{
                    if(sender.hasPermission("miraimc.command.mirai.sendfriendmessage")){
                        if(args.length >= 4){
                            StringBuilder message = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {    //list.size()就是循环的次数
                                if(i >= 3){
                                    message.append(args[i]).append(" ");
                                }
                            }
                            String text = message.toString().replace("\\n",System.lineSeparator());
                            try {
                                MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendMessageMirai(text);
                            } catch (NoSuchElementException e){
                                if(Config.Gen_EnableHttpApi && MiraiHttpAPI.Bots.containsKey(Long.parseLong(args[1]))){
                                    try {
                                        MiraiHttpAPI.INSTANCE.sendGroupMessage(MiraiHttpAPI.Bots.get(Long.parseLong(args[1])), Long.parseLong(args[2]), text);
                                    } catch (IOException ex) {
                                        Utils.logger.warning("发送好友消息时出现异常，原因: "+ e);
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c发送好友消息时出现异常，请检查控制台了解更多信息！"));
                                    } catch (AbnormalStatusException ex) {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c发送好友消息时出现异常，状态码: " + ex.getCode()+"，原因: "+ex.getMessage()));
                                    }
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai sendfriendmessage <账号> <好友> <消息>"));
                        }
                    } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "sendfriendnudge":{
                    if(sender.hasPermission("miraimc.command.mirai.sendfriendnudge")){
                        if(args.length >= 3){
                            MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendNudge();
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai sendfriendnudge <账号> <好友>"));
                        }
                    } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "list":{
                    if(sender.hasPermission("miraimc.command.mirai.list")){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a存在的机器人: "));
                        List<Long> BotList = MiraiBot.getOnlineBots();

                        // Core
                        for (Long bots : BotList) {
                            Bot bot = Bot.getInstance(bots);
                            if (bot.isOnline()) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + bot.getId() + "&r &7-&r &6" + Bot.getInstance(bots).getNick()));
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + bot.getId() + "&r &7-&r &c离线"));
                            }
                        }

                        // HTTP API
                        for (long botWithHttp : MiraiHttpAPI.Bots.keySet()){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b"+botWithHttp + "&r &7-&r &eHTTP API"));
                        }
                    } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "checkonline":{
                    if(sender.hasPermission("miraimc.command.mirai.checkonline")){
                        if(args.length >= 2){
                            if(MiraiBot.getBot(Long.parseLong(args[1])).isOnline()){
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a当前机器人在线"));
                            } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e当前机器人不在线"));
                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai checkonline <账号>"));
                    } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
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
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a新的自动登录机器人添加成功！"));
                                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c新的自动登录机器人添加失败，请检查控制台错误输出！"));
                                    } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai autologin add <账号> <密码> [协议]"));
                                    break;
                                }
                                case "remove":{
                                    boolean result;
                                    if(args.length>=3){
                                        result = MiraiAutoLogin.delAutoLoginBot(Long.parseLong(args[2]));
                                        if(result){
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a删除自动登录机器人成功！"));
                                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c删除自动登录机器人失败，请检查控制台错误输出！"));
                                    } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /mirai autologin remove <账号>"));
                                    break;
                                }
                                case "list":{
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a存在的自动登录机器人: "));
                                    List<Map<?,?>> AutoLoginBotList = MiraiAutoLogin.loadAutoLoginList();
                                    for (Map<?,?> bots : AutoLoginBotList){
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b"+bots.get("account")));
                                    }
                                    break;
                                }
                                default:{
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                                    break;
                                }
                            }
                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                    } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "uploadimage":{
                    if(sender.hasPermission("miraimc.command.mirai.uploadimage")) {
                        if (args.length >= 3) {
                            File ImageDir = new File(Config.PluginDir, "images");
                            if(!ImageDir.exists()) ImageDir.mkdir();
                            File image = new File(ImageDir, args[2]);

                            if(!image.exists() || image.isDirectory()) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c指定的图片文件不存在，请检查是否存在文件" + image.getPath()+"！"));
                                break;
                            }

                            try {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a图片上传成功，可使用Mirai Code发送图片：[mirai:image:" + MiraiBot.getBot(Long.parseLong(args[1])).uploadImage(image) + "]"));
                            } catch (NoSuchElementException e){
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c指定的机器人不存在！"));
                                break;
                            }

                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                    } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                    break;
                }
                case "help":{
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&lMiraiMC&r &b机器人帮助菜单"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai login <账号> <密码> [协议]:&r 登录一个机器人"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai logout <账号>:&r 退出一个机器人"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai list:&r 查看当前存在的机器人"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai sendfriendmessage <账号> <好友> <消息>:&r 向指定好友发送私聊消息"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai sendgroupmessage <账号> <群号> <消息>:&r 向指定群发送群聊消息"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai sendfriendnudge <账号> <好友>:&r 向指定好友发送戳一戳"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai uploadimage <账号> <图片文件名>:&r 上传指定图片"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai checkonline <账号>:&r 检查指定的机器人是否在线"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai autologin add <账号> <密码> [协议]:&r 添加一个自动登录账号"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai autologin list:&r 查看自动登录账号列表"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai autologin remove <账号>:&r 删除一个自动登录账号"));
                    break;
                }
                default:{
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                    break;
                }
            }
            return true;
        } else {
            sender.sendMessage("This server is running "+ plugin.getDescription().getName() +" version "+ plugin.getDescription().getVersion()+" by "+ plugin.getDescription().getAuthors().toString().replace("[","").replace("]",""));
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> result = new ArrayList<>();

        if(args.length == 1){
            String[] list = new String[]{"login", "logout", "list", "sendfriendmessage", "sendgroupmessage", "sendfriendnudge", "uploadimage", "checkonline", "autologin", "help"};
            for(String s : list){
                if(s.startsWith(args[0])) result.add(s);
            }
        }

        if(args.length == 2 && sender.hasPermission("miraimc.command.mirai." + args[0].toLowerCase())){
            // 1
            List<String> list1 = Arrays.asList("login", "logout", "sendfriendmessage", "sendgroupmessage", "sendfriendnudge", "uploadimage", "checkonline");
            if(list1.contains(args[0].toLowerCase())){
                for(Long l : MiraiBot.getOnlineBots()){
                    if(String.valueOf(l).startsWith(args[1])) result.add(String.valueOf(l));
                }
            }

            // 2
            if("autologin".equalsIgnoreCase(args[0])){
                String[] list = new String[]{"add", "list", "remove"};
                for(String s : list){
                    if(s.startsWith(args[1])) result.add(s);
                }
            }
        }

        if(args.length == 3 && sender.hasPermission("miraimc.command.mirai." + args[0].toLowerCase())){
            // 1
            List<String> list1 = Arrays.asList("sendfriendmessage", "sendfriendnudge");
            if(list1.contains(args[1].toLowerCase())){
                try {
                    for (Long l : MiraiBot.getBot(Long.parseLong(args[1])).getFriendList()) {
                        if (String.valueOf(l).startsWith(args[2])) result.add(String.valueOf(l));
                    }
                } catch (NoSuchElementException ignored) {}
            }

            // 2
            if("sendgroupmessage".equalsIgnoreCase(args[1])){
                try {
                    for (Long l : MiraiBot.getBot(Long.parseLong(args[1])).getGroupList()) {
                        if (String.valueOf(l).startsWith(args[2])) result.add(String.valueOf(l));
                    }
                } catch (NoSuchElementException ignored) {}
            }

            // 3
            if(args[0].equalsIgnoreCase("autologin")) {
                // 1
                if("add".equalsIgnoreCase(args[1])){
                    for(Long l : MiraiBot.getOnlineBots()){
                        if(String.valueOf(l).startsWith(args[2])) result.add(String.valueOf(l));
                    }
                }

                // 2
                if ("remove".equalsIgnoreCase(args[1])) {
                    List<String> accounts = MiraiAutoLogin.loadAutoLoginList().stream().map(map -> String.valueOf(map.get("account"))).collect(Collectors.toList());
                    for(String s : accounts){
                        if(s.startsWith(args[2])) result.add(s);
                    }
                }
            }
        }

        if(args.length == 4 && sender.hasPermission("miraimc.command.mirai." + args[0].toLowerCase())){
            if("login".equalsIgnoreCase(args[0])){
                result = MiraiBot.getAvailableProtocol();
            }
        }

        if(args.length == 5 && sender.hasPermission("miraimc.command.mirai." + args[0].toLowerCase())){
            if("autologin".equalsIgnoreCase(args[0]) && "add".equalsIgnoreCase(args[1])){
                result = MiraiBot.getAvailableProtocol();
            }
        }

        return result;
    }
}
