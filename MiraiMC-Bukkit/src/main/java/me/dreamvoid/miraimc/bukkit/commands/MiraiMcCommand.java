package me.dreamvoid.miraimc.bukkit.commands;

import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.BukkitConfig;
import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class MiraiMcCommand implements CommandExecutor {
    private final BukkitPlugin plugin;

    public MiraiMcCommand(BukkitPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 0) {
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
}
