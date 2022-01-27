package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.internal.httpapi.response.Bind;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.BotConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Commands implements CommandExecutor {

    private final BukkitPlugin plugin;
    private final MiraiAutoLogin MiraiAutoLogin;

    public Commands(BukkitPlugin plugin) {
        this.plugin = plugin;
        this.MiraiAutoLogin = new MiraiAutoLogin(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        switch (command.getName().toLowerCase()){
            case "mirai" : {
                if(!(args.length == 0)){
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
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e可用的协议类型: " + Arrays.toString(BotConfiguration.MiraiProtocol.values())
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
                                                        Bind bind = httpAPI.bind(httpAPI.verify(args[2]).session, Long.parseLong(args[1]));
                                                        if(bind.code == 0) {
                                                            sender.sendMessage(ChatColor.GREEN + args[1] + " HTTP-API登录成功！");
                                                        } else {
                                                            sender.sendMessage(ChatColor.YELLOW + "登录机器人时出现异常，原因: " + bind.msg);
                                                        }
                                                    } else sender.sendMessage(ChatColor.RED + "此服务器没有启用HTTP-API模式，请检查配置文件！");
                                                }
                                            } catch (InterruptedException e) {
                                                if(Config.Gen_FriendlyException) {
                                                    Utils.logger.warning("登录机器人时出现异常，原因: " + e.getLocalizedMessage());
                                                } else e.printStackTrace();
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c登录机器人时出现异常，请检查控制台输出！"));
                                            } catch (IOException e) {
                                                if(Config.Gen_FriendlyException) {
                                                    Utils.logger.warning("登录机器人时出现异常，原因: " + e);
                                                } else e.printStackTrace();
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c登录机器人时出现异常，请检查控制台输出！"));
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
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c指定的机器人不存在！"));
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
                                    MiraiBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2])).sendMessage(message.toString().replace("\\n",System.lineSeparator()));
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
                                    MiraiBot.getBot(Long.parseLong(args[1])).getFriend(Long.parseLong(args[2])).sendMessage(message.toString().replace("\\n",System.lineSeparator()));
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
                                for (long bots : BotList){
                                    Bot bot = Bot.getInstance(bots);
                                    if(bot.isOnline()){
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b"+bot.getId() + "&r &7-&r &6"+Bot.getInstance(bots).getNick()));
                                    } else {
                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b"+bot.getId() + "&r &7-&r &c离线"));
                                    }
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
                        case "help":{
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&lMiraiMC&r &b机器人帮助菜单"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai login <账号> <密码> [协议]:&r 登录一个机器人"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai logout <账号>:&r 退出一个机器人"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai list:&r 查看当前存在的机器人"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai sendfriendmessage <账号> <好友> <消息>:&r 向指定好友发送私聊消息"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai sendgroupmessage <账号> <群号> <消息>:&r 向指定群发送群聊消息"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/mirai sendfriendnudge <账号> <好友>:&r 向指定好友发送戳一戳"));
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
            case "miraimc" : {
                if(!(args.length == 0)) {
                    switch (args[0].toLowerCase()) {
                        case "reload": {
                            if(sender.hasPermission("miraimc.command.miraimc.reload")){
                                BukkitConfig.reloadConfig();
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a配置文件已经重新加载，部分配置可能需要重新启动服务器才能生效！"));
                            } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                            break;
                        }
                        case "bind": {
                            if(sender.hasPermission("miraimc.command.miraimc.bind")){
                                if(args.length >= 2){
                                    switch (args[1].toLowerCase()){
                                        case "add": {
                                            if(args.length>=4){
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        String uuid = Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString();
                                                        long qqid = Long.parseLong(args[3]);
                                                        MiraiMC.addBinding(uuid,qqid);
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已添加绑定！"));
                                                    }
                                                }.runTaskAsynchronously(plugin);
                                            } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /miraimc bind add <玩家名> <QQ号>"));
                                            break;
                                        }
                                        case "removeplayer":{
                                            if(args.length>=3){
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        String uuid = Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString();
                                                        MiraiMC.removeBinding(uuid);
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已移除相应绑定！"));
                                                    }
                                                }.runTaskAsynchronously(plugin);
                                            } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /miraimc bind removeplayer <玩家名>"));
                                            break;
                                        }
                                        case "removeqq":{
                                            if(args.length>=3){
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        long qqid = Long.parseLong(args[2]);
                                                        MiraiMC.removeBinding(qqid);
                                                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已移除相应绑定！"));
                                                    }
                                                }.runTaskAsynchronously(plugin);
                                            } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /miraimc bind removeqq <QQ号>"));
                                            break;
                                        }
                                        case "getplayer":{
                                            if(args.length>=3){
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        String uuid = Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString();
                                                        long qqId = MiraiMC.getBinding(uuid);
                                                        if(qqId!=0){
                                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a绑定的QQ号："+qqId));
                                                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c未找到符合条件的记录！"));
                                                    }
                                                }.runTaskAsynchronously(plugin);
                                            } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /miraimc bind getplayer <玩家名>"));
                                            break;
                                        }
                                        case "getqq":{
                                            if(args.length>=3){
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        long qqid = Long.parseLong(args[2]);
                                                        String UUID = MiraiMC.getBinding(qqid);
                                                        if(!UUID.equals("")){
                                                            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID); // 对于此方法来说，任何玩家都存在. 亲测是真的
                                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a绑定的玩家名："+player.getName()));
                                                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c未找到符合条件的记录！"));
                                                    }
                                                }.runTaskAsynchronously(plugin);
                                            } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /miraimc bind getqq <QQ号>"));
                                            break;
                                        }
                                        default:{
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /miraimc bind 查看帮助！"));
                                            break;
                                        }
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&lMiraiMC&r &b插件帮助菜单&r &a玩家绑定"));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind add <玩家名> <QQ号>:&r 为玩家和QQ号添加绑定"));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind getplayer <玩家名>:&r 获取指定玩家名绑定的QQ号"));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind getqq <QQ号>:&r 获取指定QQ号绑定的玩家名"));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind removeplayer <玩家名>:&r 删除一个玩家的绑定"));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind removeqq <QQ号>:&r 删除一个QQ号的绑定"));
                                }
                            } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                            break;
                        }
                        case "help": {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&lMiraiMC&r &b插件帮助菜单"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind:&r 玩家绑定菜单"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6/miraimc reload:&r 重新加载插件"));
                            break;
                        }
                        default:{
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /miraimc help 查看帮助！"));
                            break;
                        }
                    }
                    return true;
                } else {
                    sender.sendMessage("This server is running "+ plugin.getDescription().getName() +" version "+ plugin.getDescription().getVersion()+" by "+ plugin.getDescription().getAuthors().toString().replace("[","").replace("]",""));
                    return false;
                }
            }
            case "miraiverify":{
                switch (args[0].toLowerCase()){
                    case "unsafedevice":{
                        if(args.length >= 2){
                            MiraiLoginSolver.solveUnsafeDeviceLoginVerify(Long.parseLong(args[1]),false);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已将验证请求提交到服务器"));
                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify unsafedevice <账号>"));
                        break;
                    }
                    case "unsafedevicecancel":{
                        if(args.length >= 2){
                            MiraiLoginSolver.solveUnsafeDeviceLoginVerify(Long.parseLong(args[1]),true);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已取消登录验证流程"));
                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify unsafedevicecancel <账号>"));
                        break;
                    }
                    case "slidercaptcha":{
                        if(args.length >= 3){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已将ticket提交到服务器"));
                            MiraiLoginSolver.solveSliderCaptcha(Long.parseLong(args[1]),args[2]);
                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify slidercaptcha <账号> <ticket>"));
                        break;
                    }
                    case "slidercaptchacancel":{
                        if(args.length >= 2){
                            MiraiLoginSolver.solveSliderCaptcha(Long.parseLong(args[1]),true);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已取消登录验证流程"));
                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify slidercaptchacancel <账号>"));
                        break;
                    }
                    case "piccaptcha":{
                        if(args.length >= 3){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已将验证码提交到服务器"));
                            MiraiLoginSolver.solvePicCaptcha(Long.parseLong(args[1]),args[2]);
                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify piccaptcha <账号> <验证码>"));
                        break;
                    }
                    case "piccaptchacancel":{
                        if(args.length >= 2){
                            MiraiLoginSolver.solvePicCaptcha(Long.parseLong(args[1]),true);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a已取消登录验证流程"));
                        } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法：/miraiverify piccaptchacancel <账号>"));
                        break;
                    }
                }
                return true;
            }
        }
        return false;

    }
}
