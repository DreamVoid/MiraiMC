package me.dreamvoid.miraimc.bungee.commands;

import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bungee.BungeePlugin;
import me.dreamvoid.miraimc.bungee.BungeeConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class MiraiMcCommand extends Command {
    private final BungeePlugin bungee;

    public MiraiMcCommand(BungeePlugin bungee, String name) {
        super(name);
        this.bungee = bungee;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(args.length == 0)) {
            switch (args[0].toLowerCase()) {
                case "reload": {
                    if(sender.hasPermission("miraimc.command.miraimc.reload")){
                        BungeeConfig.reloadConfig();
                        sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&a配置文件已经重新加载，部分配置可能需要重新启动服务器才能生效！")).create());
                    } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！")).create());
                    break;
                }
                case "bind": {
                    if(sender.hasPermission("miraimc.command.miraimc.bind")){
                        if(args.length >= 2){
                            switch (args[1].toLowerCase()){
                                case "add": {
                                    if(args.length>=4){
                                        bungee.getProxy().getScheduler().runAsync(bungee, () -> {
                                            ProxiedPlayer player = bungee.getProxy().getPlayer(args[2]);
                                            if(player!=null){
                                                String uuid = player.getUniqueId().toString();
                                                long qqid = Long.parseLong(args[3]);
                                                MiraiMC.addBinding(uuid,qqid);
                                                sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&a已添加绑定！")).create());
                                            } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c指定的玩家不存在，请检查是否存在拼写错误！")).create());
                                        });
                                    } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /miraimc bind add <玩家名> <QQ号>")).create());
                                    break;
                                }
                                case "removeplayer":{
                                    if(args.length>=3){
                                        bungee.getProxy().getScheduler().runAsync(bungee,() -> {
                                            ProxiedPlayer player = bungee.getProxy().getPlayer(args[2]);
                                            if(player!=null){
                                                String uuid = bungee.getProxy().getPlayer(args[2]).getUniqueId().toString();
                                                MiraiMC.removeBinding(uuid);
                                                sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&a已移除相应绑定！")).create());
                                            } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c指定的玩家不存在，请检查是否存在拼写错误！")).create());
                                        });
                                    } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /miraimc bind removeplayer <玩家名>")).create());
                                    break;
                                }
                                case "removeqq":{
                                    if(args.length>=3){
                                        bungee.getProxy().getScheduler().runAsync(bungee, () -> {
                                            long qqid = Long.parseLong(args[2]);
                                            MiraiMC.removeBinding(qqid);
                                            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&a已移除相应绑定！")).create());
                                        });
                                    } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /miraimc bind removeqq <QQ号>")).create());
                                    break;
                                }
                                case "getplayer":{
                                    if(args.length>=3){
                                        bungee.getProxy().getScheduler().runAsync(bungee, () -> {
                                            ProxiedPlayer player = bungee.getProxy().getPlayer(args[2]);
                                            if(player!=null){
                                                String uuid = bungee.getProxy().getPlayer(args[2]).getUniqueId().toString();
                                                long qqId = MiraiMC.getBinding(uuid);
                                                if(qqId!=0){
                                                    sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&a绑定的QQ号："+qqId)).create());
                                                } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c未找到符合条件的记录！")).create());
                                            } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c指定的玩家不存在，请检查是否存在拼写错误！")).create());
                                        });
                                    } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /miraimc bind getplayer <玩家名>")).create());
                                    break;
                                }
                                case "getqq":{
                                    if(args.length>=3){
                                        bungee.getProxy().getScheduler().runAsync(bungee, () -> {
                                            long qqid = Long.parseLong(args[2]);
                                            String playerName = MiraiMC.getBinding(qqid);
                                            if(!playerName.equals("")){
                                                sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&a绑定的玩家名："+bungee.getProxy().getPlayer(UUID.fromString(playerName)).getDisplayName())).create());
                                            } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c未找到符合条件的记录！")).create());
                                        });
                                    } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c无效的参数！用法: /miraimc bind getqq <QQ号>")).create());
                                    break;
                                }
                                default:{
                                    sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /miraimc bind 查看帮助！")).create());
                                    break;
                                }
                            }
                        } else {
                            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&6&lMiraiMC&r &b插件帮助菜单&r &a玩家绑定")).create());
                            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind add <玩家名> <QQ号>:&r 为玩家和QQ号添加绑定")).create());
                            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind getplayer <玩家名>:&r 获取指定玩家名绑定的QQ号")).create());
                            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind getqq <QQ号>:&r 获取指定QQ号绑定的玩家名")).create());
                            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind removeplayer <玩家名>:&r 删除一个玩家的绑定")).create());
                            sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind removeqq <QQ号>:&r 删除一个QQ号的绑定")).create());
                        }
                    } else sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！")).create());
                    break;
                }
                case "help": {
                    sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&6&lMiraiMC&r &b插件帮助菜单")).create());
                    sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&6/miraimc bind:&r 玩家绑定菜单")).create());
                    sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&6/miraimc reload:&r 重新加载插件")).create());
                    break;
                }
                default:{
                    sender.sendMessage(new ComponentBuilder(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /miraimc help 查看帮助！")).create());
                    break;
                }
            }
        } else {
            sender.sendMessage(new ComponentBuilder("This server is running "+ bungee.getDescription().getName() +" version "+ bungee.getDescription().getVersion()+" by "+ bungee.getDescription().getAuthor()).create());
        }

    }
}
