package me.dreamvoid.miraimc.nukkit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseCommand;
import me.dreamvoid.miraimc.nukkit.commands.miraisubcommand.*;

/**
 * @author LT_Name
 */
public class MiraiCommand extends BaseCommand {

    public MiraiCommand() {
        super("mirai", "MiraiBot Bot Command.");
        this.setPermission("miraimc.command.mirai");
        this.setUsage("For help, type /mirai help");

        this.addSubCommand(new LoginCommand("Login"));
        this.addSubCommand(new LogoutCommand("Logout"));
        this.addSubCommand(new SendGroupMessageCommand("SendGroupMessage"));
        this.addSubCommand(new SendFriendMessageCommand("SendFriendMessage"));
        this.addSubCommand(new SendFriendNudgeCommand("SendFriendNudge"));
        this.addSubCommand(new ListCommand("List"));
        this.addSubCommand(new CheckOnlineCommand("CheckOnline"));
        this.addSubCommand(new AutoLoginCommand("AutoLogin"));
        this.addSubCommand(new AutoLoginCommand("UploadImage"));
    }

    @Override
    public void sendHelp(CommandSender sender) {
        sender.sendMessage(TextFormat.colorize('&',"&6&lMiraiMC&r &b机器人帮助菜单"));
        sender.sendMessage(TextFormat.colorize('&',"&6/mirai login <账号> <密码> [协议]:&r 登录一个机器人"));
        sender.sendMessage(TextFormat.colorize('&',"&6/mirai logout <账号>:&r 退出一个机器人"));
        sender.sendMessage(TextFormat.colorize('&',"&6/mirai list:&r 查看当前存在的机器人"));
        sender.sendMessage(TextFormat.colorize('&',"&6/mirai sendfriendmessage <账号> <好友> <消息>:&r 向指定好友发送私聊消息"));
        sender.sendMessage(TextFormat.colorize('&',"&6/mirai sendgroupmessage <账号> <群号> <消息>:&r 向指定群发送群聊消息"));
        sender.sendMessage(TextFormat.colorize('&',"&6/mirai sendfriendnudge <账号> <好友>:&r 向指定好友发送戳一戳"));
        sender.sendMessage(TextFormat.colorize('&',"&6/mirai uploadimage <账号> <图片文件名>:&r 上传指定图片"));
        sender.sendMessage(TextFormat.colorize('&',"&6/mirai checkonline <账号>:&r 检查指定的机器人是否在线"));
        sender.sendMessage(TextFormat.colorize('&',"&6/mirai autologin add <账号> <密码> [协议]:&r 添加一个自动登录账号"));
        sender.sendMessage(TextFormat.colorize('&',"&6/mirai autologin list:&r 查看自动登录账号列表"));
        sender.sendMessage(TextFormat.colorize('&',"&6/mirai autologin remove <账号>:&r 删除一个自动登录账号"));
    }

    @Override
    public void sendUI(Player player) {
        this.sendHelp(player); //无GUI界面
    }
}
