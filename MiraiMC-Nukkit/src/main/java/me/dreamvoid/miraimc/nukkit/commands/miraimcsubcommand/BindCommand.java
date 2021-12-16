package me.dreamvoid.miraimc.nukkit.commands.miraimcsubcommand;

import cn.nukkit.OfflinePlayer;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.nukkit.commands.base.BaseSubCommand;

/**
 * @author LT_Name
 */
public class BindCommand extends BaseSubCommand {

    public BindCommand(String name) {
        super(name);
    }

    @Override
    public boolean canUser(CommandSender sender) {
        return sender.hasPermission("miraimc.command.miraimc.bind");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 2){
            switch (args[1].toLowerCase()){
                case "add": {
                    if(args.length>=4){
                        plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                            @Override
                            public void onRun() {
                                String uuid = plugin.getServer().getOfflinePlayer(args[2]).getUniqueId().toString();
                                long qqid = Long.parseLong(args[3]);
                                MiraiMC.addBinding(uuid,qqid);
                                sender.sendMessage(TextFormat.colorize('&',"&a已添加绑定！"));
                            }
                        });
                    } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /miraimc bind add <玩家名> <QQ号>"));
                    break;
                }
                case "removeplayer":{
                    if(args.length>=3){
                        plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                            @Override
                            public void onRun() {
                                String uuid = plugin.getServer().getOfflinePlayer(args[2]).getUniqueId().toString();
                                MiraiMC.removeBinding(uuid);
                                sender.sendMessage(TextFormat.colorize('&',"&a已移除相应绑定！"));
                            }
                        });
                    } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /miraimc bind removeplayer <玩家名>"));
                    break;
                }
                case "removeqq":{
                    if(args.length>=3){
                        plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                            @Override
                            public void onRun() {
                                long qqid = Long.parseLong(args[2]);
                                MiraiMC.removeBinding(qqid);
                                sender.sendMessage(TextFormat.colorize('&',"&a已移除相应绑定！"));
                            }
                        });
                    } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /miraimc bind removeqq <QQ号>"));
                    break;
                }
                case "getplayer":{
                    if(args.length>=3){
                        plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                            @Override
                            public void onRun() {
                                String uuid = plugin.getServer().getOfflinePlayer(args[2]).getUniqueId().toString();
                                long qqId = MiraiMC.getBinding(uuid);
                                if(qqId!=0){
                                    sender.sendMessage(TextFormat.colorize('&',"&a绑定的QQ号："+qqId));
                                } else sender.sendMessage(TextFormat.colorize('&',"&c未找到符合条件的记录！"));
                            }
                        });
                    } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /miraimc bind getplayer <玩家名>"));
                    break;
                }
                case "getqq":{
                    if(args.length>=3){
                        plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                            @Override
                            public void onRun() {
                                long qqid = Long.parseLong(args[2]);
                                String UUID = MiraiMC.getBinding(qqid);
                                if(!UUID.equals("")){
                                    OfflinePlayer player = (OfflinePlayer) plugin.getServer().getOfflinePlayer(UUID); // 对于此方法来说，任何玩家都存在. 亲测是真的
                                    sender.sendMessage(TextFormat.colorize('&',"&a绑定的玩家名："+player.getName()));
                                } else sender.sendMessage(TextFormat.colorize('&',"&c未找到符合条件的记录！"));
                            }
                        });
                    } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /miraimc bind getqq <QQ号>"));
                    break;
                }
                default:{
                    sender.sendMessage(TextFormat.colorize('&',"&c未知或不完整的命令，请输入 /miraimc bind 查看帮助！"));
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public CommandParameter[] getParameters() {
        //TODO
        return new CommandParameter[0];
    }
}
