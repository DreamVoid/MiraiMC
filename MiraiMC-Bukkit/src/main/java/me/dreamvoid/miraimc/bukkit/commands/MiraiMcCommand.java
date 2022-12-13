package me.dreamvoid.miraimc.bukkit.commands;

import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.BukkitConfig;
import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MiraiMcCommand implements TabExecutor {
    private final BukkitPlugin plugin;

    public MiraiMcCommand(BukkitPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("This server is running "+ plugin.getDescription().getName() +" version "+ plugin.getDescription().getVersion()+" by "+ plugin.getDescription().getAuthors().toString().replace("[","").replace("]",""));
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "reload": {
                if(sender.hasPermission("miraimc.command.miraimc.reload")){
                    try {
                        BukkitConfig.reloadConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a配置文件已经重新加载，部分配置可能需要重新启动服务器才能生效！"));
                    } catch (IOException | InvalidConfigurationException e) {
                        plugin.getLogger().warning("加载配置文件时出现问题，原因："+e);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c重新配置文件时出现问题，请查看控制台了解更多信息！"));
                    }
                } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                break;
            }
            case "bind": {
                if(sender.hasPermission("miraimc.command.miraimc.bind")) {
                    if (args.length >= 2) {
                        switch (args[1].toLowerCase()) {
                            case "add": {
                                if (args.length >= 4) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            UUID uuid = Bukkit.getOfflinePlayer(args[2]).getUniqueId();
                                            long qqid = Long.parseLong(args[3]);
                                            MiraiMC.addBind(uuid, qqid);
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a已添加绑定！"));
                                        }
                                    }.runTaskAsynchronously(plugin);
                                } else
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c无效的参数！用法: /miraimc bind add <玩家名> <QQ号>"));
                                break;
                            }
                            case "removeplayer": {
                                if (args.length >= 3) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            UUID uuid = Bukkit.getOfflinePlayer(UUID.fromString(args[2])).getUniqueId();
                                            MiraiMC.removeBind(uuid);
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a已移除相应绑定！"));
                                        }
                                    }.runTaskAsynchronously(plugin);
                                } else
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c无效的参数！用法: /miraimc bind removeplayer <玩家名>"));
                                break;
                            }
                            case "removeqq": {
                                if (args.length >= 3) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            long qqid = Long.parseLong(args[2]);
                                            MiraiMC.removeBind(qqid);
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a已移除相应绑定！"));
                                        }
                                    }.runTaskAsynchronously(plugin);
                                } else
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c无效的参数！用法: /miraimc bind removeqq <QQ号>"));
                                break;
                            }
                            case "getplayer": {
                                if (args.length >= 3) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            UUID uuid = Bukkit.getOfflinePlayer(args[2]).getUniqueId();
                                            long qqId = MiraiMC.getBind(uuid);
                                            if (qqId != 0) {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a绑定的QQ号：" + qqId));
                                            } else
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c未找到符合条件的记录！"));
                                        }
                                    }.runTaskAsynchronously(plugin);
                                } else
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c无效的参数！用法: /miraimc bind getplayer <玩家名>"));
                                break;
                            }
                            case "getqq": {
                                if (args.length >= 3) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            long qqid = Long.parseLong(args[2]);
                                            UUID uuid = MiraiMC.getBind(qqid);
                                            if (uuid != null) {
                                                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid); // 对于此方法来说，任何玩家都存在. 亲测是真的
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a绑定的玩家名：" + player.getName()));
                                            } else
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c未找到符合条件的记录！"));
                                        }
                                    }.runTaskAsynchronously(plugin);
                                } else
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c无效的参数！用法: /miraimc bind getqq <QQ号>"));
                                break;
                            }
                            default: {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c未知或不完整的命令，请输入 /miraimc help 查看帮助！"));
                                break;
                            }
                        }
                    } else {
                        for (String s : Arrays.asList("&6&lMiraiMC&r &b插件帮助菜单&r &a玩家绑定",
                                "&6/miraimc bind add <玩家名> <QQ号>:&r 为玩家和QQ号添加绑定",
                                "&6/miraimc bind getplayer <玩家名>:&r 获取指定玩家名绑定的QQ号",
                                "&6/miraimc bind getqq <QQ号>:&r 获取指定QQ号绑定的玩家名",
                                "&6/miraimc bind removeplayer <玩家名>:&r 删除一个玩家的绑定",
                                "&6/miraimc bind removeqq <QQ号>:&r 删除一个QQ号的绑定")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
                        }
                    }
                } else sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c你没有足够的权限执行此命令！"));
                break;
            }
            case "help": {
                for (String s : Arrays.asList("&6&lMiraiMC&r &b插件帮助菜单",
                        "&6/miraimc bind add <玩家名> <QQ号>:&r 为玩家和QQ号添加绑定",
                        "&6/miraimc bind getplayer <玩家名>:&r 获取指定玩家名绑定的QQ号",
                        "&6/miraimc bind getqq <QQ号>:&r 获取指定QQ号绑定的玩家名",
                        "&6/miraimc bind removeplayer <玩家名>:&r 删除一个玩家的绑定",
                        "&6/miraimc bind removeqq <QQ号>:&r 删除一个QQ号的绑定",
                        "&6/miraimc reload:&r 重新加载插件")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
                }
                break;
            }
            default:{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c未知或不完整的命令，请输入 /miraimc help 查看帮助！"));
                break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> result = new ArrayList<>();

        if(args.length == 1){
            String[] list = new String[]{"bind", "reload"};
            for(String s : list){
                if(s.startsWith(args[0])) result.add(s);
            }
        }

        if(args.length == 2){
            if("bind".equalsIgnoreCase(args[0])){
                String[] list = new String[]{"add", "getplayer", "getqq", "removeplayer", "removeqq"};
                for(String s : list){
                    if(s.startsWith(args[1])) result.add(s);
                }
            }
        }

        if(args.length == 3){
            List<String> list = Arrays.asList("add", "getplayer", "removeplayer");
            if("bind".equalsIgnoreCase(args[0]) && list.contains(args[1].toLowerCase())){
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p.getName().startsWith(args[2])) result.add(p.getName());
                }
            }
        }

        return result;
    }
}
