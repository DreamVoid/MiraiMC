package me.dreamvoid.miraimc.nukkit.commands.miraisubcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.nukkit.MiraiAutoLogin;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseSubCommand;

import java.util.Map;

/**
 * @author LT_Name
 */
public class AutoLoginCommand extends BaseSubCommand {

    private final MiraiAutoLogin MiraiAutoLogin = me.dreamvoid.miraimc.nukkit.MiraiAutoLogin.Instance;

    public AutoLoginCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.hasPermission("miraimc.command.mirai.autologin");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length>=2){
            switch (args[1]){
                case "add":{
                    boolean result;
                    if(args.length>=4){
                        if(args.length == 5){
                            result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], args[4]);
                        } else result = MiraiAutoLogin.addAutoLoginBot(Long.parseLong(args[2]), args[3], "ANDROID_PHONE");
                        if(result){
                            sender.sendMessage(TextFormat.colorize('&',"&a新的自动登录机器人添加成功！"));
                        } else sender.sendMessage(TextFormat.colorize('&',"&c新的自动登录机器人添加失败，请检查控制台错误输出！"));
                    } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /mirai autologin add <账号> <密码> [协议]"));
                    break;
                }
                case "remove":{
                    boolean result;
                    if(args.length>=3){
                        result = MiraiAutoLogin.deleteAutoLoginBot(Long.parseLong(args[2]));
                        if(result){
                            sender.sendMessage(TextFormat.colorize('&',"&a删除自动登录机器人成功！"));
                        } else sender.sendMessage(TextFormat.colorize('&',"&c删除自动登录机器人失败，请检查控制台错误输出！"));
                    } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /mirai autologin remove <账号>"));
                    break;
                }
                case "list":{
                    sender.sendMessage(TextFormat.colorize('&',"&a存在的自动登录机器人: "));
                    for (Map<?,?> bots : MiraiAutoLogin.loadAutoLoginList()){
                        sender.sendMessage(TextFormat.colorize('&', "&b"+bots.get("account")));
                    }
                    break;
                }
                default:{
                    sender.sendMessage(TextFormat.colorize('&',"&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));
                    break;
                }
            }
        } else sender.sendMessage(TextFormat.colorize('&',"&c未知或不完整的命令，请输入 /mirai help 查看帮助！"));

        return false;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}
