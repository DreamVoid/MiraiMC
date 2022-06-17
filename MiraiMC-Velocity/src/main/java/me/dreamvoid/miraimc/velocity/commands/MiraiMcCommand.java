package me.dreamvoid.miraimc.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.velocity.VelocityConfig;
import me.dreamvoid.miraimc.velocity.VelocityPlugin;
import me.dreamvoid.miraimc.velocity.utils.Color;
import net.kyori.adventure.text.Component;

import java.io.IOException;
import java.util.UUID;

public class MiraiMcCommand implements SimpleCommand {
    private final VelocityPlugin plugin;

    public MiraiMcCommand(VelocityPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();

        if(args.length != 0) {
            switch (args[0].toLowerCase()) {
                case "reload": {
                    if(source.hasPermission("miraimc.command.miraimc.reload")){
                        try {
                            VelocityConfig.reloadConfig();
                            source.sendMessage(Component.text(Color.translate( "&a配置文件已经重新加载，部分配置可能需要重新启动服务器才能生效！")));
                        } catch (IOException e) {
                            e.printStackTrace();
                            source.sendMessage(Component.text(Color.translate("&c加载配置文件时出错，请检查控制台了解更多信息！")));
                        }
                    } else source.sendMessage(Component.text(Color.translate("&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "bind": {
                    if(source.hasPermission("miraimc.command.miraimc.bind")){
                        if(args.length >= 2){
                            switch (args[1].toLowerCase()){
                                case "add": {
                                    if(args.length>=4){
                                        plugin.getServer().getScheduler().buildTask(plugin, () -> {
                                            Player player = plugin.getServer().getPlayer(args[2]).orElse(null);
                                            if(player!=null){
                                                String uuid = player.getUniqueId().toString();
                                                long qqid = Long.parseLong(args[3]);
                                                MiraiMC.addBinding(uuid,qqid);
                                                source.sendMessage(Component.text(Color.translate("&a已添加绑定！")));
                                            } else source.sendMessage(Component.text(Color.translate("&c指定的玩家不存在，请检查是否存在拼写错误！")));
                                        });
                                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /miraimc bind add <玩家名> <QQ号>")));
                                    break;
                                }
                                case "removeplayer":{
                                    if(args.length>=3){
                                        plugin.getServer().getScheduler().buildTask(plugin,() -> {
                                            Player player = plugin.getServer().getPlayer(args[2]).orElse(null);
                                            if(player!=null){
                                                String uuid = player.getUniqueId().toString();
                                                MiraiMC.removeBinding(uuid);
                                                source.sendMessage(Component.text(Color.translate("&a已移除相应绑定！")));
                                            } else source.sendMessage(Component.text(Color.translate("&c指定的玩家不存在，请检查是否存在拼写错误！")));
                                        });
                                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /miraimc bind removeplayer <玩家名>")));
                                    break;
                                }
                                case "removeqq":{
                                    if(args.length>=3){
                                        plugin.getServer().getScheduler().buildTask(plugin, () -> {
                                            long qqid = Long.parseLong(args[2]);
                                            MiraiMC.removeBinding(qqid);
                                            source.sendMessage(Component.text(Color.translate("&a已移除相应绑定！")));
                                        });
                                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /miraimc bind removeqq <QQ号>")));
                                    break;
                                }
                                case "getplayer":{
                                    if(args.length>=3){
                                        plugin.getServer().getScheduler().buildTask(plugin, () -> {
                                            Player player = plugin.getServer().getPlayer(args[2]).orElse(null);
                                            if(player!=null){
                                                String uuid = player.getUniqueId().toString();
                                                long qqId = MiraiMC.getBinding(uuid);
                                                if(qqId!=0){
                                                    source.sendMessage(Component.text(Color.translate("&a绑定的QQ号："+qqId)));
                                                } else source.sendMessage(Component.text(Color.translate("&c未找到符合条件的记录！")));
                                            } else source.sendMessage(Component.text(Color.translate("&c指定的玩家不存在，请检查是否存在拼写错误！")));
                                        });
                                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /miraimc bind getplayer <玩家名>")));
                                    break;
                                }
                                case "getqq":{
                                    if(args.length>=3){
                                        plugin.getServer().getScheduler().buildTask(plugin, () -> {
                                            long qqid = Long.parseLong(args[2]);
                                            String playerName = MiraiMC.getBinding(qqid);
                                            if(!playerName.equals("")){
                                                source.sendMessage(Component.text(Color.translate("&a绑定的玩家名："+ plugin.getServer().getPlayer(UUID.fromString(playerName)).get().getUsername())));
                                            } else source.sendMessage(Component.text(Color.translate("&c未找到符合条件的记录！")));
                                        });
                                    } else source.sendMessage(Component.text(Color.translate("&c无效的参数！用法: /miraimc bind getqq <QQ号>")));
                                    break;
                                }
                                default:{
                                    source.sendMessage(Component.text(Color.translate("&c未知或不完整的命令，请输入 /miraimc bind 查看帮助！")));
                                    break;
                                }
                            }
                        } else {
                            source.sendMessage(Component.text(Color.translate("&6&lMiraiMC&r &b插件帮助菜单&r &a玩家绑定")));
                            source.sendMessage(Component.text(Color.translate("&6/miraimc bind add <玩家名> <QQ号>:&r 为玩家和QQ号添加绑定")));
                            source.sendMessage(Component.text(Color.translate("&6/miraimc bind getplayer <玩家名>:&r 获取指定玩家名绑定的QQ号")));
                            source.sendMessage(Component.text(Color.translate("&6/miraimc bind getqq <QQ号>:&r 获取指定QQ号绑定的玩家名")));
                            source.sendMessage(Component.text(Color.translate("&6/miraimc bind removeplayer <玩家名>:&r 删除一个玩家的绑定")));
                            source.sendMessage(Component.text(Color.translate("&6/miraimc bind removeqq <QQ号>:&r 删除一个QQ号的绑定")));
                        }
                    } else source.sendMessage(Component.text(Color.translate("&c你没有足够的权限执行此命令！")));
                    break;
                }
                case "help": {
                    source.sendMessage(Component.text(Color.translate("&6&lMiraiMC&r &b插件帮助菜单")));
                    source.sendMessage(Component.text(Color.translate("&6/miraimc bind:&r 玩家绑定菜单")));
                    source.sendMessage(Component.text(Color.translate("&6/miraimc reload:&r 重新加载插件")));
                    break;
                }
                default:{
                    source.sendMessage(Component.text(Color.translate("&c未知或不完整的命令，请输入 /miraimc help 查看帮助！")));
                    break;
                }
            }
        } else {
            source.sendMessage(Component.text("This server is running "+ plugin.getPluginContainer().getDescription().getName() +" version "+ plugin.getPluginContainer().getDescription().getVersion()+" by "+ plugin.getPluginContainer().getDescription().getAuthors().toString().replace("[","").replace("]","")));
        }
    }
}
