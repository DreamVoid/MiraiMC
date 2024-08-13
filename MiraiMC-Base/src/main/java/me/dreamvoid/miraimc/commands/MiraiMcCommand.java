package me.dreamvoid.miraimc.commands;

import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.config.PluginConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class MiraiMcCommand implements ICommandExecutor {
    @Override
    public boolean onCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("This server is running "+ MiraiMC.getPlatform().getPluginName() +" version "+ MiraiMC.getPlatform().getPluginVersion()+" by "+ MiraiMC.getPlatform().getAuthors().toString().replace("[","").replace("]",""));
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "reload": {
                if(sender.hasPermission("miraimc.command.miraimc.reload")){
                    try {
                        PluginConfig.reloadConfig();
                        sender.sendMessage("&a配置文件已经重新加载，部分配置可能需要重新启动服务器才能生效！");
                    } catch (IOException e) {
                        Utils.getLogger().warning("加载配置文件时出现问题，原因："+e);
                        sender.sendMessage("&c重新配置文件时出现问题，请查看控制台了解更多信息！");
                    }
                } else sender.sendMessage("&c你没有足够的权限执行此命令！");
                break;
            }
            case "bind": {
                if(sender.hasPermission("miraimc.command.miraimc.bind")) {
                    if (args.length >= 2) {
                        switch (args[1].toLowerCase()) {
                            case "add": {
                                if (args.length >= 4) {
                                    MiraiMC.getPlatform().runTaskAsync(() -> {
                                        UUID uuid = MiraiMC.getPlatform().getPlayerUUID(args[2]);
                                        long qqid = Long.parseLong(args[3]);
                                        MiraiMC.Bind.addBind(uuid, qqid);
                                        sender.sendMessage("&a已添加绑定！");
                                    });
                                } else
                                    sender.sendMessage("&c无效的参数！用法: /miraimc bind add <玩家名> <QQ号>");
                                break;
                            }
                            case "removeplayer": {
                                if (args.length >= 3) {
                                    MiraiMC.getPlatform().runTaskAsync(() -> {
                                        UUID uuid = MiraiMC.getPlatform().getPlayerUUID(args[2]);
                                        MiraiMC.Bind.removeBind(uuid);
                                        sender.sendMessage("&a已移除相应绑定！");
                                    });
                                } else
                                    sender.sendMessage("&c无效的参数！用法: /miraimc bind removeplayer <玩家名>");
                                break;
                            }
                            case "removeqq": {
                                if (args.length >= 3) {
                                    MiraiMC.getPlatform().runTaskAsync(() -> {
                                        long qqid = Long.parseLong(args[2]);
                                        MiraiMC.Bind.removeBind(qqid);
                                        sender.sendMessage("&a已移除相应绑定！");
                                    });
                                } else
                                    sender.sendMessage("&c无效的参数！用法: /miraimc bind removeqq <QQ号>");
                                break;
                            }
                            case "getplayer": {
                                if (args.length >= 3) {
                                    MiraiMC.getPlatform().runTaskAsync(() -> {
                                        UUID uuid = MiraiMC.getPlatform().getPlayerUUID(args[2]);
                                        long qqId = MiraiMC.Bind.getBind(uuid);
                                        if (qqId != 0) {
                                            sender.sendMessage("&a绑定的QQ号：" + qqId);
                                        } else
                                            sender.sendMessage("&c未找到符合条件的记录！");
                                    });
                                } else
                                    sender.sendMessage("&c无效的参数！用法: /miraimc bind getplayer <玩家名>");
                                break;
                            }
                            case "getqq": {
                                if (args.length >= 3) {
                                    MiraiMC.getPlatform().runTaskAsync(() -> {
                                        long qqid = Long.parseLong(args[2]);
                                        UUID uuid = MiraiMC.Bind.getBind(qqid);
                                        if (uuid != null) {
                                            sender.sendMessage("&a绑定的玩家名：" + MiraiMC.getPlatform().getPlayerName(uuid));
                                        } else
                                            sender.sendMessage("&c未找到符合条件的记录！");
                                    });
                                } else
                                    sender.sendMessage("&c无效的参数！用法: /miraimc bind getqq <QQ号>");
                                break;
                            }
                            default: {
                                sender.sendMessage("&c未知或不完整的命令，请输入 /miraimc help 查看帮助！");
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
                            sender.sendMessage(s);
                        }
                    }
                } else sender.sendMessage("&c你没有足够的权限执行此命令！");
                break;
            }
            case "help": {
                for (String s : Arrays.asList("&6&lMiraiMC&r &b插件帮助菜单",
                        "&6/miraimc bind:&r 玩家绑定帮助菜单",
                        "&6/miraimc reload:&r 重新加载插件")) {
                    sender.sendMessage(s);
                }
                break;
            }
            default:{
                sender.sendMessage("&c未知或不完整的命令，请输入 /miraimc help 查看帮助！");
                break;
            }
        }
        return true;
    }
}
